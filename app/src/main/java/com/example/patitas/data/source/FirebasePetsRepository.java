package com.example.patitas.data.source;

import android.net.Uri;

import com.example.patitas.data.Pet;
import com.example.patitas.pets.PetsAdapter;
import com.example.patitas.util.FBPushOnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class FirebasePetsRepository implements PetsRepository{

    private static final FirebasePetsRepository INSTANCE = new FirebasePetsRepository();

    private static final String PET_PHOTOS = "pet_photos";
    private static final String PETS_REFERENCE = "pets";

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public List<Pet> pets;


    private FirebasePetsRepository() {
        this.pets = new ArrayList<>();
        this.databaseReference = DatabaseUtils.getDatabase().getReference(PETS_REFERENCE);
        this.storageReference = FirebaseStorage.getInstance().getReference().child(PET_PHOTOS);
        this.databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pet pet = dataSnapshot.getValue(Pet.class);
                pet.setId(dataSnapshot.getKey());

                FirebasePetsRepository.this.pets.add(pet);
                PetsAdapter.getInstance().replaceData(FirebasePetsRepository.this.pets);
                PetsAdapter.getInstance().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Pet pet = dataSnapshot.getValue(Pet.class);
                pet.setId(dataSnapshot.getKey());

                FirebasePetsRepository.this.pets.remove(pet);
                PetsAdapter.getInstance().replaceData(FirebasePetsRepository.this.pets);
                PetsAdapter.getInstance().notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static FirebasePetsRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void getPets(LoadPetsCallback callback) {
        checkNotNull(callback);

        this.pets.clear();

    }

    @Override
    public void getPet(String petId, final LoadPetCallback callback) {
        this.databaseReference.child(petId).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onPetLoaded(dataSnapshot.getValue(Pet.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void savePet(final Pet pet) {
        Uri localImageUri = Uri.parse(pet.getLocalImageUri());

        this.storageReference
                .child(localImageUri.getLastPathSegment())
                .putFile(localImageUri)
                .addOnSuccessListener(new FBPushOnSuccessListener(pet, this.databaseReference));
    }

}
