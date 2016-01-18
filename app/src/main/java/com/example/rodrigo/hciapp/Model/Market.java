package com.example.rodrigo.hciapp.Model;

import android.location.Location;
import android.os.Parcelable;
import android.os.Parcel;
/**
 * Created by rodrigo on 17/01/16.
 */
public class Market implements Parcelable{
    private int idMarket;
    private double latitude, longitude;
    private String name,address;

    public Market(double latitude, double longitude,String name,String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
    }

    public Location getLocation(){
        Location location = new Location("");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}