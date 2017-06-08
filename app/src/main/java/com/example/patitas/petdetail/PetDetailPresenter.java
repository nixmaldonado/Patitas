package com.example.patitas.petdetail;


import com.example.patitas.data.Pet;
import com.example.patitas.data.source.PetsRepository;

class PetDetailPresenter implements PetDetailContract.Presenter {

    private final PetsRepository petsRepository;

    private final PetDetailContract.View view;

    private String petId;

    public PetDetailPresenter(String petId, PetsRepository petsRepository, PetDetailContract.View view) {
        this.petsRepository = petsRepository;
        this.petId = petId;
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void start() {
        this.petsRepository.getPet(this.petId, new PetsRepository.LoadPetCallback() {

            @Override
            public void onPetLoaded(Pet pet) {
                PetDetailPresenter.this.view.showPetDetail(pet);
            }
        });
    }
}
