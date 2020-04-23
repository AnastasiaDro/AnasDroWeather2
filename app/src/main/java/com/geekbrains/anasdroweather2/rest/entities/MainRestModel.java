package com.geekbrains.anasdroweather2.rest.entities;

import com.google.gson.annotations.SerializedName;

public class MainRestModel {
    public int getTemp() {
        temp -= 273.15;
        int result = (int) Math.round(temp);
        return result;
    }

    @SerializedName("temp") public float temp;



    @SerializedName("feels_like") public float feelsLike;
    @SerializedName("pressure") public float pressure;

    public int getPressure() {
        //переведем в мм ртутного столба
        int result = (int) Math.round(pressure / 1.333224);
        return result;
    }

    @SerializedName("humidity") public float humidity;
    @SerializedName("temp_min") public float tempMin;
    @SerializedName("temp_max") public float tempMax;
}
