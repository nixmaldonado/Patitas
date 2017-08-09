package com.example.patitas.petcreator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.patitas.R;
import com.example.patitas.util.ActivityUtils;
import com.example.patitas.util.Injection;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PetCreatorActivity extends AppCompatActivity {

    private PetCreatorFragment petCreatorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_creator);
        ButterKnife.bind(this);

        this.setFragment();

        new PetCreatorPresenter(Injection.providePetsRepository(), this.petCreatorFragment);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.creator_toolbar);
        toolbar.setTitle(this.getString(R.string.add_new_pet));
        this.setSupportActionBar(toolbar);
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

    @OnClick(R.id.done_creating)
    public void create_pet(){
        this.petCreatorFragment.done();
    }
}