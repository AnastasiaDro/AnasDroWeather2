package com.geekbrains.anasdroweather2.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRequestRestModel {
    @SerializedName("list") public ListResponce[] listResponce;
}
