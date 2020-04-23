package com.geekbrains.anasdroweather2.rest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherRepo {
    private static OpenWeatherRepo instanse = null;
    private IOpenWeather API;

    private OpenWeatherRepo() {
        API = createAdapter();
    }

    public static OpenWeatherRepo getInstance()  {
        if (instanse == null) {
            instanse = new OpenWeatherRepo();
        }
        return instanse;
    }

    public IOpenWeather getAPI(){
          return API;
    }

    private IOpenWeather createAdapter(){
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return adapter.create(IOpenWeather.class);
    }
}
