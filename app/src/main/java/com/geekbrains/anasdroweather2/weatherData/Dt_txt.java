package com.geekbrains.anasdroweather2.weatherData;


// время и дата прогноза
public class Dt_txt {
    public String getDt_txt() {
        //вырежем часы
        return dt_txt.substring(11, 15);
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    private String dt_txt;

}
