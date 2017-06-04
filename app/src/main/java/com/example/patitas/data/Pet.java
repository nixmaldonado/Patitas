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

    private String name;

    private String localImageUri;

    private String remoteImageUri;

    public Pet() {}

    public Pet(final String name, final String localImageUri) {
        this.name = name;
        this.localImageUri = localImageUri;
    }

    protected Pet(Parcel input) {
        this.name = input.readString();
        this.remoteImageUri = input.readString();
        this.localImageUri = input.readString();
    }

    public void setRemoteImageUri(String remoteImageUri) {
        this.remoteImageUri = remoteImageUri;
    }

    public String getName() {
        return this.name;
    }

    public String getRemoteImageUri() {
        return this.remoteImageUri;
    }

    public String getLocalImageUri() {
        return this.localImageUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.remoteImageUri);
        dest.writeString(this.localImageUri);
    }
}
