package de.ur.mi.android.project_sunset;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeCalculator {

    private boolean sunrise = false;
    private boolean sunset = false;
    private int index = -1;

    TimeCalculator mCurrentInstance;
    Context context;
    Calendar cal;

    double newLatitude;
    double newLongitude;

    private static final double NO_CLOUDS = 0;
    private static final double MEDIUM_CLOUDS = 0.5;
    private static final double MANY_CLOUDS = 1;

    private static final int NOON_TIME = 43200;

    public TimeCalculator(Context context){
        this.context = context;
        cal = Calendar.getInstance(TimeZone.getDefault());
    }

    public ResultObject calculateResult(ArrayList<LocationObject> locationList){
        int timeNoClouds = calculateTime(locationList, NO_CLOUDS);
        int timeMediumClouds = calculateTime(locationList, MEDIUM_CLOUDS);
        int timeManyClouds = calculateTime(locationList, MANY_CLOUDS);
        return new ResultObject(timeNoClouds, timeMediumClouds, timeManyClouds, newLatitude, newLongitude);
    }

    private int calculateTime(ArrayList<LocationObject> locationList, double param){
        LocationObject initialObject = locationList.get(0);
        if (initialObject.getArrivalTime() <= (initialObject.getSunriseTime() + initialObject.getModifierValue() * param) && initialObject.getArrivalTime() <= NOON_TIME) {
            index = checkForSunrise(locationList, param);
            sunrise = true;
        }
        else {
                index = checkForSunset(locationList, param);
                sunset = true;
            }
        if (index == -1) {return -1;}

        LocationObject loc1 = locationList.get(index-1);
        LocationObject loc2 = locationList.get(index);
        int deltaLoc1 = loc1.getArrivalTime() - loc1.getSunriseTime() + (int) (loc1.getModifierValue() * param);
        int deltaLoc2 = loc2.getArrivalTime() - loc2.getSunriseTime() + (int) (loc2.getModifierValue() * param);
        int deltaSum = Math.abs(deltaLoc1) + Math.abs(deltaLoc2);
        double deltaLocationFraction = Math.abs(deltaSum / deltaLoc1);
        double deltaLatitude = loc1.getLatitude() - loc2.getLatitude();
        double deltaLongitude = loc1.getLongitude() - loc2.getLongitude();
        newLatitude = loc1.getLatitude() + (deltaLatitude * deltaLocationFraction);
        newLongitude = loc1.getLongitude() + (deltaLongitude * deltaLocationFraction);
        getTimeByWaypoint(newLatitude, newLongitude, param);
        return -1;
    }

    private void getTimeByWaypoint(double lat, double lng, double param) {
        Log.e("ZZZ", "Latitude: " + lat + ", Longitude: " + lng);
        try {
            MyDatabaseAdapter mda = new MyDatabaseAdapter(context);
            mda.open();
            Log.e("ZZZ", "Sunrise: " + mda.getSunriseTime(Math.round(lat), cal.get(Calendar.DAY_OF_MONTH)));
            mda.close();
        }
        catch (Exception e){Log.e("ZZZ", e.toString());}
        //Log.e("ZZZ", "Sunrise" + mda.getSunriseTime(Math.round(lat), cal.get(Calendar.DAY_OF_MONTH)));
    }

    private int checkForSunrise(ArrayList<LocationObject> locationList, double modifier) {
        for (int i = 1; i <= locationList.size(); i++) {
            if (locationList.get(i).getArrivalTime() >= (locationList.get(i).getSunriseTime() + (locationList.get(i).getModifierValue() * modifier))) {return i;}
        }
        return -1;
    }

    private int checkForSunset(ArrayList<LocationObject> locationList, double modifier) {
        for (int i = 1; i <= locationList.size(); i++) {
            if (locationList.get(i).getArrivalTime() >= (locationList.get(i).getSunsetTime() + (locationList.get(i).getModifierValue() * modifier))) {return i;}
        }
        return -1;
    }

}
