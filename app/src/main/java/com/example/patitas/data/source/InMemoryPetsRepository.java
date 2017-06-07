package com.example.patitas.data.source;

import com.example.patitas.data.Pet;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPetsRepository implements PetsRepository {

    private List<Pet> pets = new ArrayList<>();

    public InMemoryPetsRepository() {
        Pet pet = new Pet("Arturito", "");
        pet.setRemoteImageUri("");

        this.pets.add(pet);
    }

    public List<Pet> getPets() {
        return this.pets;
    }
}
