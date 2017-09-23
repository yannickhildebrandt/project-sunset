package de.ur.mi.android.project_sunset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabaseAdapter extends SQLiteAssetHelper {

    //private MyDatabaseHelper helper;
    private SQLiteDatabase db;

    // database setup
    private static final String DB_NAME = "waypoints.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_WAYPOINTS = "waypoints";
    private static final String TABLE_SUNTIME = "suntime";

    public MyDatabaseAdapter(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //opens a database connection
    public void open() {
        db = this.getWritableDatabase();
    }

    //closes a database connection
    public void close() {
        db.close();
    }

    /**
     * Creates a Waypoint object from the waypoint.db
     * @param name selects which waypoint is choosen
     * @return a WaypointObject with all information for the selected waypoint
     */
    public WaypointObject getWaypointObjectByName(String name) {
        String[] tableColumns = new String[] {"ID" , "IDENT", "LATITUDE", "LONGITUDE", "TYPE"};
        String whereClause = "IDENT = ?";
        String[] whereArgs = new String[] {name};
        String oderBy = "ID";

        try {
            Cursor c = db.query(TABLE_WAYPOINTS, tableColumns, whereClause, whereArgs, null, null, oderBy);
            if (c != null) {c.moveToFirst();}
            WaypointObject result = new WaypointObject(Integer.parseInt(c.getString(0)), c.getString(1), Double.parseDouble(c.getString(2)), Double.parseDouble(c.getString(3)), c.getString(4));
            return result;
        }
        catch (Exception e) {
            Log.d("ZZZ",e.toString());
        }
        return null;
    }

    /**
     * adds a custom waypoint to the waypoints.db
     * @param name used for identification, has to be 5 characters
     * @param longitude longitude of the custom waypoint
     * @param latitude latitude of the custom waypoint
     * @return returns true if a new waypoint was added successfully
     */
    public boolean addWaypoint (String name, String longitude, String latitude) {
        ContentValues content = new ContentValues();
        content.put("IDENT", name);
        content.put("LATITUDE", latitude);
        content.put("LONGITUDE", longitude);
        content.put("TYPE", "custom");
        content.put("ID", "999999");

        try {
            db.insert(TABLE_WAYPOINTS, null, content);
            return true;
        }
        catch (Exception e) {Log.e("ZZZ", e.toString());}
        return false;
    }

    /**
     * gets the sunrise time of the suntime.db
     * @param lat latitude of the position
     * @param day current day
     * @return the time of sunset in seconds
     */
    public int getSunriseTime (double lat, int day) {
        String[] tableColumns = new String[] {"Sonnenaufgang"};
        String whereClause = "Breitengrad = ? AND Tag = ?";
        String[] whereArgs = new String[] {Integer.toString((int) lat), Integer.toString(day)};
        String oderBy = "Tag";
        try {
            Cursor c = db.query(TABLE_SUNTIME, tableColumns, whereClause, whereArgs, null, null, oderBy);
            if (c != null) {
                c.moveToFirst();
                return Integer.parseInt(c.getString(0));
            }
        }
        catch (Exception e) {
            Log.d("ZZZ",e.toString());
        }
        return -1;}

    /**
     * gets the sunrise time of a waypoint of the suntime.db
     * @param lat latitude of the position
     * @param day current day
     * @return time of sunrise in seconds
     */
    public int getSunsetTime (double lat, int day) {
        String[] tableColumns = new String[] {"Sonnenuntergang"};
        String whereClause = "Breitengrad = ? AND Tag = ?";
        String[] whereArgs = new String[] {Double.toString(lat), Integer.toString(day)};
        String oderBy = "Sonnenuntergang";
        try {
            Cursor c = db.query(TABLE_SUNTIME, tableColumns, whereClause, whereArgs, null, null, oderBy);
            if (c != null) {
                c.moveToFirst();
                return Integer.parseInt(c.getString(0));
            }
        }
        catch (Exception e) {
            Log.d("ZZZ",e.toString());
        }
        return -1;}

    /**
     * gets the modifier for a position out of the suntime.db
     * @param lat latitude of the waypoint
     * @param day current day
     * @return time to add to suntime in seconds
     */
    public int getModifier (double lat, int day) {
        String[] tableColumns = new String[] {"Modifikator"};
        String whereClause = "Breitengrad = ? AND Tag = ?";
        String[] whereArgs = new String[] {Double.toString(lat), Integer.toString(day)};
        String oderBy = "Modifikator";
        try {
            Cursor c = db.query(TABLE_SUNTIME, tableColumns, whereClause, whereArgs, null, null, oderBy);
            if (c != null) {
                c.moveToFirst();
                return Integer.parseInt(c.getString(0));
            }
        }
        catch (Exception e) {
            Log.d("ZZZ",e.toString());
        }
        return -1;}

}