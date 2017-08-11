package com.example.patitas.data.source;

import com.example.patitas.data.Pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static dagger.internal.Preconditions.checkNotNull;

class InMemoryPetsRepository implements PetsRepository {

    private static final InMemoryPetsRepository INSTANCE = new InMemoryPetsRepository();

    private Map<String, Pet> pets = new HashMap<>();

    private InMemoryPetsRepository() {
        Pet pet = new Pet("Arturito", "", "testUserId", "testUserName", "testUserPhone",
                "testUserDescription");
        pet.setRemoteImageUri("");
        pet.setId("1");

        this.pets.put(pet.getId(), pet);
    }

    public static InMemoryPetsRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void getPets(LoadPetsCallback callback) {
        checkNotNull(callback);

        callback.onPetsLoaded(new ArrayList<>(this.pets.values()));
    }

    @Override
    public void savePet(Pet pet) {

    }

    @Override
    public void getPet(String petId, LoadPetCallback callback) {
        callback.onPetLoaded(this.pets.get(petId));
    }
}
