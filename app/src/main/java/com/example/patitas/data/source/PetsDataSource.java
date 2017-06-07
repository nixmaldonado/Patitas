package com.example.patitas.data.source;


import com.example.patitas.data.Pet;

public interface PetsDataSource {

    void save(final Pet pet);

    void refreshPets();
}
