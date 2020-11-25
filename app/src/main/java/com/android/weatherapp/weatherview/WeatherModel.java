package com.android.weatherapp.weatherview;


public class WeatherModel {

    private int dayTemp; //temperature of day
    private int nightTemp; //temperature of night
    private String dayWeather; //weather of day
    private String nightWeather; // weather of night
    private String date; //date
    private String week; //week
    private int dayPic; //image res id of day
    private int nightPic; // image res id if night
    private String windOrientation; //orientation of wind
    private String windLevel; //level of wind
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(int dayTemp) {
        this.dayTemp = dayTemp;
    }

    public int getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(int nightTemp) {
        this.nightTemp = nightTemp;
    }

    public String getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }

    public String getNightWeather() {
        return nightWeather;
    }

    public void setNightWeather(String nightWeather) {
        this.nightWeather = nightWeather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }



    public int getDayPic() {
        return dayPic;
    }

    public void setDayPic(int dayPic) {
        this.dayPic = dayPic;
    }

    public int getNightPic() {
        return nightPic;
    }

    public void setNightPic(int nightPic) {
        this.nightPic = nightPic;
    }

    public String getWindOrientation() {
        return windOrientation;
    }

    public void setWindOrientation(String windOrientation) {
        this.windOrientation = windOrientation;
    }

    public String getWindLevel() {
        return windLevel;
    }

    public void setWindLevel(String windLevel) {
        this.windLevel = windLevel;
    }


}
