package com.example.patitas.util;

import com.example.patitas.auth.AuthActivity;
import com.example.patitas.data.Pet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;


public class FBPushOnSuccessListener implements OnSuccessListener<UploadTask.TaskSnapshot> {

    private Pet pet;

    private DatabaseReference petsDatabaseReference;
    private DatabaseReference usersDatabaseReference;

    public FBPushOnSuccessListener(final Pet pet, final DatabaseReference petsDatabaseReference,
                                   final DatabaseReference usersDatabaseReference) {
        this.pet = pet;
        this.petsDatabaseReference = petsDatabaseReference;
        this.usersDatabaseReference = usersDatabaseReference;
    }

    @SuppressWarnings("VisibleForTests")
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        this.pet.setRemoteImageUri(taskSnapshot.getDownloadUrl().toString());
        this.petsDatabaseReference.push().setValue(this.pet);
        this.usersDatabaseReference
                .child(AuthActivity.getCurrentUserId()).push().setValue(this.pet);
    }
}
