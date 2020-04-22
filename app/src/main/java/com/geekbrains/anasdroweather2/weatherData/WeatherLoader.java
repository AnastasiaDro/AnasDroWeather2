package com.geekbrains.anasdroweather2.weatherData;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.geekbrains.anasdroweather2.R;
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
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

//помогает получать данные с сервера
public class WeatherLoader {
    Thread myThread;
    //Засечем время выполнения
    long startTime;
    long endTime;
    private String url_maket = "https://api.openweathermap.org/data/2.5/forecast?q=99999999999,RU&appid=cf6eb93358473e7ee159a01606140722";
    private String city;
    private MyData myData;
    private HashMap<Integer, String[]> takenWeatherData;
    JSONArray jsonArray;
    Context context;
    Exception e;
    String result;

    //Конструктор
    public WeatherLoader(Context context) {
        this.myData = myData.getInstance();
        city = myData.getCurrentCity();
        takenWeatherData = myData.getAllWeatherDataHashMap();
        this.context = context;
        e = new Exception();
    }

    //Сделать URL c учетом выбранного города
    private String createURL(String city) {
        String weather_url = url_maket.replaceAll("99999999999", city);
        return weather_url;
    }

    //Основной метод выгрузки данных погоды с сервера. УПРОСТИТЬ
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadWeatherData() {
        try {
            final URL uri = new URL(createURL(city));
            myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpsURLConnection urlConnection;
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        final String result = in.lines().collect(Collectors.joining("\n"));
                        JSONObject jsonResponse = new JSONObject(result);
                        jsonArray = jsonResponse.getJSONArray("list");
                        //мои котовасии
                    } catch (java.io.FileNotFoundException e) {
                        e.printStackTrace();
                        myData.setExceptionWhileLoading(e, R.string.cityError, R.string.adviceCityError);
                        endTime = System.currentTimeMillis();
                    } catch (IOException e) {
                        e.printStackTrace();
                        myData.setExceptionWhileLoading(e, R.string.connectionError, R.string.adviceConnectonError);
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

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void showExceptionAlert() {
        final int exceptionStringId = myData.getExceptionNameId();
        final int adviceStringId = myData.getExceptionAdviceId();
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
