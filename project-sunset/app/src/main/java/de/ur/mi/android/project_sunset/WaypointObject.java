package de.ur.mi.android.project_sunset;

/**
 * Object to handel information from the waypoints-database
 */

public class WaypointObject {
    private int id;
    private String ident;
    private double latitude;
    private double longitude;
    private String type;

    /**
     * @param id incremental id of the waypoint
     * @param ident identifies a waypoint b 5 characters
     * @param latitude latitude of the waypoint
     * @param longitude longitude of the waypoint
     * @param type type of the waypoint (e.g. airport, custom)
     */

    public WaypointObject(int id, String ident, double latitude, double longitude, String type){
        this.id = id;
        this.ident = ident;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getId() {
        return id;
    }

    public String getIdent() {
        return ident;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "" + id + " " + ident + " " + latitude + " " + longitude + " " + type;
    }
}
