package com.example.patitas.pets;

import com.example.patitas.BasePresenter;
import com.example.patitas.BaseView;
import com.example.patitas.data.Pet;

import java.util.List;

interface PetsContract {

    interface View extends BaseView<Presenter>{
        void showPets(List<Pet> pets);

        void showNoPets();

        void showPetDetails(String petId);
    }

    interface Presenter extends BasePresenter{
        void loadPets();

        void openPetDetail(Pet pet);
    }

}
