package com.example.patitas.petcreator;

import com.example.patitas.BasePresenter;
import com.example.patitas.BaseView;

interface PetCreatorContract {

    interface View extends BaseView<Presenter>{

        void triggerToast(int message);

        String getName();

        String getImageUri();
    }
    interface Presenter extends BasePresenter{

        void createPet(String name, String imageUri, String currentUserId, String userName);

    }
}
