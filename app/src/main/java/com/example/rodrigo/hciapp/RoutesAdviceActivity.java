package com.example.rodrigo.hciapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.rodrigo.hciapp.Helpers.LocationHelper;
import com.example.rodrigo.hciapp.Utils.NetworkUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class RoutesAdviceActivity extends AppCompatActivity implements RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks  {
    protected GoogleApiClient mGoogleApiClient;
    protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    private ArrayList<Polyline> polylines;
    private ProgressDialog progressDialog;
    private Location userLocation;
    private LocationHelper locationHelper;
    Bundle data;
    private int[] colors = new int[]{R.color.primary_dark_material_dark,R.color.primary_dark_material_light,R.color.primary_text_default_material_dark,R.color.accent_material_dark,R.color.primary_dark_material_light};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_advice);
        Intent intent = getIntent();
        data = intent.getExtras();
        setToolBar();
        polylines = new ArrayList<>();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        MapsInitializer.initialize(this);
        mGoogleApiClient.connect();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        map = mapFragment.getMap();
        locationHelper = new LocationHelper(this);
        setLocations();
        setViewMap();
        sendRequest();
    }

    public void setLocations(){
        userLocation = locationHelper.getRecentLocation();
        start = new LatLng(userLocation.getLatitude(),userLocation.getLongitude());
        if(data!=null) {
            Location market = data.getParcelable("Market Location");
            end = new LatLng(market.getLatitude(), market.getLongitude());
        }
    }

    public void setViewMap(){
        LatLng currentPosition = new LatLng(userLocation.getLatitude(),userLocation.getLongitude());
        CameraUpdate center = CameraUpdateFactory.newLatLng(currentPosition);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo((float) 12.5);
        map.moveCamera(center);
        map.animateCamera(zoom);
    }

    public void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRoutes);
        if(data!=null) {
            toolbar.setTitle("Ruta a " +data.get("Name Market"));
        }else{
            toolbar.setTitle("Ruta al supermercado");
        }
        toolbar.setTitleTextColor(rgb(255, 255, 255));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        this.setSupportActionBar(toolbar);
    }


    public void route() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Fetching route information.", true);
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(start, end)
                .build();
        routing.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_routes_advice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendRequest()
    {
        if(NetworkUtil.Operations.isOnline(this)) {
            route();
        }else{
            Toast.makeText(this,"No internet connectivity",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onRoutingFailure(RouteException e) {
        progressDialog.dismiss();
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route,int shortestRouteIndex) {
        progressDialog.dismiss();
        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);

        map.moveCamera(center);

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }
        polylines = new ArrayList<>();
        //add route(s) to the map.
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(rgb(33, 150, 243));
        polyOptions.width(10);
        polyOptions.addAll(route.get(0).getPoints());
        Polyline polyline = map.addPolyline(polyOptions);
        polylines.add(polyline);
        //Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();
        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(start);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_circle));
        map.addMarker(options);
        // End marker
        options = new MarkerOptions();
        options.position(end);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local_grocery_store));
        map.addMarker(options);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(start);
        builder.include(end);
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
    }

    @Override
    public void onRoutingCancelled() {

    }

    public void onMapReady(GoogleMap googleMap){
        map = googleMap;
        userLocation = locationHelper.getRecentLocation();
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(userLocation.getLatitude(),userLocation.getLongitude()));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }

}
