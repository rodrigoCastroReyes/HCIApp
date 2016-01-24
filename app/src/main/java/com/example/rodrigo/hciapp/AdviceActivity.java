package com.example.rodrigo.hciapp;

import android.content.Intent;
import android.location.Location;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rodrigo.hciapp.Model.Market;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AdviceActivity extends FragmentActivity implements OnMapReadyCallback {
    public GoogleMap map;
    Intent intent;
    private Location location;
    private ArrayList<Market> marketsCloser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        intent = this.getIntent();
        Bundle data = intent.getExtras();
        location = (Location)data.getParcelable("UserLocation");
        marketsCloser = data.getParcelableArrayList("MarketCloser");
    }

    public void addLocationMarkets(GoogleMap map){
        Location location;
        MarkerOptions options;
        for(Market market : marketsCloser) {
            location = market.getLocation();
            options = new MarkerOptions();
            options.position(new LatLng(location.getLatitude(), location.getLongitude()));
            map.addMarker(options);
        }
    }

    public void onMapReady(GoogleMap map) {
        LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions options = new MarkerOptions();
        options.position(currentPosition);
        map.addMarker(options);
        addLocationMarkets(map);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 18.0f));
        //map.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
    }


}
