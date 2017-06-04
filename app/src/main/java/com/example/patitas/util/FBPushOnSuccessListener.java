package com.example.patitas.util;

import com.example.patitas.data.Pet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;


public class FBPushOnSuccessListener implements OnSuccessListener<UploadTask.TaskSnapshot> {

    private Pet pet;

    private DatabaseReference databaseReference;

    public FBPushOnSuccessListener(final Pet pet, final DatabaseReference databaseReference) {
        this.pet = pet;
        this.databaseReference = databaseReference;
    }

    @SuppressWarnings("VisibleForTests")
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        this.pet.setRemoteImageUri(taskSnapshot.getDownloadUrl().toString());
        this.databaseReference.push().setValue(this.pet);
    }
}
