package com.example.patitas.data.source;

import com.example.patitas.data.Pet;

import java.util.ArrayList;
import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class InMemoryPetsRepository implements PetsRepository {

    public static final InMemoryPetsRepository INSTANCE = new InMemoryPetsRepository();

    private List<Pet> pets = new ArrayList<>();

    private InMemoryPetsRepository() {
        Pet pet = new Pet("Arturito", "");
        pet.setRemoteImageUri("");

        this.pets.add(pet);
    }

    public static InMemoryPetsRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void getPets(LoadPetsCallback callback) {
        checkNotNull(callback);

        callback.onPetsLoaded(this.pets);
    }
}
