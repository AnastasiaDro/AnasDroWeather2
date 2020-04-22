package com.geekbrains.anasdroweather2.weatherData;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.geekbrains.anasdroweather2.model.MyData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class WeatherParserService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private String gotTime;
    private String gotTemp;
    private String gotPressure;
    private String gotWind;
    HashMap<Integer, String[]> takenWeatherData;

    MyData myData;
    JSONArray jsonArray;
    JSONObject jsonObject;

    public WeatherParserService(String name, JSONArray jsonArray) {
        super(name);
        this.jsonArray = jsonArray;
        myData = MyData.getInstance();
        takenWeatherData = myData.getAllWeatherDataHashMap();
    }


    @Override
    public void onHandleIntent(@Nullable Intent intent) {
        //Получим данные объекта Json и спарсим их
        for (int i = 0; i < 4; i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                String myJsonString = jsonObject.toString();
                Gson gson = new Gson();
                final WeatherRequest weatherRequest = gson.fromJson(myJsonString, WeatherRequest.class);
                gotTime = weatherRequest.getDt_txt().substring(10);
                gotTemp = ((Integer) weatherRequest.getMain().getTemp()).toString();
                gotPressure = ((Integer) weatherRequest.getMain().getPressure()).toString();
                gotWind = ((Float) weatherRequest.getWind().getSpeed()).toString();
                //Теперь положим все данные в массив
                String[] weatherDataArr = {gotTime, gotTemp, gotPressure, gotWind};
                //сохраним массив в myData;
                takenWeatherData.put(i, weatherDataArr);
                //Остановим сервис
                stopSelf();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                //без этой штуки почему-то вылетает
            }
        }
    }
}
