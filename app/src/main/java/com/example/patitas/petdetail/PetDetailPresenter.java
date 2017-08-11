package com.example.patitas.petdetail;


import com.example.patitas.data.Pet;
import com.example.patitas.data.source.PetsRepository;

class PetDetailPresenter implements PetDetailContract.Presenter {


    private final PetDetailContract.View view;

    private String petId;
    private Pet pet;

    public PetDetailPresenter(String petId, PetsRepository petsRepository, PetDetailContract.View view) {
        this.petId = petId;
        this.view = view;


    }

    public PetDetailPresenter(Pet pet, PetDetailContract.View view){
        this.pet = pet;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {
                PetDetailPresenter.this.view.showPetDetail(this.pet);
    }
 }

