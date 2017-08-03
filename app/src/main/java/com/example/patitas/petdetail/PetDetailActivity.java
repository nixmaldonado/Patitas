package com.example.patitas.petdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.patitas.R;
import com.example.patitas.pets.PetsFragment;
import com.example.patitas.util.ActivityUtils;
import com.example.patitas.util.Injection;

import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        PetDetailFragment petDetailFragment = (PetDetailFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (petDetailFragment == null) {
            petDetailFragment = PetDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    this.getSupportFragmentManager(), petDetailFragment, R.id.contentFrame);
        }

        String petId = this.getIntent().getStringExtra(PetsFragment.EXTRA_PET_ID);

        new PetDetailPresenter(
                petId,
                Injection.providePetsRepository(),
                petDetailFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
}
