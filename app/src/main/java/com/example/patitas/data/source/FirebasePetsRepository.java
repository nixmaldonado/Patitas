package com.example.patitas.data.source;

import android.net.Uri;

import com.example.patitas.auth.AuthActivity;
import com.example.patitas.data.Pet;
import com.example.patitas.pets.PetsAdapter;
import com.example.patitas.pets.UserPetsAdapter;
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

public class FirebasePetsRepository implements PetsRepository {

    private static final FirebasePetsRepository INSTANCE = new FirebasePetsRepository();

    private static final String PET_PHOTOS = "pet_photos";
    private static final String PETS_REFERENCE = "pets";
    private static final String USERS_REFERENCE = "users";
    public List<Pet> pets;
    private DatabaseReference petsDatabaseReference;
    private DatabaseReference usersDatabaseReference;
    private StorageReference storageReference;
    private List<Pet> userPets;

    private FirebasePetsRepository() {
        this.pets = new ArrayList<>();

        this.storageReference = FirebaseStorage.getInstance().getReference().child(PET_PHOTOS);

        this.usersDatabaseReference = DatabaseUtils.getDatabase().getReference().child(USERS_REFERENCE);

        this.petsDatabaseReference = DatabaseUtils.getDatabase().getReference(PETS_REFERENCE);

        this.petsDatabaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pet pet = dataSnapshot.getValue(Pet.class);
                pet.setId(dataSnapshot.getKey());

                FirebasePetsRepository.this.pets.add(0, pet);
                PetsAdapter.getInstance().replaceData(FirebasePetsRepository.this.pets);
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
        this.petsDatabaseReference.child(petId).addListenerForSingleValueEvent(new ValueEventListener() {
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
        Uri imageUri = Uri.parse(pet.getLocalImageUri());

        this.storageReference
                .child(imageUri.getLastPathSegment())
                .putFile(imageUri)
                .addOnSuccessListener(new FBPushOnSuccessListener(pet,
                        this.petsDatabaseReference, this.usersDatabaseReference));
    }

    public DatabaseReference getUsersDatabaseReference() {
        return this.usersDatabaseReference;
    }

    public void setListenerForUserPets() {
        this.userPets = new ArrayList<>();
        this.usersDatabaseReference.child(AuthActivity.getCurrentUserId())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        FirebasePetsRepository.this.userPets.add(0, dataSnapshot.getValue(Pet.class));

                        UserPetsAdapter.getInstance()
                                .replacePetsData(FirebasePetsRepository.this.userPets);
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
                });
    }

}
