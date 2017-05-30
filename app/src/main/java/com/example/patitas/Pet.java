package com.example.patitas;

public class Pet {
    private String name;
    private String photoUrl;

    public Pet(){}

    public Pet(String name, String photUrl){
        this.name = name;
        this.photoUrl = photUrl;
    }

    public String getName(){
        return name;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }

}
