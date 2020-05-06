package com.geekbrains.anasdroweather2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//Основной класс по работе с базой данных
//В параметрах аннотации Database указываем, какие Entity будут использоваться,
// и версию базы.
// Для каждого Entity класса из списка entities будет создана таблица.

//В Database классе необходимо описать абстрактные методы
// для получения Dao объектов, которые вам понадобятся.
@Database(entities = {City.class}, version = 1)
public abstract class CitiesDatabase  extends RoomDatabase {
    public abstract CityDao cityDao();
}
