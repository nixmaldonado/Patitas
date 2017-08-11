package com.example.patitas.petcreator;

import com.example.patitas.R;
import com.example.patitas.data.Pet;
import com.example.patitas.data.source.PetsRepository;

class PetCreatorPresenter implements PetCreatorContract.Presenter {

    private final PetsRepository petsRepository;

    private final PetCreatorContract.View view;

    PetCreatorPresenter(PetsRepository petsRepository, PetCreatorContract.View view) {
        this.petsRepository = petsRepository;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void createPet(String name, String imageUri, String currentUserId, String petUserName,
                          String petUserPhone, String petDescription) {
        if (!this.hasRequiredInput(name, imageUri)) {
            this.view.triggerToast(R.string.provide_fields);
            return;
        }
        this.petsRepository.savePet(new Pet(name, imageUri, currentUserId, petUserName,
                petUserPhone, petDescription));
    }


    private boolean hasRequiredInput(String name, String imageUri) {
        return (name != null && imageUri != null);
    }
}
