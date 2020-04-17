package com.geekbrains.anasdroweather2.weatherData;

    public class WeatherRequest {
        private Coord coord;
        private Weather [] weather;
        private Main main;
        private Wind wind;
        private Clouds clouds;
        private String name;

        public Dt_txt getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(Dt_txt dt_txt) {
            this.dt_txt = dt_txt;
        }

        private Dt_txt dt_txt;

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public Weather[] getWeather() {
            return weather;
        }

        public void setWeather(Weather[] weather) {
            this.weather = weather;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
