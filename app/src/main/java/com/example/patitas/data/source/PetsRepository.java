package com.example.patitas.data.source;

import com.example.patitas.data.Pet;

import java.util.List;


public interface PetsRepository {

    interface LoadPetsCallback {

        void onPetsLoaded(List<Pet> pets);
    }

    void getPets(LoadPetsCallback callback);
}
