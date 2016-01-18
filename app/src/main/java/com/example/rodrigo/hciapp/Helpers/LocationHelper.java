package com.example.rodrigo.hciapp.Helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Stack;

/**
 * Created by rodrigo on 17/01/16.
 */
public class LocationHelper implements LocationListener {
    Stack<Location>recentsLocations = new Stack<>();
    private boolean isGPSEnabled=false;
    private boolean isNetworkEnabled=false;

    protected LocationManager locationManager;

    private Context ctx;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES=0;
    private static final long MIN_TIME_BW_UPDATES=1000;

    public LocationHelper(Context ctx){
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        init();
    }
    public void init(){
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        try{
            if(isGPSEnabled){
                //Register for location updates using the named provider
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            }
            if(isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public Location getCurrentPosition(){
        Location location = null;
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        try {
            if (isGPSEnabled) {
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//Returns a Location indicating the data from the last known location fix obtained from the given provider.
                    if (location != null) {
                       // pos = new LatLng(location.getLatitude(), location.getLongitude());
                        return location;
                    }
                }
            }
            if(isNetworkEnabled){
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//Returns a Location indicating the data from the last known location fix obtained from the given provider.
                    if (location != null) {
                        return location;
                    }
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return location;
    }

    public Location getRecentLocation(){
        if(!this.recentsLocations.empty()){
            return this.recentsLocations.peek();
        }
        return this.getCurrentPosition();
    }

    @Override
    public void onLocationChanged(Location location) {
        /*MainActivity parent = (MainActivity)this.ctx;
        LatLng currentPosition = new LatLng(location.getLatitude(),location.getLongitude());
        parent.map.addMarker(new MarkerOptions().position(currentPosition));
        parent.map.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));*/
        recentsLocations.push(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
