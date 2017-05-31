package com.example.patitas;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {
    private String name;
    private String photoUrl;

    public Pet() {}

    public Pet(String name, String photUrl) {
        this.name = name;
        this.photoUrl = photUrl;
    }

    protected Pet(Parcel in) {
        name = in.readString();
        photoUrl = in.readString();
    }

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

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(photoUrl);
    }
}
