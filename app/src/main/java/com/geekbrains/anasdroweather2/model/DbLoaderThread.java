package com.geekbrains.anasdroweather2.model;

import com.geekbrains.anasdroweather2.database.City;
import com.geekbrains.anasdroweather2.database.CityDao;

import java.util.ArrayList;
import java.util.List;

public class DbLoaderThread extends Thread {

    CityDao cityDao;
    List <City> dataList;
//выгружает данные их базы данных
    public DbLoaderThread (CityDao cityDao, List dataList) {
        this.cityDao = cityDao;
        this.dataList = dataList;
    }


    @Override
    public void run() {
        super.run();
        dataList = cityDao.getAll();
    }
}
