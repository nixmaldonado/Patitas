package com.example.patitas.pets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.patitas.R;
import com.example.patitas.data.source.FirebasePetsRepository;
import com.example.patitas.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetsActivity extends AppCompatActivity {

    /*@BindView(R.id.fab)
    FloatingActionButton fab;*/

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PetsPresenter petsPresenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        this.initToolbar();

        PetsFragment petsFragment = (PetsFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (petsFragment == null) {
            petsFragment = PetsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    this.getSupportFragmentManager(), petsFragment, R.id.contentFrame);
        }

        this.petsPresenter = new PetsPresenter(FirebasePetsRepository.getInstance(), petsFragment);
    }

    private void initToolbar() {
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setLogo(R.drawable.ic_pets_white_24dp);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /*@OnClick(R.id.fab)
    protected void addPet() {
        Intent editorIntent = new Intent(this, PetEditorActivity.class);
        this.startActivity(editorIntent);
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_catalog, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }*/
}
