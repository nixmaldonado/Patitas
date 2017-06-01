package com.example.patitas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private DatabaseReference petsDatabaseReference;
    private ChildEventListener childEventListener;
    private ProgressBar progressBar;

    private PetAdapter petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_pets_white_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editorIntent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }
        });


        ListView petList = (ListView) findViewById(R.id.pet_list);
        List<Pet> pets = new ArrayList<>();
        petAdapter = new PetAdapter(this, R.layout.pet_item, pets);

        petList.setAdapter(petAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        petsDatabaseReference = DatabaseUtils.getDatabase().getReference("pets");

        attachDatabaseReadListener();


        petList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent petItemIntent = new Intent(CatalogActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("pet", (Parcelable) petAdapter.getItem(position));
                petItemIntent.putExtras(bundle);
                startActivity(petItemIntent);
            }
        });
    }

    private void attachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Pet pet = dataSnapshot.getValue(Pet.class);
                    petAdapter.add(pet);
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
            petsDatabaseReference.addChildEventListener(childEventListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDatabaseReadListener();
        petAdapter.clear();
    }

    private void detachDatabaseReadListener() {
        if (childEventListener != null) {
            petsDatabaseReference.removeEventListener(childEventListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
