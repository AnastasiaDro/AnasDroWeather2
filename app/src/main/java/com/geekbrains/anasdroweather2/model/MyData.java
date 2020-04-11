package com.geekbrains.anasdroweather2.model;

import android.util.Log;

import com.geekbrains.anasdroweather2.interfaces.Observable;
import com.geekbrains.anasdroweather2.interfaces.Observer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    static int currentHour;




    private MyData() {
        currentHour = 0;
        observers = new LinkedList<>();
    }


//сделаем наблюдаемый класс сингл-тоном
    public static MyData getInstance(){
        if (instance == null) {
            instance = new MyData();
        }
//получим текущий час
        currentDate = new Date();
        currentDate.getTime();
        currentHour = takeCurrentHour(currentDate);
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
    private static int takeCurrentHour(Date currentDate) {
        DateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String dateText = hourFormat.format(currentDate);
        currentHour = Integer.parseInt(dateText);
        Log.d("takeCurrentHour", String.valueOf(currentHour));

        return currentHour;
    }

//геттер текущего часа
    public int getCurrentHour(){
        return currentHour;
    }







}
