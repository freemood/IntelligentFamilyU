package com.Intelligent.FamilyU.model.home.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 天气
 */

public class WearherBean implements Serializable {

    public String date;
    public String error;
    public List<Results> results;
    public String status;

    public class Results {
        public String currentCity;
        public List<Index> index;
        public String pm25;
        public List<Weather_data> weather_data;

        public String getCurrentCity() {
            return currentCity;
        }

        public Results setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
            return this;
        }

        public List<Index> getIndex() {
            return index;
        }

        public Results setIndex(List<Index> index) {
            this.index = index;
            return this;
        }

        public String getPm25() {
            return pm25;
        }

        public Results setPm25(String pm25) {
            this.pm25 = pm25;
            return this;
        }

        public List<Weather_data> getWeather_data() {
            return weather_data;
        }

        public Results setWeather_data(List<Weather_data> weather_data) {
            this.weather_data = weather_data;
            return this;
        }
    }

    public class Index {
        public String des;
        public String tipt;
        public String title;
        public String zs;

        public String getDes() {
            return des;
        }

        public Index setDes(String des) {
            this.des = des;
            return this;
        }

        public String getTipt() {
            return tipt;
        }

        public Index setTipt(String tipt) {
            this.tipt = tipt;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public Index setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getZs() {
            return zs;
        }

        public Index setZs(String zs) {
            this.zs = zs;
            return this;
        }
    }

    public class Weather_data {
        public String date;
        public String dayPictureUrl;
        public String nightPictureUrl;
        public String temperature;
        public String weather;
        public String wind;

        public String getDate() {
            return date;
        }

        public Weather_data setDate(String date) {
            this.date = date;
            return this;
        }

        public String getDayPictureUrl() {
            return dayPictureUrl;
        }

        public Weather_data setDayPictureUrl(String dayPictureUrl) {
            this.dayPictureUrl = dayPictureUrl;
            return this;
        }

        public String getNightPictureUrl() {
            return nightPictureUrl;
        }

        public Weather_data setNightPictureUrl(String nightPictureUrl) {
            this.nightPictureUrl = nightPictureUrl;
            return this;
        }

        public String getTemperature() {
            return temperature;
        }

        public Weather_data setTemperature(String temperature) {
            this.temperature = temperature;
            return this;
        }

        public String getWeather() {
            return weather;
        }

        public Weather_data setWeather(String weather) {
            this.weather = weather;
            return this;
        }

        public String getWind() {
            return wind;
        }

        public Weather_data setWind(String wind) {
            this.wind = wind;
            return this;
        }
    }

    public String getDate() {
        return date;
    }

    public WearherBean setDate(String date) {
        this.date = date;
        return this;
    }

    public String getError() {
        return error;
    }

    public WearherBean setError(String error) {
        this.error = error;
        return this;
    }

    public List<Results> getResults() {
        return results;
    }

    public WearherBean setResults(List<Results> results) {
        this.results = results;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public WearherBean setStatus(String status) {
        this.status = status;
        return this;
    }
}
