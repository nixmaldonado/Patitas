package com.example.patitas;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtils {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase(){
        if (mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
