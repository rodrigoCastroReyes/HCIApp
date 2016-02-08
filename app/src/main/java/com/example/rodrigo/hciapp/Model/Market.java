package com.example.rodrigo.hciapp.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by rodrigo on 17/01/16.
 */
public class Market implements Serializable{
    private int idMarket;
    private double latitude, longitude;
    private String name,address;

    public Market(){

    }

    public Market(String name,String latitude, String longitude,String address){
        this.latitude = Double.valueOf(latitude);
        this.longitude = Double.valueOf(longitude);
        this.name = name;
        this.address = address;
    }

    public Location getLocation(){
        Location location = new Location("");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
    }

    public int getIdMarket() {
        return idMarket;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Market setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Market setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Market setName(String name) {
        this.name = name;
        return this;
    }

    public Market setAddress(String address) {
        this.address = address;
        return this;
    }

    public static abstract class MarketEntry implements BaseColumns {
        public static final String TABLE_NAME = "Market";
        public static final String COLUMN_ENTRY_ID = "marketid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ADDRESS = "address";

        public static final int INDEX_ENTRY_ID = 0;
        public static final int INDEX_NAME = 1;
        public static final int INDEX_LATITUDE = 2;
        public static final int INDEX_LONGITUDE = 3;
        public static final int INDEX_ADDRESS = 4;

        public static final String DATABASE_CREATE = "create table if not exists "
                + TABLE_NAME + " (" + COLUMN_ENTRY_ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " text, "
                + COLUMN_LATITUDE + " text, " + COLUMN_LONGITUDE + " text, "
                + COLUMN_ADDRESS  +" text)";
        public static final String DATABASE_DELETE = "drop table if exists " + TABLE_NAME;
    }


}