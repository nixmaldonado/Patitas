package com.example.patitas.peteditor;

import com.example.patitas.BasePresenter;
import com.example.patitas.BaseView;
import com.example.patitas.data.Pet;

public interface PetEditorContract {

    interface View extends BaseView<Presenter>{
        void showPetEditor(Pet pet);

        boolean hasRequiredInput();

        void setPresenter();
    }

    interface Presenter extends BasePresenter{
        void savePet(Pet pet);
    }
}
