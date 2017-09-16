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
    public static final int DB_VERSION = 1;

    // Relationenmodell
    // 3 Spalten in der Tabelle my-example-table
    // _id, first-example, second-example
    public static final String TABLE_EXAMPLE = "waypointTable";
    public static final String KEY_ID = "_id";
    public static final String KEY_IDENT = "ident";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_TYPE = "type";

    public MyDatabaseAdapter(Context context) {
        //helper = new MyDatabaseHelper(context, DB_NAME, null, DB_VERSION);
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
        String tableName = "waypoints";
        String[] tableColumns = new String[] {"ID" , "IDENT", "LATITUDE", "LONGITUDE", "TYPE"};
        String whereClause = "IDENT = ?";
        String[] whereArgs = new String[] {name};
        String oderBy = "ID";

        try {
            Cursor c = db.query(tableName, tableColumns, whereClause, whereArgs, null, null, oderBy);
            if (c != null) {c.moveToFirst();}
            WaypointObject result = new WaypointObject(Integer.parseInt(c.getString(0)), c.getString(1), Float.parseFloat(c.getString(2)), Float.parseFloat(c.getString(3)), c.getString(4));
            return result;
        }
        catch (Exception e) {
            Log.d("CREATION",e.toString());
        }
        return null;
    }

}