package com.example.patitas.pets;

import com.example.patitas.data.Pet;
import com.example.patitas.data.source.PetsRepository;

import static dagger.internal.Preconditions.checkNotNull;

class PetsPresenter implements PetsContract.Presenter {

    private final PetsRepository petsRepository;

    private final PetsContract.View petsView;

    PetsPresenter(PetsRepository petsRepository, PetsContract.View petsView) {
        this.petsView = checkNotNull(petsView, "petsView cannot be null");
        this.petsRepository = checkNotNull(petsRepository, "petsRepository cannot be null");

        this.petsView.setPresenter(this);
    }


    @Override
    public void openPetDetail(Pet pet) {
        this.petsView.showPetDetails(pet.getId());
    }

    @Override
    public void start() {

    }
}