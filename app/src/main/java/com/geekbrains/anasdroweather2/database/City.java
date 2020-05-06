package com.geekbrains.anasdroweather2.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {

    @PrimaryKey
    public long id;

    public String getCityName() {
        return cityName;
    }

    public String cityName;
    public String cityTemp;
    public String lastLoadTime;



}
