package com.example.patitas.pets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.patitas.R;
import com.example.patitas.data.source.InMemoryPetsRepository;
import com.example.patitas.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetsActivity extends AppCompatActivity {

    /*public static final String DATABASE_PETS_REFERENCE = "pets";

    public static final String PET_MODEL_KEY = "pet";*/

    /*@BindView(R.id.fab)
    FloatingActionButton fab;*/

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PetsPresenter petsPresenter;

    /*private DatabaseReference petsDatabaseReference;*/

    /*private ChildEventListener childEventListener;*/

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

        petsPresenter = new PetsPresenter(new InMemoryPetsRepository(), petsFragment);
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

    /*private void attachDatabaseReadListener() {
        if (this.childEventListener != null) return;

        this.childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PetsActivity.this.petAdapter.add(dataSnapshot.getValue(Pet.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        this.petsDatabaseReference.addChildEventListener(this.childEventListener);
    }*/

    /*private void detachDatabaseReadListener() {
        if (this.childEventListener != null) {
            this.petsDatabaseReference.removeEventListener(this.childEventListener);
        }
    }*/

}
