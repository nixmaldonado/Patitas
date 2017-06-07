package com.example.patitas.pets;

import com.example.patitas.BasePresenter;
import com.example.patitas.BaseView;
import com.example.patitas.data.Pet;

import java.util.List;

public interface PetsContract {

    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);

        void showPets(List<Pet> pets);

        void showAddPets();

        void showPetDetailsUi(String petId);

        void showLoadingPetsError();

        void showNoPets();

        void showSuccessfullySavedMessage();

        boolean isActive();

    }

    interface Presenter extends BasePresenter{
        void openPetDetails(Pet requestedPet);

        void loadPets (boolean forceUpdate);


    }

}
