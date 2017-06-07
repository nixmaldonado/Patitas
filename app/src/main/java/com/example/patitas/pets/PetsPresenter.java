package com.example.patitas.pets;

import com.example.patitas.data.Pet;
import com.example.patitas.data.source.PetsDataSource;

import static dagger.internal.Preconditions.checkNotNull;

public class PetsPresenter implements PetsContract.Presenter {

    private final PetsDataSource petsDataSource;
    private final PetsContract.View petsView;
    private boolean firstLoad = true;


    public PetsPresenter(PetsContract.View petsView, PetsDataSource petsDataSource) {
        this.petsView = checkNotNull(petsView, "petsView cannot be null");
        this.petsDataSource = checkNotNull(petsDataSource, "petsRepository cannot be null");

        this.petsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadPets(false);
    }

    @Override
    public void openPetDetails(Pet requestedPet) {
        checkNotNull(requestedPet, "requested pet cannot be null!");
        this.petsView.showPetDetailsUi(requestedPet.getId());
    }

    @Override
    public void loadPets(boolean forceUpdate) {
        this.loadPets(forceUpdate || this.firstLoad, true);
        this.firstLoad = false;
    }

    private void loadPets(boolean forceUpdate, final boolean showLoadingUi) {
        if (showLoadingUi) {
            this.petsView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            this.petsDataSource.refreshPets();
        }
    }
}