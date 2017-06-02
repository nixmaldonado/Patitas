package com.example.patitas.infrastructure.persistance.firebase;


import android.net.Uri;

import com.example.patitas.DatabaseUtils;
import com.example.patitas.domain.pet.Pet;
import com.example.patitas.domain.pet.PetsRepository;
import com.example.patitas.infrastructure.listeners.FBPushOnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebasePetsRepository implements PetsRepository {

    private StorageReference  storageReference;
    private DatabaseReference databaseReference;

    public FirebasePetsRepository() {
        this.databaseReference = DatabaseUtils.getDatabase().getReference().child("pets");
        this.storageReference = FirebaseStorage.getInstance().getReference().child("pet_photos");
    }

    @Override
    public void save(final Pet pet) {
        Uri localImageUri = Uri.parse(pet.getLocalImageUri());

        this.storageReference
                .child(localImageUri.getLastPathSegment())
                .putFile(localImageUri)
                .addOnSuccessListener(new FBPushOnSuccessListener(pet, this.databaseReference));
    }
}
