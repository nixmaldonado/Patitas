package com.example.patitas.pets;

import com.example.patitas.data.Pet;
import com.example.patitas.data.source.PetsRepository;

import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class PetsPresenter implements PetsContract.Presenter {

    private final PetsRepository petsRepository;

    private final PetsContract.View petsView;

    public PetsPresenter(PetsRepository petsRepository, PetsContract.View petsView) {
        this.petsView = checkNotNull(petsView, "petsView cannot be null");
        this.petsRepository = checkNotNull(petsRepository, "petsRepository cannot be null");

        this.petsView.setPresenter(this);
    }

    @Override
    public void start() {
        this.loadPets();
    }

    @Override
    public void loadPets() {
        this.petsRepository.getPets(new PetsRepository.LoadPetsCallback() {
            @Override
            public void onPetsLoaded(List<Pet> pets) {
                PetsPresenter.this.processPets(pets);
            }
        });
    }

    @Override
    public void openPetDetail(Pet pet) {
        this.petsView.showPetDetails(pet.getId());
    }

    private void processPets(List<Pet> pets) {
        if (pets.isEmpty())
            this.petsView.showNoPets();
        else
            this.petsView.showPets(pets);
    }
}