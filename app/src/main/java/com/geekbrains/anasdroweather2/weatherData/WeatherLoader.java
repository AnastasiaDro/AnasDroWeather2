package com.geekbrains.anasdroweather2.weatherData;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;

import com.geekbrains.anasdroweather2.model.MyData;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

//помогает получать данные с сервера
public class WeatherLoader {
   private Thread myThread;

   private String url_maket = "https://api.openweathermap.org/data/2.5/forecast?q=99999999999,RU&appid=cf6eb93358473e7ee159a01606140722";
   private String city;
   private MyData myData;
   private Context context;
   private Exception e;

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    private JSONObject jsonResponse;

//Конструктор
    public WeatherLoader(Context context){
        this.myData = myData.getInstance();
        city = myData.getCurrentCity();
        this.context = context;
        e = new Exception();

    }


//Сделать URL c учетом выбранного города
    private String createURL(String city){
        return url_maket.replaceAll( "99999999999", city);
    }


//Основной метод выгрузки данных погоды с сервера. УПРОСТИТЬ
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadWeatherData() {
        try {
            final URL uri = new URL(createURL(city));

            myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        HttpsURLConnection urlConnection;
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        final String result = in.lines().collect(Collectors.joining("\n"));

                        //получим результат
                        jsonResponse = new JSONObject(result);
                        JSONArray jsonArray = getJsonResponse().getJSONArray("list");

                        //переберём полученый массив и запустим на каждую полученную строку свой сервис-поток
                        //Боюсь, это слишком долго работает
                        for (int i = 0; i < 4; i++) {
                            WeatherParserServise weatherParserServise = new WeatherParserServise("MyParser", jsonArray.getJSONObject(i), i);
                            Intent intent = new Intent(context, WeatherParserServise.class);
                            weatherParserServise.onHandleIntent(intent);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Looper.prepare();
                        myData.setExceptionWhileLoading(e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
                    myData.setWeatherLoaderThread(myThread);
                    myData.getWeatherLoaderThread().start();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    //показывает сообщение об ошибке сети, если она происходит
        public void showExceptionAlert(final int exceptionStringId, final int adviceStringId) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(exceptionStringId);
            builder.setMessage(adviceStringId);
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

}
