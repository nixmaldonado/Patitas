package com.example.patitas.petcreator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.patitas.R;
import com.example.patitas.util.ActivityUtils;
import com.example.patitas.util.Injection;

import butterknife.ButterKnife;

public class PetCreatorActivity extends AppCompatActivity {

    private PetCreatorFragment petCreatorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_editor);
        ButterKnife.bind(this);

        this.setFragment();

        new PetCreatorPresenter(Injection.providePetsRepository(), this.petCreatorFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    private void setFragment() {
        this.petCreatorFragment = (PetCreatorFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (this.petCreatorFragment == null) {
            this.petCreatorFragment = PetCreatorFragment.newInstance();
            ActivityUtils.addFragmentToActivity(this.getSupportFragmentManager(),
                    this.petCreatorFragment, R.id.contentFrame);
        }
    }
}