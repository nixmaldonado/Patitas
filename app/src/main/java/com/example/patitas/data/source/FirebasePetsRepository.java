package com.example.patitas.data.source;

import com.example.patitas.data.Pet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class FirebasePetsRepository implements PetsRepository{

    private static final FirebasePetsRepository INSTANCE = new FirebasePetsRepository();

    private static final String PETS_REFERENCE = "pets";

    private DatabaseReference databaseReference;

    private List<Pet> pets;

    private FirebasePetsRepository() {
        this.pets = new ArrayList<>();
        this.databaseReference = FirebaseDatabase.getInstance().getReference(PETS_REFERENCE);
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
