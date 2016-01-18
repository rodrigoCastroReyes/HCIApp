package com.example.rodrigo.hciapp.Helpers;

import android.location.Location;

import com.example.rodrigo.hciapp.Model.Market;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 17/01/16.
 */
public class MarketsHelper {

    public ArrayList<Market> getMarkets(){
        //do query database
        ArrayList<Market> markets = new ArrayList<Market>();
        markets.add(new Market(-2.142107,-79.897664,"Mi Comisariato","Ing R Baquerizo Nazur, Guayaquil, Ecuador"));
        markets.add(new Market(-2.139820, -79.898667,"Tia","Rodolfo Baquerizo Nazur y José María Egas"));
        return markets;
    }
    public List findMarketsCloser(Location userLocation,float ratio){
        ArrayList<Market> marketsCloser = new ArrayList<>();
        ArrayList<Market> markets = this.getMarkets();
        for(Market market : markets){
            if(market.getLocation().distanceTo(userLocation)<=ratio){
                marketsCloser.add(market);
            }
        }
        return  marketsCloser;
    }
}
