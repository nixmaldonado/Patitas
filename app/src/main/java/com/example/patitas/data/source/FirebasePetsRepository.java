package com.example.patitas.data.source;

import android.net.Uri;

import com.example.patitas.data.Pet;
import com.example.patitas.util.FBPushOnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    private List<Pet> pets;

    private FirebasePetsRepository() {
        this.pets = new ArrayList<>();
        this.databaseReference = DatabaseUtils.getDatabase().getReference(PETS_REFERENCE);
        this.storageReference = FirebaseStorage.getInstance().getReference().child(PET_PHOTOS);
    }

    public static FirebasePetsRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void getPets(LoadPetsCallback callback) {
        checkNotNull(callback);

        this.pets.clear();
        this.databaseReference.addChildEventListener(new PetValueEventListener(callback));
    }

    @Override
    public Pet getPet(String petId) {
        return null;
    }

    @Override
    public void savePet(final Pet pet) {
        Uri localImageUri = Uri.parse(pet.getLocalImageUri());

        this.storageReference
                .child(localImageUri.getLastPathSegment())
                .putFile(localImageUri)
                .addOnSuccessListener(new FBPushOnSuccessListener(pet, this.databaseReference));
    }

    private class PetValueEventListener implements ChildEventListener {

        private LoadPetsCallback callback;

        PetValueEventListener(LoadPetsCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Pet pet = dataSnapshot.getValue(Pet.class);
            pet.setId(dataSnapshot.getKey());

            FirebasePetsRepository.this.pets.add(pet);
            this.callback.onPetsLoaded(FirebasePetsRepository.this.pets);
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
    }

}
