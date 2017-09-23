package de.ur.mi.android.project_sunset;

public class ResultObject {

    private int timeNoClouds;
    private int timeMediumClouds;
    private int timeManyClouds;
    private double posLatitude;
    private double posLongitude;

    public ResultObject(int timeNoClouds, int timeMediumClouds, int timeManyClouds, double posLatitude, double posLongitude){
        this.timeNoClouds = timeNoClouds;
        this.timeMediumClouds = timeMediumClouds;
        this.timeManyClouds = timeManyClouds;
        this.posLongitude = posLongitude;
        this.posLatitude = posLatitude;
    }

    public int getTimeNoClouds() {return timeNoClouds;}

    public int getTimeMediumClouds() {return timeMediumClouds;}

    public int getTimeManyClouds() {return timeManyClouds;}

    public double getPosLatitude() {return posLatitude;}

    public double getPosLongitude() {return posLongitude;}

    @Override
    public String toString(){return "" + timeNoClouds + " " + timeMediumClouds + " " + timeManyClouds + " " + posLatitude + " " + posLongitude;}
}
