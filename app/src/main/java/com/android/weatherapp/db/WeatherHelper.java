package com.android.weatherapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherHelper extends SQLiteOpenHelper {
	private Context mContext;
 
	public WeatherHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
		super(context, name, cursorFactory, version);
		mContext = context;
	}
	public void onCreate(SQLiteDatabase db) {
		String sql="create table if not exists weather(" +
				"_id integer primary key autoincrement," +
				"cityName varchar,"+
				"date varchar,"+
				"dayWeahter varchar,"+
				"dayTemp integer ,"+
				"nightTemp integer ,"+
				"nightWeatehr varchar,"+
				"wind varchar,"+
				"windLevel varchar)";
		db.execSQL(sql);
	}
 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
 
} 
