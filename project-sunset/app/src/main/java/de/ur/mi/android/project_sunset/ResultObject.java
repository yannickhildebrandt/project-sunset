package de.ur.mi.android.project_sunset;

/**
 * Created by Nils on 25.08.2017.
 */

public class ResultObject {

    private int timeNoClouds;
    private int timeMediumClouds;
    private int timeManyClouds;
    private long posLongitude;
    private long posLatitude;

    public ResultObject(int timeNoClouds, int timeMediumClouds, int timeManyClouds, long posLongitude, long posLatitude){
        this.timeNoClouds = timeNoClouds;
        this.timeMediumClouds = timeMediumClouds;
        this.timeManyClouds = timeManyClouds;
        this.posLongitude = posLongitude;
        this.posLatitude = posLatitude;
    }
}
