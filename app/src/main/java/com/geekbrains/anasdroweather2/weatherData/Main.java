package com.geekbrains.anasdroweather2.weatherData;

public class Main {
    private float temp;
    private int pressure;

    public int getTemp() {
        temp -= 273.15;
        int result = (int)Math.round(temp);
        return result;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPressure() {
        //переведем в мм ртутного столба
        int result = (int)Math.round(pressure/1.333224);
        return result;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    private int humidity;
}
