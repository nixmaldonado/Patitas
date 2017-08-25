package com.example.patitas.pets;

import com.example.patitas.BasePresenter;
import com.example.patitas.BaseView;
import com.example.patitas.data.Pet;

interface PetsContract {

    interface View extends BaseView<Presenter>{

        void showPetDetails(Pet pet);

        void refreshView();
    }


    interface Presenter extends BasePresenter{

        void openPetDetail(Pet pet);
    }

}
