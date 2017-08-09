package com.example.patitas.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {

    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };

    private String id;

    private String petName;

    private String localImageUri;

    private String remoteImageUri;

    private String userId;

    private String petUserName;

    public Pet() {
    }

    public Pet(final String name, final String localImageUri, final String userId, final String petUserName) {
        this.petName = name;
        this.userId = userId;
        this.localImageUri = localImageUri;
        this.petUserName = petUserName;
    }

    protected Pet(Parcel input) {
        this.petName = input.readString();
        this.userId = input.readString();
        this.remoteImageUri = input.readString();
        this.localImageUri = input.readString();
        this.petUserName = input.readString();
    }

    public void setRemoteImageUri(String remoteImageUri) {
        this.remoteImageUri = remoteImageUri;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
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

    public String getLocalImageUri() {
        return this.localImageUri;
    }

    public String getPetUserName(){ return this.petUserName; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.petName);
        dest.writeString(this.remoteImageUri);
        dest.writeString(this.localImageUri);
        dest.writeString(this.userId);
    }

    @Override
    public boolean equals(Object obj) {
        final Pet pet = (Pet) obj;
        return this.getId().equals(pet.getId());
    }
}
