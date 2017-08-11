package com.example.patitas.pets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.patitas.R;
import com.example.patitas.auth.SignInActivity;
import com.example.patitas.data.source.FirebasePetsRepository;
import com.example.patitas.petcreator.PetCreatorActivity;
import com.example.patitas.util.ActivityUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.patitas.R.id.fab;

public class PetsActivity extends AppCompatActivity  {

    private PetsFragment petsFragment = new PetsFragment();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pets);
        new PetsPresenter(this.petsFragment);
        ButterKnife.bind(this);
        this.addFragmentToPetActivity();
        FirebasePetsRepository.getInstance();

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.pets_toolbar);
        this.setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_pets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_log_in){
            Intent intent = new Intent(this, SignInActivity.class);
            this.startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(fab)
    protected void addPet() {
        if(SignInActivity.isUserSignedIn()){
            Intent editorIntent = new Intent(this, PetCreatorActivity.class);
            this.startActivityForResult(editorIntent, 1);
        }else{
            this.petsFragment.signInToast();
        }
    }

    private void addFragmentToPetActivity() {
        ActivityUtils.addFragmentToActivity(
                this.getSupportFragmentManager(), this.petsFragment, R.id.contentFrame);
    }
}
