package de.ur.mi.android.project_sunset;

import java.util.ArrayList;

public class TimeCalculator {

    private boolean sunset = false;
    private boolean sunrise = false;
    private int index = -1;

    private static final double NO_CLOUDS = 0;
    private static final double MEDIUM_CLOUDS = 0.5;
    private static final double MANY_CLOUDS = 1;

    private static final int NOON_TIME = 43200;

    public ResultObject calculateResult(ArrayList<LocationObject> locationList){
        //calculateTime();
        return null;
    }

    private int calculateTime(ArrayList<LocationObject> locationList, double param){
        LocationObject initialObject = locationList.get(0);
        if (initialObject.getArrivalTime() <= (initialObject.getSunriseTime() + initialObject.getModifierValue() * param) && initialObject.getArrivalTime() <= NOON_TIME) {index = checkForSunrise(locationList, param);}
        else {index = checkForSunset(locationList, param);}
        if (index == -1) {
            System.out.println("Es findet kein Sonnenaufgang bzw. Sonnenuntergang statt");
            return -1;
        }
        LocationObject loc1 = locationList.get(index-1);
        LocationObject loc2 = locationList.get(index);
        int deltaLoc1 ;
        int deltaLoc2;
        return -1;
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
