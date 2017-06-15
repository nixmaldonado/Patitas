package com.example.patitas.peteditor;

import com.example.patitas.data.Pet;
import com.example.patitas.data.source.PetsRepository;

public class PetEditorPresenter implements PetEditorContract.Presenter{

    private final PetsRepository petsRepository;

    private final PetEditorContract.View view;

    private String petId;

    public PetEditorPresenter(String petId, PetsRepository petsRepository, PetEditorContract.View view){
        this.petsRepository = petsRepository;
        this.view = view;
        this.petId = petId;

        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void savePet(Pet pet) {
        if(view.hasRequiredInput()){
            this.petsRepository.savePet(pet);}
    }




}
