package com.example.patitas.data.source;

import com.example.patitas.data.Pet;

import java.util.List;


public interface PetsRepository {

    void getPets(LoadPetsCallback callback);

    interface LoadPetsCallback {

        void onPetsLoaded(List<Pet> pets);

    }

    void getPet(String petId, LoadPetCallback callback);

    interface LoadPetCallback {

        void onPetLoaded(Pet pet);
    }

    void savePet(Pet pet);
}
