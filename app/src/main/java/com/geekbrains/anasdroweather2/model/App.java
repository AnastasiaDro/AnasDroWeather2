package com.geekbrains.anasdroweather2.model;

import android.app.Application;

import androidx.room.Room;

import com.geekbrains.anasdroweather2.database.CitiesDatabase;

public class App extends Application {

    public static App instance;
    MyData myData;
    private CitiesDatabase database;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, CitiesDatabase.class, "database")
                .build();
        //чтобы не пересоздавался myData
        myData = MyData.getInstance();
    }

    public static App getInstance() {
        return instance;
    }

    public CitiesDatabase getDatabase() {
        return database;
    }
}
