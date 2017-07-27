package com.example.patitas.petcreator;

import android.support.design.widget.FloatingActionButton;

import com.example.patitas.R;
import com.example.patitas.data.Pet;
import com.example.patitas.data.source.PetsRepository;

import butterknife.BindView;
import butterknife.OnClick;

public class PetCreatorPresenter implements PetCreatorContract.Presenter {

    private final PetsRepository petsRepository;

    private final PetCreatorContract.View view;


    public PetCreatorPresenter(PetsRepository petsRepository, PetCreatorContract.View view) {
        this.petsRepository = petsRepository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void createPet(String name, String imageUri) {
        if (!this.hasRequiredInput(name, imageUri)) {
            view.triggerToast(R.string.provide_fields);
            return;
        }
        this.petsRepository.savePet(new Pet(name, imageUri));
    }


    private boolean hasRequiredInput(String name, String imageUri) {
        return (name != null && imageUri != null);
    }
}
