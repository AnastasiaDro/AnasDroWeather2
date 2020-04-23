package com.geekbrains.anasdroweather2.rest.entities;

import com.google.gson.annotations.SerializedName;

public class WeatherRequestRestModel {
    @SerializedName("coord") public CoordRestModel coordinates;
    @SerializedName("weather") public WeatherRestModel[] weather;
    @SerializedName("main") public MainRestModel main;
    @SerializedName("visibility") public int visibility;
    @SerializedName("wind") public windRestModel wind;
    @SerializedName("pressure") public PressureRestModel pressure;
    @SerializedName("dt_txt") public String textDt;
    @SerializedName("clouds") public CloudsRestModel clouds;
    @SerializedName("name") public String name;
    @SerializedName("id") public long id;
    @SerializedName("rain") public RainRestModel rain;
}
