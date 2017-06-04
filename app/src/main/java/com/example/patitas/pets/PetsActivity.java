package com.example.patitas.pets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.patitas.DatabaseUtils;
import com.example.patitas.PetAdapter;
import com.example.patitas.R;
import com.example.patitas.activities.DetailActivity;
import com.example.patitas.activities.EditorActivity;
import com.example.patitas.domain.pet.Pet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class PetsActivity extends AppCompatActivity {

    public static final String DATABASE_PETS_REFERENCE = "pets";

    public static final String PET_MODEL_KEY = "pet";

    @BindView(R.id.pet_list)
    ListView petList;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    PetAdapter petAdapter;

    private DatabaseReference petsDatabaseReference;

    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        this.initToolbar();

        this.petAdapter = new PetAdapter(this, R.layout.pet_item, new ArrayList<Pet>());
        this.petList.setAdapter(this.petAdapter);

        this.petsDatabaseReference = DatabaseUtils.getDatabase()
                .getReference(DATABASE_PETS_REFERENCE);
        this.attachDatabaseReadListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.detachDatabaseReadListener();
    }

    private void initToolbar() {
        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setLogo(R.drawable.ic_pets_white_24dp);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @OnItemClick(R.id.pet_list)
    protected void showPet(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PET_MODEL_KEY, this.petAdapter.getItem(position));

        this.startActivity(new Intent(this, DetailActivity.class).putExtras(bundle));
    }

    @OnClick(R.id.fab)
    protected void addPet() {
        Intent editorIntent = new Intent(this, EditorActivity.class);
        this.startActivity(editorIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_catalog, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void attachDatabaseReadListener() {
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
    }

    private void detachDatabaseReadListener() {
        if (this.childEventListener != null) {
            this.petsDatabaseReference.removeEventListener(this.childEventListener);
        }
    }

}
