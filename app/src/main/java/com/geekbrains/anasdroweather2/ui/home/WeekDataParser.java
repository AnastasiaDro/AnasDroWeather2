package com.geekbrains.anasdroweather2.ui.home;

import com.geekbrains.anasdroweather2.model.MyData;

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
    private int[] dayBeginningIndexesArr;
    private int[] morAftEvIndexesArr;
    private HashMap indexesForFourDaysHashMap;
    //для первого дня
    //это 9 утра, 15 дня, и 21 - вечер


    public WeekDataParser() {
        myData = MyData.getInstance();
        indexesForFourDaysHashMap = new HashMap();
        twentyFourHours = 24;
        timeStep = 3;
    }

    private void findDayBeginningIndexes() {
        allWeatherDataHashMap = myData.getAllWeatherDataHashMap();
        //получим время указанное в первом, втором и третьем элементах
        //индексы времени, из которых мы будем получать температуру
        dayBeginningIndexesArr = new int[3];
        zeroArr = allWeatherDataHashMap.get(0);
        curTimeString = zeroArr[Constants.TIME_KEY_IN_WEATHERDATA_ARRAY];
        curTimeString = curTimeString.substring(0, 2);
        curTime = Integer.parseInt(curTimeString);
        firstDayBeginIndex = (twentyFourHours - curTime) / 3;
        dayBeginningIndexesArr[0] = firstDayBeginIndex;
        for (int i = 1; i < 3; i++) {
            dayBeginningIndexesArr[i] = dayBeginningIndexesArr[i - 1] + 8;
        }
    }

    //получим индексы заданного дня для 9.00, 15.00  и 21.00
    private int[] forDayTempDataIndexes(int dayBeginIndex) {
        morAftEvIndexesArr = new int[3];
        try {
            morAftEvIndexesArr[0] = dayBeginIndex + 3;
            morAftEvIndexesArr[1] = dayBeginIndex + 5;
            morAftEvIndexesArr[2] = dayBeginIndex + 7;
        } catch (NullPointerException e) {

        }
        return morAftEvIndexesArr;
    }

    //получим ХэшМап с данными для каждого дня
    public HashMap takeForEachDayIndexMap() {
        findDayBeginningIndexes();
        for (int i = 0; i < dayBeginningIndexesArr.length; i++) {
            indexesForFourDaysHashMap.put(dayBeginningIndexesArr[i], forDayTempDataIndexes(dayBeginningIndexesArr[i]));
        }
        return indexesForFourDaysHashMap;
    }

}


    //получим данные по осадкам за все дни и выберем дождь, или экстрим



