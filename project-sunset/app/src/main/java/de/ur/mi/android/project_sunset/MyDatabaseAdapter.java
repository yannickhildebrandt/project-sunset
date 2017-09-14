package de.ur.mi.android.project_sunset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseAdapter {

    private MyDatabaseHelper helper;
    private SQLiteDatabase db;

    // Datenbanksetup
    public static final String DB_NAME = "wayPointsDB";
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
        helper = new MyDatabaseHelper(context, DB_NAME, null, DB_VERSION);
    }

    // ÷ffnen der Datenbankverbindung
    public void open() {
        db = helper.getWritableDatabase();
    }

    // Schlieﬂen der Datenbankverbindung
    public void close() {
        db.close();
        helper.close();
    }

    // Datenmanipulation: Methoden

    // Beispielmethode: Objekt in my-example-table einf¸gen
    public long insertMyObject(WaypointObject waypointObject) {
        // Datensammlung f¸r den einzuf¸genden Datensatz erstellen (ContentValues)
        // nutzt Schl¸ssel-Wert-Mechanismus
        // es werden die Konstanten v. o. genutzt, um Fehler zu vermeiden
        ContentValues v = new ContentValues();
        v.put(KEY_ID,waypointObject.getId());
        v.put(KEY_IDENT, waypointObject.getIdent()); // exemparisch einfach toString()
        v.put(KEY_LATITUDE,waypointObject.getLatitude());
        v.put(KEY_LONGITUDE,waypointObject.getLongitude());
        v.put(KEY_TYPE,waypointObject.getType());
        long newInsertId = db.insert(TABLE_EXAMPLE, null, v);
        return newInsertId;
    }

    // Beispielmethode: alle Eintr‰ge aus my-example-table holen
    public Cursor getAllMyObjects() {
        String[] allColumns = new String[] { KEY_ID, KEY_IDENT, KEY_LATITUDE,KEY_LONGITUDE,KEY_TYPE};
        Cursor results = db.query(TABLE_EXAMPLE, allColumns, null, null, null, null, null);
        return results;
    }


    // Beispielmethode: Ein myObject-Tupel lˆschen
    public void removeMyObject(long id) {
        String toDelete = KEY_ID + "=?";
        String[] deleteArgs = new String[] { String.valueOf(id) };
        db.delete(TABLE_EXAMPLE, toDelete, deleteArgs);
    }




    // Interne Ableitung der Hilfsklasse SQLiteOpenHelper zur Erstellung der Tabellen
    private class MyDatabaseHelper extends SQLiteOpenHelper {

        // Hier wird ¸ber das SQL Statement das Datenmodell festgelegt
        private static final String CREATE_DB = "create table " + TABLE_EXAMPLE + " (" + KEY_ID + " text not null, " + KEY_IDENT + " text not null, " + KEY_LATITUDE + " text not null" + KEY_LONGITUDE+" text not null" + KEY_TYPE +" text not null"+");";
        public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Upgrage bei Versions‰nderung: Wie hat sich das Datenmodell ver‰ndert? Immer individuell je nach Datenbankversion!
        }
    }
}


