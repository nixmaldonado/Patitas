package com.example.patitas.data;


import android.net.Uri;

import com.example.patitas.util.FBPushOnSuccessListener;
import com.example.patitas.pets.PetsRepository;
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
