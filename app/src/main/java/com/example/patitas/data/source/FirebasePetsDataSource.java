package com.example.patitas.data.source;


import android.net.Uri;

import com.example.patitas.data.Pet;
import com.example.patitas.util.FBPushOnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebasePetsDataSource implements PetsDataSource {

    private StorageReference  storageReference;
    private DatabaseReference databaseReference;

    public FirebasePetsDataSource() {
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

    @Override
    public void refreshPets() {

    }
}
