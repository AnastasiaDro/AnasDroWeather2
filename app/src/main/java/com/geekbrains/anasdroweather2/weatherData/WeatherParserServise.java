package com.geekbrains.anasdroweather2.weatherData;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.geekbrains.anasdroweather2.model.MyData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class WeatherParserServise extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


    private String gotTime;
    private String gotTemp;
    private String gotPressure;
    private String gotWind;
    HashMap <Integer, String[]> takenWeatherData;

//    private String currentTime;
//    private String currentTemp;
//    private String currentPressure;
//    private String currentWind;

//    //ближайшие часы (время)
//    private String f_soonTime;
//    private String s_soonTime;
//    private String th_soonTime;
//
//    //температура в ближайшие часы
//    private String f_soonTemp;
//    private String s_soonTemp;
//    private String th_soonTemp;


    MyData myData;
    JSONArray jsonArray;
    JSONObject jsonObject;
    int dataNumber;
//    public WeatherParserServise(String name, JSONArray jsonArray) {
//        super(name);
//        this.jsonArray = jsonArray;
//        myData = MyData.getInstance();
//    }

    public WeatherParserServise(String name, JSONObject jsonObject, int dataNumber) {
        super(name);
        this.jsonObject = jsonObject;
        this.dataNumber = dataNumber;
        myData = MyData.getInstance();
        takenWeatherData = myData.getAllWeatherDataHashMap();
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //Получим данные объекта Json и спарсим их
            String myJsonString = jsonObject.toString();
            Gson gson = new Gson();
            final WeatherRequest weatherRequest = gson.fromJson(myJsonString, WeatherRequest.class);
            gotTime = weatherRequest.getDt_txt().substring(10);
            gotTemp = ((Integer)weatherRequest.getMain().getTemp()).toString();
            gotPressure = ((Integer)weatherRequest.getMain().getPressure()).toString();
            gotWind = ((Float)weatherRequest.getWind().getSpeed()).toString();
            //Теперь положим все данные в массив
            String [] weatherDataArr = {gotTime, gotTemp, gotPressure, gotWind};
            //сохраним массив в myData;
            takenWeatherData.put(dataNumber, weatherDataArr);
    }
}
