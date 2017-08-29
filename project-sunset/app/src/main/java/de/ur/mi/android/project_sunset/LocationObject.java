package de.ur.mi.android.project_sunset;

/**
 * Object which contains all necessary information for a position
 */

public class LocationObject {

    //Time is given in seconds
    private String name;
    private long longitude;
    private long latitude;
    private int sunriseTime;
    private int sunsetTime;
    private int arrivalTime;
    private int modifierValue;

    /**
     * @param name The name of the waypoint/fix
     * @param longitude The longitude of the waypoint
     * @param latitude The latitude of the waypoint
     * @param sunriseTime The time the sun rises at this certain location in seconds after midnight. Has to be converted to UTC!
     * @param sunsetTime The time the sun sets at this certain location in seconds after midnight. Has to be converted to UTC!
     * @param arrivalTime The time the user arrives at this certain location in seconds after midnight. Has to be converted to UTC!
     * @param modifierValue A modifier depending on the current weather situation
     */

    public LocationObject(String name, long longitude, long latitude, int sunriseTime, int sunsetTime, int arrivalTime, int modifierValue){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.arrivalTime = arrivalTime;
        this.modifierValue = modifierValue;
    }

    public String getName() {return name;}

    public long getLongitude() {return longitude;}

    public long getLatitude() {return latitude;}

    public int getSunriseTime() {return sunriseTime;}

    public int getSunsetTime() {return sunsetTime;}

    public int getArrivalTime() {return arrivalTime;}

    public int getModifierValue() {return modifierValue;}
}
