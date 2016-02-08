package com.example.rodrigo.hciapp.Helpers;

import android.content.Context;
import android.location.Location;

import com.example.rodrigo.hciapp.Model.DBOperations;
import com.example.rodrigo.hciapp.Model.Market;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 17/01/16.
 */
public class MarketsHelper {
    DBOperations dbOperations;
    private ArrayList<Market>markets;

    public MarketsHelper(Context context){
       dbOperations= new DBOperations(context);
    }

    public ArrayList<Market> getMarkets(){
        markets = dbOperations.getMarkets();
        return markets;
    }

    public List findMarketsCloser(Location userLocation,float ratio){
        ArrayList<Market> marketsCloser = new ArrayList<>();
        for(Market market : markets){
            double distance = market.getLocation().distanceTo(userLocation);
            if(distance <= ratio){
                marketsCloser.add(market);
            }
        }
        return  marketsCloser;
    }
}
