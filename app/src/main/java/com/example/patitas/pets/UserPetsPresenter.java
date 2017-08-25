package com.example.patitas.pets;

import com.example.patitas.auth.AuthActivity;
import com.example.patitas.data.Pet;
import com.example.patitas.data.source.FirebasePetsRepository;

import static dagger.internal.Preconditions.checkNotNull;

class UserPetsPresenter implements PetsContract.Presenter {

    UserPetsPresenter(PetsContract.View userPetsView) {

        PetsContract.View userPetsFragment = checkNotNull(userPetsView, "userPetsView cannot be null");

        userPetsFragment.setPresenter(this);

        FirebasePetsRepository.getInstance().getUsersDatabaseReference();
    }

    @Override
    public void start() {
        if (AuthActivity.isUserSignedIn()) {
            FirebasePetsRepository.getInstance().setListenerForUserPets();
        }
    }

    @Override
    public void openPetDetail(Pet pet) {

    }
}
