package com.jt28.a6735.weatherget.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jt28.a6735.weatherget.dao.bean.City;
import com.jt28.a6735.weatherget.dao.greendao.DBOpenHelper;


/**
 * Created by ghbha on 2016/4/27.
 */
public class CityDao {

    private Context context;
    private DBOpenHelper dbOpenHelper;

    public CityDao(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(context);
    }

    public City getCityByCityAndArea(String cityName, String areaName) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from citys where cityName = '" + cityName + "' and areaName = '" + areaName + "'", null);
        City city = new City();
        if (cursor.moveToNext()) {
            city.setAreaName(cursor.getString(cursor.getColumnIndex("cityName")));
            city.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
            city.setWeatherId(cursor.getString(cursor.getColumnIndex("weatherId")));
            city.setAreaId(cursor.getString(cursor.getColumnIndex("areaId")));
            cursor.close();
        } else {
            cursor.close();
            return null;
        }

        return city;
    }

    public City getCityByWeatherID(String weather_id) {
//        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from citys where weatherId ='" + weather_id + "'", null);
//        City city = new City();
//        if (cursor.moveToNext()) {
//            city.setAreaName(cursor.getString(cursor.getColumnIndex("areaName")));
//            city.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
//            city.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
//            city.setWeatherId(cursor.getString(cursor.getColumnIndex("weatherId")));
//            city.setAreaId(cursor.getString(cursor.getColumnIndex("areaId")));
//            cursor.close();
//        } else {
//            cursor.close();
//            return null;
//        }
//
//        return city;
        City city = new City();
        city.setAreaName("杭州");
        city.setProvinceName("杭州");
        city.setCityName("杭州");
        city.setWeatherId(weather_id);
        city.setAreaId("58457");
        return city;
    }

}
