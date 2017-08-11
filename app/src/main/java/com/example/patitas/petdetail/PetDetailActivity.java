package com.example.patitas.petdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.patitas.R;
import com.example.patitas.data.Pet;
import com.example.patitas.pets.PetsFragment;
import com.example.patitas.util.ActivityUtils;

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

        Pet pet = this.getIntent().getParcelableExtra(PetsFragment.EXTRA_PET);

        new PetDetailPresenter(
                pet,
                petDetailFragment);

        this.setSupportActionBar((Toolbar) this.findViewById(R.id.detail_toolbar));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
