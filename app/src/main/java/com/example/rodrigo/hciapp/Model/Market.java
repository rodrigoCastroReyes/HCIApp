package com.example.rodrigo.hciapp.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rodrigo on 17/01/16.
 */
public class Market implements Parcelable {
    private int idMarket;
    private double latitude, longitude;
    private String name,address;

    public Market(double latitude, double longitude,String name,String address){
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
    }


    protected Market(Parcel in) {
        idMarket = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
        address = in.readString();
    }

    public static final Creator<Market> CREATOR = new Creator<Market>() {
        @Override
        public Market createFromParcel(Parcel in) {
            return new Market(in);
        }

        @Override
        public Market[] newArray(int size) {
            return new Market[size];
        }
    };

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