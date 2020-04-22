package com.geekbrains.anasdroweather2.weatherData;

public class Weather {
    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    private String main;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
}
