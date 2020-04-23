package com.geekbrains.anasdroweather2.rest.entities;

import com.google.gson.annotations.SerializedName;

public class ListResponce {
    @SerializedName("coord") public CoordRestModel coordinates;
    @SerializedName("weather") public WeatherRestModel[] weather;
    @SerializedName("main") public MainRestModel main;
    @SerializedName("visibility") public int visibility;
    @SerializedName("wind") public WindRestModel wind;
   // @SerializedName("pressure") public PressureRestModel pressure;
    @SerializedName("dt_txt") public String textDt;
    @SerializedName("name") public String name;
    @SerializedName("id") public long id;
//    @SerializedName("sys") public SysRestModel sys;
//    @SerializedName("rain") public RainRestModel rain;
}
