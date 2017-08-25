package com.example.patitas.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {

    public static final Parcelable.Creator<Pet> CREATOR = new Parcelable.Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel source) {
            return new Pet(source);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    private String id;
    private String petName;
    private String userId;
    private String localImageUri;
    private String remoteImageUri;
    private String petUserName;
    private String petUserPhone;
    private String petDescription;

    public Pet() {
    }

    public Pet(final String name, final String localImageUri,
               final String userId, final String petUserName,
               final String petUserPhone, final String petDescription) {
        this.petName = name;
        this.userId = userId;
        this.localImageUri = localImageUri;
        this.petUserName = petUserName;
        this.petUserPhone = petUserPhone;
        this.petDescription = petDescription;
    }

    protected Pet(Parcel in) {
        this.id = in.readString();
        this.petName = in.readString();
        this.localImageUri = in.readString();
        this.remoteImageUri = in.readString();
        this.userId = in.readString();
        this.petUserName = in.readString();
        this.petUserPhone = in.readString();
        this.petDescription = in.readString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPetName() {
        return this.petName;
    }

    public String getRemoteImageUri() {
        return this.remoteImageUri;
    }

    public void setRemoteImageUri(String remoteImageUri) {
        this.remoteImageUri = remoteImageUri;
    }

    public String getLocalImageUri() {
        return this.localImageUri;
    }

    public String getPetUserName() {
        return this.petUserName;
    }

    public String getUserPhone(){return this.petUserPhone;}

    public String getPetDescription(){return this.petDescription;}

    @Override
    public boolean equals(Object obj) {
        final Pet pet = (Pet) obj;
        return this.getId().equals(pet.getId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.petName);
        dest.writeString(this.localImageUri);
        dest.writeString(this.remoteImageUri);
        dest.writeString(this.userId);
        dest.writeString(this.petUserName);
        dest.writeString(this.petUserPhone);
        dest.writeString(this.petDescription);
    }
}
