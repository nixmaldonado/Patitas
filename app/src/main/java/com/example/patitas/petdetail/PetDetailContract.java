package com.example.patitas.petdetail;

import com.example.patitas.BasePresenter;
import com.example.patitas.BaseView;
import com.example.patitas.data.Pet;

public interface PetDetailContract {

    interface View extends BaseView<Presenter> {

        void showPetDetail(Pet pet);
    }

    interface Presenter extends BasePresenter {

    }
}
