package com.android.weatherapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.weatherapp.weatherview.WeatherModel;

import java.util.ArrayList;
import java.util.List;


public class WeatherDao {
    private WeatherHelper dbHelper;
    private SQLiteDatabase db;

    public WeatherDao(Context context) {
        dbHelper = new WeatherHelper(context, "data.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    // 添加
    public void add(WeatherModel weatherModel) {
        if (!Dataexist(weatherModel.getCityName(), weatherModel.getDate())) {
            try {

               /* db.execSQL("insert into weather(cityName,date,dayTemp,dayWeahter,windLevel,wind,nightTemp,nightWeatehr) " +
                                "values("+weatherModel.getCityName()+","+weatherModel.getDate()+","+weatherModel.getDayTemp()+"" +
                                ","+weatherModel.getDayWeather()+","+weatherModel.getWindLevel()+
                        ","+ weatherModel.getWindOrientation()+","+ weatherModel.getNightTemp()+","+ weatherModel.getNightWeather()+")");*/
                ContentValues values=new ContentValues();
                values.put("cityName",weatherModel.getCityName());
                values.put("date",weatherModel.getDate());
                values.put("dayTemp",weatherModel.getDayTemp());
                values.put("dayWeahter",weatherModel.getDayWeather());
                values.put("wind",weatherModel.getWindOrientation());
                values.put("windLevel",weatherModel.getWindLevel());
                values.put("nightTemp",weatherModel.getNightTemp());
                values.put("nightWeatehr",weatherModel.getNightWeather());

                db.insert("weather", null, values);

            }catch (Exception e){
                Log.e("sqlexception",e.getMessage());
            }

        }
    }


    public List<WeatherModel> queryAlllxr(String cityName) {
        List<WeatherModel> weatherModels = new ArrayList<WeatherModel>();
        Cursor c = db.rawQuery("select * from weather where cityName= ?", new String[]{cityName});
        while (c.moveToNext()) {
            WeatherModel lxr = new WeatherModel();
            lxr.setCityName(c.getString(1));
            lxr.setDate(c.getString(2));
            lxr.setDayWeather(c.getString(3));
            lxr.setDayTemp(c.getInt(4));
            lxr.setNightTemp(c.getInt(5));
            lxr.setNightWeather(c.getString(6));
            lxr.setWindOrientation(c.getString(7));
            lxr.setWindLevel(c.getString(8));

            weatherModels.add(lxr);
        }
        c.close();
        return weatherModels;

    }
    public List<WeatherModel> queryAlllxr() {
        List<WeatherModel> weatherModels = new ArrayList<WeatherModel>();
        Cursor c = db.rawQuery("select * from weather", null);
        while (c.moveToNext()) {
            WeatherModel lxr = new WeatherModel();
            lxr.setCityName(c.getString(1));
            lxr.setDayWeather(c.getString(2));
            lxr.setDayTemp(c.getInt(3));
            lxr.setNightTemp(c.getInt(4));
            lxr.setNightWeather(c.getString(5));
            lxr.setWindOrientation(c.getString(6));
            lxr.setWindLevel(c.getString(6));

            weatherModels.add(lxr);
        }
        c.close();
        return weatherModels;

    }

    // 检验用户名是否已存在
    public boolean CheckIsDataAlreadyInDBorNot(String value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select * from lxrData where name =?";
        Cursor cursor = db.rawQuery(Query, new String[]{value});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // 判断信息是否已经存在
    public boolean Dataexist(String cityName, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select * from weather where cityName =? and date=?";
        Cursor cursor = db.rawQuery(Query, new String[]{cityName, date});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

}
