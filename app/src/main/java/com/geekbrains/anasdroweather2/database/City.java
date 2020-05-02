package com.geekbrains.anasdroweather2.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {

    @PrimaryKey
    public long id;
    public String cityName;
    public int cityTemp;
    public int lastLoadTime;

}
