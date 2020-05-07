package com.geekbrains.anasdroweather2.model;

import android.util.Log;
import com.geekbrains.anasdroweather2.database.City;
import com.geekbrains.anasdroweather2.database.CityDao;
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
        dataList.addAll(cityDao.getAll());
        Log.d("DbLoaderThread", "Размер выгруженных данных: " + dataList.size());
    }
}
