package com.geekbrains.anasdroweather2.model;

import android.app.Activity;

import androidx.navigation.NavController;

import com.geekbrains.anasdroweather2.interfaces.Observable;
import com.geekbrains.anasdroweather2.interfaces.Observer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;



//Класс с данными, наблюдаемый
public class MyData implements Observable {
    private static MyData instance;
    public List<Observer> observers;
    //узнаем время
    static Date currentDate;
    int currentHour;
    private ArrayList <String> citiesList;



    NavController navController;


    String currentCity;

    private MyData() {
        currentCity = "Moscow";
        currentHour = 0;
        observers = new LinkedList<>();
        citiesList = new ArrayList();
        citiesList.add("Moscow");
        citiesList.add("Saint-Petersburg");
        citiesList.add("Kazan");
        citiesList.add("Sochi");
    }


//сделаем наблюдаемый класс сингл-тоном
    public static MyData getInstance(){
        if (instance == null) {
            instance = new MyData();
        }
//получим текущий час
        currentDate = new Date();
        currentDate.getTime();
        //currentHour = takeCurrentHour(currentDate);
//вернём MyData
        return instance;
    }

//добавить наблюдателя
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("Наблюдатель добавлен. Список наблюдателей " + observers.toString());

    }

//удалить наблюдателя
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Наблюдатель удалён. Список наблюдателей " + observers.toString());
    }

//уведомить наблюдателей
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateViewData();
        }
    }
//данные о текущей погоде для CurrentWeatherFragment
    public void getDataForNow() {
        //заполнить
    }

//Высчитать текущий час
    private int takeCurrentHour(Date currentDate) {
        DateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String dateText = hourFormat.format(currentDate);
        currentHour = Integer.parseInt(dateText);
      //  Log.d("takeCurrentHour", String.valueOf(currentHour));
        System.out.println("время = " + currentHour);
        return currentHour;
    }

//геттер текущего часа
    public int getCurrentHour(){
        takeCurrentHour(currentDate);
        return currentHour;
    }

    public ArrayList getCitiesList(){
        return citiesList;
    }

    //получим или изменим текущий город
    public String getCurrentCity() {
        return currentCity;
    }


    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }


    public NavController getNavController() {
        return navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }



}
