package com.geekbrains.anasdroweather2.weatherData;


import android.os.Build;
import android.os.Handler;

import com.geekbrains.anasdroweather2.model.MyData;
import com.google.gson.Gson;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

//помогает получать данные с сервера
public class WeatherLoader {

    String url_maket = "https://api.openweathermap.org/data/2.5/forecast?q=99999999999,RU&appid=cf6eb93358473e7ee159a01606140722";
    String city;
    MyData myData;

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

//Конструктор
    public WeatherLoader(MyData myData){
        myData = MyData.getInstance();
        city = myData.getCurrentCity();
        currentTemp = null;
        currentPressure = null;
        currentWind = null;
    }


//Сделать URL c учетом выбранного города
    private String createURL(String city){
        String weather_url = url_maket.replaceAll( "99999999999", city);
        return weather_url;
    }


//Основной метод выгрузки данных погоды с сервера. УПРОСТИТЬ
    public void loadWeatherData() {
        try {
            final URL uri = new URL(createURL(city));
            //нужен хэндлер
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    try{
                        HttpsURLConnection urlConnection;
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        final String result = in.lines().collect(Collectors.joining("\n"));

                        //мои котовасии
                        JSONObject jsonResponse = new JSONObject(result);
                        JSONArray jsonArray = jsonResponse.getJSONArray("list");
                        //Текущая погода (первый элемент массива)
                        JSONObject currentWeatherJson = jsonArray.getJSONObject(0);
                        //погода в след 3 часа
                        JSONObject fst_soonWeatherJson = jsonArray.getJSONObject(1);
                        //погода через 6 часов
                        JSONObject scnd_soonWeatherJson = jsonArray.getJSONObject(2);
                        //погода через 9 часов
                        JSONObject thrd_soonWeatherJson = jsonArray.getJSONObject(3);


                        System.out.println("Размер массива: " + jsonArray.length());
                        //строка с данными текущей погоды
                        String curWeathString = currentWeatherJson.toString();
                        //строка с данными погоды через 3 часа
                        String fst_soonWeatherString = fst_soonWeatherJson.toString();
                        //Строка с данными погоды через 6 часов
                        String scnd_soonWeatherString = scnd_soonWeatherJson.toString();
                        //Строка с данными погоды через 9 часов
                        final String thrd_soonWeatherString = thrd_soonWeatherJson.toString();

                        Gson gson = new Gson();
                        final WeatherRequest currentWeatherRequest = gson.fromJson(curWeathString, WeatherRequest.class);
                        final WeatherRequest fst_soonWeatherRequest = gson.fromJson(fst_soonWeatherString, WeatherRequest.class);
                        final WeatherRequest scnd_soonWeatherRequest = gson.fromJson(scnd_soonWeatherString, WeatherRequest.class);
                        final WeatherRequest thrd_soonWeatherRequest = gson.fromJson(thrd_soonWeatherString, WeatherRequest.class);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
             //вытаскиваем данные
                        //текущие данные
                                currentTemp = ((Float)currentWeatherRequest.getMain().getTemp()).toString();
                                currentPressure = ((Integer)currentWeatherRequest.getMain().getPressure()).toString();
                                currentWind = ((Float)currentWeatherRequest.getWind().getSpeed()).toString();
             //ближайшие часы (время)
                                f_soonTime = fst_soonWeatherRequest.getDt_txt().getDt_txt();
                                s_soonTime = scnd_soonWeatherRequest.getDt_txt().getDt_txt();
                                th_soonTime = thrd_soonWeatherRequest.getDt_txt().getDt_txt();
              //температура в ближайшие часы
                                f_soonTemp = ((Float)fst_soonWeatherRequest.getMain().getTemp()).toString();
                                s_soonTemp = ((Float)scnd_soonWeatherRequest.getMain().getTemp()).toString();
                                th_soonTemp = ((Float)thrd_soonWeatherRequest.getMain().getTemp()).toString();
                            }
                        });
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sendWeatherDataToMyData();


                }
            }).start();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    //сохраним полученные данные в MyData
    public void sendWeatherDataToMyData() {
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