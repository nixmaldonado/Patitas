package com.example.patitas.data;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtils {

    private static FirebaseDatabase database;

    public static FirebaseDatabase getDatabase(){
        if (database == null){
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }

        return database;
    }
}
