package com.geekbrains.anasdroweather2.model;

import android.app.Activity;

import androidx.navigation.NavController;

import com.geekbrains.anasdroweather2.R;
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
    NavController navController;

    private static MyData instance;
    public List<Observer> observers;
    //узнаем время
    static Date currentDate;
    int currentHour;
    private ArrayList <String> citiesList;
    private int[] lastSearchCitiesArr;
    String currentCity;

    //текущая погодные данные
    String currentTemp;
    String currentPressure;
    String currentWind;

    //ближайшие часы (время)
    String f_soonTime;
    String s_soonTime;
    String th_soonTime;

    //температура в ближайшие часы
    String f_soonTemp;
    String s_soonTemp;
    String th_soonTemp;



    private MyData() {
        currentCity = "Moscow";
        currentHour = 0;
        observers = new LinkedList<>();
        citiesList = new <String> ArrayList();
        citiesList.add("Moscow");
        citiesList.add("Saint-Petersburg");
        citiesList.add("Kazan");
        citiesList.add("Sochi");
//массив для сохранения последних городов

        //пока зададим города тут
        lastSearchCitiesArr = new int[]{R.string.moscow, R.string.kazan, R.string.spb};

        currentTemp = null;
        currentPressure = null;
        currentWind = null;



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


    //установка погодных данных


    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setCurrentPressure(String currentPressure) {
        this.currentPressure = currentPressure;
    }

    public void setCurrentWind(String currentWind) {
        this.currentWind = currentWind;
    }

    public void setF_soonTime(String f_soonTime) {
        this.f_soonTime = f_soonTime;
    }

    public void setS_soonTime(String s_soonTime) {
        this.s_soonTime = s_soonTime;
    }

    public void setTh_soonTime(String th_soonTime) {
        this.th_soonTime = th_soonTime;
    }

    public void setF_soonTemp(String f_soonTemp) {
        this.f_soonTemp = f_soonTemp;
    }

    public void setS_soonTemp(String s_soonTemp) {
        this.s_soonTemp = s_soonTemp;
    }

    public void setTh_soonTemp(String th_soonTemp) {
        this.th_soonTemp = th_soonTemp;
    }

    //Забор погодных данных


    public String getCurrentTemp() {
        return currentTemp;
    }

    public String getCurrentPressure() {
        return currentPressure;
    }

    public String getCurrentWind() {
        return currentWind;
    }

    public String getF_soonTime() {
        return f_soonTime;
    }

    public String getS_soonTime() {
        return s_soonTime;
    }

    public String getTh_soonTime() {
        return th_soonTime;
    }

    public String getF_soonTemp() {
        return f_soonTemp;
    }

    public String getS_soonTemp() {
        return s_soonTemp;
    }

    public String getTh_soonTemp() {
        return th_soonTemp;
    }
}
