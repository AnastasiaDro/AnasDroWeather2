package com.geekbrains.anasdroweather2.ui.home;

import com.geekbrains.anasdroweather2.model.MyData;

import java.util.HashMap;

public class WeekDataParser {
    MyData myData;
    HashMap <Integer, String[]> allWeatherDataHashMap;
    String [] zeroArr;
    int curTime;
    String curTimeString;
    int twentyFourHours;
    //"Шаг" между значением времени в элементах массива
    //У нас он сейчас 3
    int timeStep;

    int firstDayBeginIndex;

    //индексы времени, из которых мы будем получать температуру
    //для первого дня
    //это 9 утра, 15 дня, и 21 - вечер
    int firstDMorIndex;
    int firstDDayIndex;
    int firstDEvIndex;


    public WeekDataParser(){
        myData = MyData.getInstance();
        allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
        twentyFourHours = 24;
        timeStep = 3;
    }

    public void findFirstDayBeginning(){
        //получим время указанное в первом элементе
        zeroArr = allWeatherDataHashMap.get(0);
        curTimeString = zeroArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY];
        curTimeString = curTimeString.substring(0, 2);
        curTime = Integer.parseInt(curTimeString);
        firstDayBeginIndex = (twentyFourHours - curTime)/3;
    }

    //запустить после findFirstDayBeginning()
    private void forDayTempDataIndexes(int dayBeginIndex){
        firstDMorIndex = dayBeginIndex + 3;
        firstDDayIndex = dayBeginIndex + 5;
        firstDEvIndex = dayBeginIndex + 7;
    }

}
