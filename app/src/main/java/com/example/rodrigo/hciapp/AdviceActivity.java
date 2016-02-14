package com.example.rodrigo.hciapp;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigo.hciapp.Helpers.MarketsHelper;
import com.example.rodrigo.hciapp.Model.Market;
import com.example.rodrigo.hciapp.Services.AdviceService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class AdviceActivity extends AppCompatActivity implements OnMapReadyCallback {
    public GoogleMap map;
    Intent intent;
    Bundle data;
    private Location userLocation;
    private MarketsHelper marketsHelper;
    ArrayList<Market>marketsCloser;
    private TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        textView = (TextView) findViewById(R.id.textView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        intent = this.getIntent();
        data = intent.getExtras();
        userLocation = (Location)data.getParcelable("UserLocation");
        marketsHelper = new MarketsHelper(this);
        this.setToolBar();
    }

    public void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.adviceReminderToolBar);
        toolbar.setTitle("Supermercados cercanos");
        toolbar.setTitleTextColor(rgb(255, 255, 255));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        this.setSupportActionBar(toolbar);
    }

    public void addLocationMarkets(ArrayList<Market>mCloser,GoogleMap map){
        marketsCloser = mCloser;
        Location location;
        MarkerOptions options;
        for(Market market : marketsCloser) {
            location = market.getLocation();
            options = new MarkerOptions();
            options.title(market.getName());
            options.snippet(""+market.getIdMarket());
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local_grocery_store));
            options.position(new LatLng(location.getLatitude(), location.getLongitude()));
            map.addMarker(options);
        }
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                int id = Integer.parseInt(marker.getSnippet());
                for(Market market : marketsCloser) {
                   if(market.getIdMarket() == id){
                       Intent intent = new Intent(getBaseContext(),RoutesAdviceActivity.class);
                       Bundle data = new Bundle();
                       Location location = new Location("");
                       location.setLatitude(market.getLatitude());
                       location.setLongitude(market.getLongitude());
                       data.putParcelable("Market Location",location);
                       data.putString("Name Market",market.getName());
                       intent.putExtras(data);
                       startActivity(intent);
                   }
                }
                return false;
            }
        });
    }

    public void onMapReady(GoogleMap map) {
        LatLng currentPosition = new LatLng(userLocation.getLatitude(),userLocation.getLongitude());
        MarkerOptions options = new MarkerOptions();
        options.position(currentPosition);
        options.title("Usted se encuentra aqui");
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle));
        map.addMarker(options);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 13.0f));
        this.map = map;
        ArrayList<Market>marketsCloser = (ArrayList<Market>) data.getSerializable("MarketsCloser");
        if(marketsCloser==null){
            new LoaderMarkets().execute();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class LoaderMarkets extends AsyncTask<Void, Void,ArrayList<Market>> {

        @Override
        protected ArrayList<Market> doInBackground(Void... params) {
            marketsHelper.getMarkets();
            return (ArrayList<Market>)marketsHelper.findMarketsCloser(userLocation,5000);
        }

        protected void onPostExecute(ArrayList<Market>marketsCloser){
            marketsCloser = marketsCloser;
            if(!marketsCloser.isEmpty()) {
                addLocationMarkets(marketsCloser,map);
            }else{
                textView.setText("No hay supermercados cercanos");
            }
        }
    }




}
