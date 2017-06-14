package com.example.patitas.data.source;

import com.example.patitas.data.Pet;

import java.util.List;


public interface PetsRepository {

    Pet getPet(String petId);

    interface LoadPetsCallback {

        void onPetsLoaded(List<Pet> pets);
    }

    void getPets(LoadPetsCallback callback);

    void savePet(Pet pet);
}
