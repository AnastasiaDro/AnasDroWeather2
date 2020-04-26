package com.geekbrains.anasdroweather2.ui.home;

import com.geekbrains.anasdroweather2.model.MyData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class WeekDataParser {
    private MyData myData;
    private HashMap<Integer, String[]> allWeatherDataHashMap;
    private String[] zeroArr;
    private int curTime;
    private String curTimeString;
    private int twentyFourHours;
    //"Шаг" между значением времени в элементах массива
    //У нас он сейчас 3
    private int timeStep;

    private int firstDayBeginIndex;

    private ArrayList indexesForFourDaysList;



    public WeekDataParser() {
        myData = MyData.getInstance();
        indexesForFourDaysList = new ArrayList();
        twentyFourHours = 24;
        timeStep = 3;
    }

    public int[] findDayBeginningIndexesArr() {
        allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
        //получим время указанное в первом, втором и третьем элементах
        //индексы времени, из которых мы будем получать температуру
        int[] dayBeginningIndexesArr = new int[3];
        zeroArr = allWeatherDataHashMap.get(0);
        curTimeString = zeroArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY];
        curTimeString = curTimeString.substring(11, 13);
        curTime = Integer.parseInt(curTimeString);
        System.out.println("curTime= " + curTime);
        firstDayBeginIndex = (twentyFourHours - curTime) / 3;
        System.out.println("firstDayBeginIndex " + firstDayBeginIndex);
        dayBeginningIndexesArr[0] = firstDayBeginIndex;
        for (int i = 1; i < 3; i++) {
            dayBeginningIndexesArr[i] = dayBeginningIndexesArr[i - 1] + 8;
            System.out.println("Начальный индекс [" + i + "]" + dayBeginningIndexesArr[i]);
        }
        return dayBeginningIndexesArr;
    }

    //получим индексы заданного дня для 9.00, 15.00  и 21.00
//    public int[] forDayTempDataIndexes(int dayBeginIndex) {
//        int[] dayTempDataIndexesArr = new int[3];
//        dayTempDataIndexesArr[0] = dayBeginIndex + 3;
//        dayTempDataIndexesArr[1] = dayBeginIndex + 5;
//        dayTempDataIndexesArr[2] = dayBeginIndex + 7;
//        return dayTempDataIndexesArr;
//    }
}


