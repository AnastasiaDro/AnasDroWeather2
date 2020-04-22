package com.geekbrains.anasdroweather2.weatherData;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.geekbrains.anasdroweather2.model.MyData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherParser extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private String currentTemp;
    private String currentPressure;
    private String currentWind;

    //ближайшие часы (время)
    private String f_soonTime;
    private String s_soonTime;
    private String th_soonTime;

    //температура в ближайшие часы
    private String f_soonTemp;
    private String s_soonTemp;
    private String th_soonTemp;


    MyData myData;
    JSONArray jsonArray;

    public WeatherParser(String name, JSONArray jsonArray) {
        super(name);
        this.jsonArray = jsonArray;
        myData = MyData.getInstance();
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            //текущая погода
            JSONObject currentWeatherJson = jsonArray.getJSONObject(0);
            //погода в след 3 часа
            JSONObject fst_soonWeatherJson = jsonArray.getJSONObject(1);
            //погода через 6 часов
            JSONObject scnd_soonWeatherJson = jsonArray.getJSONObject(2);
            //погода через 9 часов
            JSONObject thrd_soonWeatherJson = jsonArray.getJSONObject(3);

            //строка с данными текущей погоды
            String curWeathString = currentWeatherJson.toString();
            //строка с данными погоды через 3 часа
            String fst_soonWeatherString = fst_soonWeatherJson.toString();
            //Строка с данными погоды через 6 часов
            String scnd_soonWeatherString = scnd_soonWeatherJson.toString();
            //Строка с данными погоды через 9 часов
            String thrd_soonWeatherString = thrd_soonWeatherJson.toString();

            Gson gson = new Gson();

            //Получаем данные ближайших дней... ух!
            final WeatherRequest currentWeatherRequest = gson.fromJson(curWeathString, WeatherRequest.class);
            final WeatherRequest fst_soonWeatherRequest = gson.fromJson(fst_soonWeatherString, WeatherRequest.class);
            final WeatherRequest scnd_soonWeatherRequest = gson.fromJson(scnd_soonWeatherString, WeatherRequest.class);
            final WeatherRequest thrd_soonWeatherRequest = gson.fromJson(thrd_soonWeatherString, WeatherRequest.class);

            currentTemp = ((Integer)currentWeatherRequest.getMain().getTemp()).toString();
            currentPressure = ((Integer)currentWeatherRequest.getMain().getPressure()).toString();
            currentWind = ((Float)currentWeatherRequest.getWind().getSpeed()).toString();
            //ближайшие часы (время)
            f_soonTime = fst_soonWeatherRequest.getDt_txt().substring(10);
            s_soonTime = scnd_soonWeatherRequest.getDt_txt().substring(10);
            th_soonTime = thrd_soonWeatherRequest.getDt_txt().substring(10);
            //температура в ближайшие часы
            f_soonTemp = ((Integer)fst_soonWeatherRequest.getMain().getTemp()).toString();
            s_soonTemp = ((Integer)scnd_soonWeatherRequest.getMain().getTemp()).toString();
            th_soonTemp = ((Integer)thrd_soonWeatherRequest.getMain().getTemp()).toString();

            //Температура в ближайшщие дни
            sendWeatherDataToMyData();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Самый последний погода
        System.out.println("Размер массива: " + jsonArray.length());
    }

    private void sendWeatherDataToMyData() {
        myData.setCurrentTemp(currentTemp);
        myData.setCurrentPressure(currentPressure);
        myData.setCurrentWind(currentWind);

        myData.setF_soonTime(f_soonTime);
        myData.setS_soonTime(s_soonTime);
        myData.setTh_soonTime(th_soonTime);

        myData.setF_soonTemp(f_soonTemp);
        myData.setS_soonTemp(s_soonTemp);
        myData.setTh_soonTemp(th_soonTemp);
    }
}
