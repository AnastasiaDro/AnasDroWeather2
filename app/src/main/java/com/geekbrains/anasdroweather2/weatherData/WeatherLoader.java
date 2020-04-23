package com.geekbrains.anasdroweather2.weatherData;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.widget.Toast;

import com.geekbrains.anasdroweather2.R;
import com.geekbrains.anasdroweather2.model.MyData;
import com.geekbrains.anasdroweather2.rest.OpenWeatherRepo;
import com.geekbrains.anasdroweather2.rest.entities.WeatherRequestRestModel;

import androidx.annotation.NonNull;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    //JSONArray jsonArray;
    Context context;
    Exception e;
    String result;

    private String gotTime;
    private String gotTemp;
    private String gotPressure;
    private String gotWind;


    //Конструктор
    public WeatherLoader(Context context) {
        this.myData = myData.getInstance();
        city = myData.getCurrentCity();
        takenWeatherData = myData.getAllWeatherDataHashMap();
        this.context = context;
        e = new Exception();
    }

    public void loadWeatherData() {
        OpenWeatherRepo.getInstance().getAPI().loadWeather(city + ",RU", "cf6eb93358473e7ee159a01606140722")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call, @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            renderWeather(response.body());
                        } else {
                            //если код не в диапазоне [200...300) то случилась ошибка
                            //здесь её обрабатываем
                            if (response.code() == 500) {
                                //Произошёл Internal Server Error, обраюотать
                            } else if (response.code() == 401) {
                                //не авторизованы, обработать
                                // и т.д.
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        Toast.makeText(context, context.getString(R.string.network_error), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void renderWeather(WeatherRequestRestModel model) {
        for (int i = 0; i < 4; i++) {
            //здесь нужно засунуть всё в HashMap в MyData
            gotTime = model.listResponce[i].textDt.substring(10);
            gotTemp = String.valueOf(model.listResponce[i].main.getTemp());
            gotPressure = String.valueOf(model.listResponce[i].main.getPressure());
            gotWind = String.valueOf(model.listResponce[i].wind.speed);
            String[] weatherDataArr = {gotTime, gotTemp, gotPressure, gotWind};
            takenWeatherData.put(i, weatherDataArr);
        }
    }
}
   //
//    //Сделать URL c учетом выбранного города
//    private String createURL(String city) {
//        String weather_url = url_maket.replaceAll("99999999999", city);
//        return weather_url;
//    }
//
//    //Основной метод выгрузки данных погоды с сервера. УПРОСТИТЬ
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void loadWeatherData() {
//        try {
//            final URL uri = new URL(createURL(city));
//            myThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        HttpsURLConnection urlConnection;
//                        urlConnection = (HttpsURLConnection) uri.openConnection();
//                        urlConnection.setRequestMethod("GET");
//                        urlConnection.setReadTimeout(10000);
//                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                        final String result = in.lines().collect(Collectors.joining("\n"));
//                        JSONObject jsonResponse = new JSONObject(result);
//                        jsonArray = jsonResponse.getJSONArray("list");
//                        //мои котовасии
//                    } catch (java.io.FileNotFoundException e) {
//                        e.printStackTrace();
//                        myData.setExceptionWhileLoading(e, R.string.cityError, R.string.adviceCityError);
//                        endTime = System.currentTimeMillis();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        myData.setExceptionWhileLoading(e, R.string.connectionError, R.string.adviceConnectonError);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//            myData.setWeatherLoaderThread(myThread);
//            myData.getWeatherLoaderThread().start();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public JSONArray getJsonArray() {
//        return jsonArray;
//    }
//
//    public void showExceptionAlert() {
//        final int exceptionStringId = myData.getExceptionNameId();
//        final int adviceStringId = myData.getExceptionAdviceId();
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(exceptionStringId);
//        builder.setMessage(adviceStringId);
//        builder.setCancelable(false);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
 //   }

