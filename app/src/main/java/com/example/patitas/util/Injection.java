package com.example.patitas.util;

import com.example.patitas.data.source.FirebasePetsRepository;
import com.example.patitas.data.source.PetsRepository;


public class Injection {

    public static PetsRepository providePetsRepository() {
        return FirebasePetsRepository.getInstance();
    }
}
