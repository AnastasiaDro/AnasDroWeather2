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


    //для первого дня
    //это 9 утра, 15 дня, и 21 - вечер



    public WeekDataParser(){
        myData = MyData.getInstance();
        allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
        twentyFourHours = 24;
        timeStep = 3;
    }

    public int[] findDayBeginningIndexes(){
        //получим время указанное в первом, втором и третьем элементах
        //индексы времени, из которых мы будем получать температуру
        int [] dayBeginningIndexesArr = new int[4];
        zeroArr = allWeatherDataHashMap.get(0);
        curTimeString = zeroArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY];
        curTimeString = curTimeString.substring(0, 2);
        curTime = Integer.parseInt(curTimeString);
        firstDayBeginIndex = (twentyFourHours - curTime)/3;
        dayBeginningIndexesArr[0] = firstDayBeginIndex;
        for (int i = 1; i < 4 ; i++) {
            dayBeginningIndexesArr[i] = dayBeginningIndexesArr[i-1]+8;
        }
        return dayBeginningIndexesArr;
    }

    //получим индексы заданного дня для 9.00, 15.00  и 21.00
    private int [] forDayTempDataIndexes(int dayBeginIndex) {
        int[] morAftEvIndexesArr = new int[3];
        morAftEvIndexesArr[0] = dayBeginIndex + 3;
        morAftEvIndexesArr[1] = dayBeginIndex + 5;
        morAftEvIndexesArr[2] = dayBeginIndex + 7;
        return morAftEvIndexesArr;
    }

    //нужно получить даты трех последних дней





    //получим данные по осадкам за все дни и выберем дождь, или экстрим



}
