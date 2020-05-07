package com.geekbrains.anasdroweather2.model;

import android.util.Log;

import com.geekbrains.anasdroweather2.database.City;
import com.geekbrains.anasdroweather2.database.CityDao;

import java.util.ArrayList;
import java.util.List;

public class DbLoaderThread extends Thread {

    CityDao cityDao;
    List <City> dataList;
    MyData myData;
//выгружает данные их базы данных
    public DbLoaderThread (CityDao cityDao, List dataList) {
        this.cityDao = cityDao;
        this.dataList = dataList;
    }

//    public DbLoaderThread (CityDao cityDao, MyData myData) {
//        this.cityDao = cityDao;
//        this.myData = myData;
//    }

    @Override
    public void run() {
        super.run();
        dataList.addAll(cityDao.getAll());
        Log.d("DbLoaderThread", "Размер выгруженных данных: " + dataList.size());
    }

//    @Override
//    public void run() {
//        super.run();
//        myData.c
//        dataList = cityDao.getAll();
//        Log.d("DbLoaderThread", "Размер выгруженных данных: " + dataList.size());
//    }

}
