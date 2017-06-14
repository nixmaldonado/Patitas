package com.example.patitas.peteditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.patitas.R;
import com.example.patitas.data.source.FirebasePetsRepository;
import com.example.patitas.pets.PetsFragment;
import com.example.patitas.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetEditorActivity extends AppCompatActivity {


    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    private PetEditorFragment petEditorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        this.setFragment();

        String petId = getIntent().getStringExtra(PetsFragment.EXTRA_PET_ID);

        this.setPresenter(petId);

        this.setSupportActionBar(this.toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_editor, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                petEditorFragment.createNewPet();
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setFragment() {
        petEditorFragment = (PetEditorFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (petEditorFragment == null){
            petEditorFragment = PetEditorFragment.newInstance();
            ActivityUtils.addFragmentToActivity(this.getSupportFragmentManager(),
                    petEditorFragment, R.id.contentFrame);
        }
    }

    private void setPresenter(String petId) {
        new PetEditorPresenter(petId, FirebasePetsRepository.getInstance(), petEditorFragment);
    }


}