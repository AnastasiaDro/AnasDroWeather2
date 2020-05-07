package com.geekbrains.anasdroweather2.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {

    @PrimaryKey (autoGenerate = true)
    public Integer id;

    public String getCityName() {
        return cityName;
    }

    @ColumnInfo
    public String cityName;
    public String cityTemp;
    public String lastLoadTime;



}
