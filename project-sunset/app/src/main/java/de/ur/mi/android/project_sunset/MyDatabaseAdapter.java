package de.ur.mi.android.project_sunset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabaseAdapter extends SQLiteAssetHelper {

    //private MyDatabaseHelper helper;
    private SQLiteDatabase db;

    // Datenbanksetup
    public static final String DB_NAME = "waypoints.db";
    public static final int DB_VERSION = 2;
    String tableNamewaypoints = "waypoints";
    String tableSuntime = "suntime";

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
     * Creates a Waypoint object from the waypoint database
     * @param name selects which waypoint is choosen
     * @return a WaypointObject with all information for the selected waypoint
     */
    public WaypointObject getWaypointObjectByName(String name) {
        String[] tableColumns = new String[] {"ID" , "IDENT", "LATITUDE", "LONGITUDE", "TYPE"};
        String whereClause = "IDENT = ?";
        String[] whereArgs = new String[] {name};
        String oderBy = "ID";

        try {
            Cursor c = db.query(tableNamewaypoints, tableColumns, whereClause, whereArgs, null, null, oderBy);
            if (c != null) {c.moveToFirst();}
            WaypointObject result = new WaypointObject(Integer.parseInt(c.getString(0)), c.getString(1), Float.parseFloat(c.getString(2)), Float.parseFloat(c.getString(3)), c.getString(4));
            return result;
        }
        catch (Exception e) {
            Log.d("ZZZ",e.toString());
        }
        return null;
    }

    public boolean addWaypoint (String name, String longitude, String latitude) {
        ContentValues content = new ContentValues();
        content.put("IDENT", name);
        content.put("LATITUDE", latitude);
        content.put("LONGITUDE", longitude);
        content.put("TYPE", "custom");
        content.put("ID", "999999");

        try {
            db.insert(tableNamewaypoints, null, content);
            return true;
        }
        catch (Exception e) {Log.e("ZZZ", e.toString());}
        return false;
    }

    public int getSunriseTime (double lat, int day) {
        String[] tableColumns = new String[] {"Sonnenaufgang"};
        String whereClause = "Breitengrad = ? AND Tag = ?";
        String[] whereArgs = new String[] {Double.toString(lat), Integer.toString(day)};
        String oderBy = "Tag";
        try {
            Cursor c = db.query(tableSuntime, tableColumns, whereClause, whereArgs, null, null, oderBy);
            if (c != null) {c.moveToFirst();}
            Log.e("ZZZ", "QueryResult: " + c.getString(0));
            return Integer.parseInt(c.getString(0));
        }
        catch (Exception e) {
            Log.d("ZZZ",e.toString());
        }
        return -1;}

}