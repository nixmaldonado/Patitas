package com.example.patitas.pets;

import com.example.patitas.data.Pet;

import static dagger.internal.Preconditions.checkNotNull;

class PetsPresenter implements PetsContract.Presenter {

    private final PetsContract.View petsView;

    PetsPresenter(PetsContract.View petsView) {
        this.petsView = checkNotNull(petsView, "petsView cannot be null");

        this.petsView.setPresenter(this);
    }


    public void openPetDetail(Pet pet) {
        this.petsView.showPetDetails(pet);
    }

    @Override
    public void start() {

    }
}