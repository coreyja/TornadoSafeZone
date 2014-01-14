package com.fll.teamstorm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.appspot.perfect_atrium_421.safezones.model.GeoPtMessage;
import com.appspot.perfect_atrium_421.safezones.model.Hours;
import com.appspot.perfect_atrium_421.safezones.model.SafeZone;

import java.util.ArrayList;

/**
 * Created by coreyja on 1/13/14.
 */
public class SafeZoneSQLHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "SafeZonesDB";

    public static final String TABLE_NAME = "safezones";

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_OCCUPANCY = "occupancy";
    public static final String KEY_MAX_OCCUPANCY = "max_occupancy";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LON = "lon";
    public static final String KEY_EXTRA = "extra";

    public static final String KEY_MON_OPEN = "mon_open";
    public static final String KEY_MON_CLOSE = "mon_close";
    public static final String KEY_TUE_OPEN = "tue_open";
    public static final String KEY_TUE_CLOSE = "tue_close";
    public static final String KEY_WED_OPEN = "wed_open";
    public static final String KEY_WED_CLOSE = "wed_close";
    public static final String KEY_THUR_OPEN = "thur_open";
    public static final String KEY_THUR_CLOSE = "thur_close";
    public static final String KEY_FRI_OPEN = "fri_open";
    public static final String KEY_FRI_CLOSE = "fri_close";
    public static final String KEY_SAT_OPEN = "sat_open";
    public static final String KEY_SAT_CLOSE = "sat_close";
    public static final String KEY_SUN_OPEN = "sun_open";
    public static final String KEY_SUN_CLOSE = "sun_close";

    private static String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static String CREATE_STATEMENT;
    static {
        StringBuilder s = new StringBuilder();
        s.append("CREATE TABLE ");
        s.append(TABLE_NAME);
        s.append(" (");
        s.append(KEY_ID);
        s.append(" INTEGER PRIMARY KEY, ");
        s.append(KEY_TITLE);
        s.append(" TEXT, ");
        s.append(KEY_PHONE);
        s.append(" TEXT, ");
        s.append(KEY_OCCUPANCY);
        s.append(" INTEGER, ");
        s.append(KEY_MAX_OCCUPANCY);
        s.append(" INTEGER, ");
        s.append(KEY_LAT);
        s.append(" DOUBLE, ");
        s.append(KEY_LON);
        s.append(" DOUBLE, ");
        s.append(KEY_EXTRA);
        s.append(" TEXT, ");

        s.append(KEY_MON_OPEN);
        s.append(" TEXT, ");
        s.append(KEY_MON_CLOSE);
        s.append(" TEXT, ");
        s.append(KEY_TUE_OPEN);
        s.append(" TEXT, ");
        s.append(KEY_TUE_CLOSE);
        s.append(" TEXT, ");
        s.append(KEY_WED_OPEN);
        s.append(" TEXT, ");
        s.append(KEY_WED_CLOSE);
        s.append(" TEXT, ");
        s.append(KEY_THUR_OPEN);
        s.append(" TEXT, ");
        s.append(KEY_THUR_CLOSE);
        s.append(" TEXT, ");
        s.append(KEY_FRI_OPEN);
        s.append(" TEXT, ");
        s.append(KEY_FRI_CLOSE);
        s.append(" TEXT, ");
        s.append(KEY_SAT_OPEN);
        s.append(" TEXT, ");
        s.append(KEY_SAT_CLOSE);
        s.append(" TEXT, ");
        s.append(KEY_SUN_OPEN);
        s.append(" TEXT, ");
        s.append(KEY_SUN_CLOSE);
        s.append(" TEXT ");

        s.append(")");
        CREATE_STATEMENT = s.toString();
    }

    private static final String[] PROJECTION = {KEY_ID, KEY_TITLE, KEY_PHONE, KEY_OCCUPANCY, KEY_MAX_OCCUPANCY,
        KEY_LAT, KEY_LON, KEY_EXTRA, KEY_MON_OPEN, KEY_MON_CLOSE, KEY_TUE_OPEN, KEY_TUE_CLOSE, KEY_WED_OPEN, KEY_WED_CLOSE,
        KEY_THUR_OPEN, KEY_THUR_CLOSE, KEY_FRI_OPEN, KEY_FRI_CLOSE, KEY_SAT_OPEN, KEY_SAT_CLOSE, KEY_SUN_OPEN, KEY_SUN_CLOSE};

    private SQLiteDatabase db;

    public SafeZoneSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(MapActivity.TAG, "Updating from version " + oldVersion + " to " +
                newVersion + ", which will destroy old table(s).");

        db.execSQL(DROP_STATEMENT);
        onCreate(db);
    }

    // Convert from SafeZone object to a table row
    private ContentValues getContentValuesFromScore(SafeZone sz) {
        ContentValues rowValues = new ContentValues();
        rowValues.put(KEY_TITLE, sz.getTitle());
        rowValues.put(KEY_PHONE, sz.getPhone());
        rowValues.put(KEY_OCCUPANCY, sz.getOccupancy());
        rowValues.put(KEY_MAX_OCCUPANCY, sz.getMaxOccupancy());
        rowValues.put(KEY_LAT, sz.getLocation().getLat());
        rowValues.put(KEY_LON, sz.getLocation().getLon());
        rowValues.put(KEY_EXTRA, sz.getExtraInfo());

        rowValues.put(KEY_MON_OPEN, sz.getHours().getMonOpen());
        rowValues.put(KEY_MON_CLOSE, sz.getHours().getMonClose());
        rowValues.put(KEY_TUE_OPEN, sz.getHours().getTueOpen());
        rowValues.put(KEY_TUE_CLOSE, sz.getHours().getTueClose());
        rowValues.put(KEY_WED_OPEN, sz.getHours().getWedOpen());
        rowValues.put(KEY_WED_CLOSE, sz.getHours().getWedClose());
        rowValues.put(KEY_THUR_OPEN, sz.getHours().getThursOpen());
        rowValues.put(KEY_THUR_CLOSE, sz.getHours().getThursClose());
        rowValues.put(KEY_FRI_OPEN, sz.getHours().getFriOpen());
        rowValues.put(KEY_FRI_CLOSE, sz.getHours().getFriClose());
        rowValues.put(KEY_SAT_OPEN, sz.getHours().getSatOpen());
        rowValues.put(KEY_SAT_CLOSE, sz.getHours().getSatClose());
        rowValues.put(KEY_SUN_OPEN, sz.getHours().getSunOpen());
        rowValues.put(KEY_SUN_CLOSE, sz.getHours().getSunClose());

        return rowValues;
    }

    //Convert from table row to SafeZone
    private SafeZone getSafeZoneFromCursor(Cursor c) {
        SafeZone sz = new SafeZone();

        sz.setTitle(c.getString(c.getColumnIndexOrThrow(KEY_TITLE)));
        sz.setPhone(c.getString(c.getColumnIndexOrThrow(KEY_PHONE)));
        sz.setOccupancy(c.getLong(c.getColumnIndexOrThrow(KEY_OCCUPANCY)));
        sz.setMaxOccupancy(c.getLong(c.getColumnIndexOrThrow(KEY_MAX_OCCUPANCY)));
        sz.setLocation(new GeoPtMessage().setLat(c.getDouble(c.getColumnIndexOrThrow(KEY_LAT))).setLon(c.getDouble(c.getColumnIndexOrThrow(KEY_LON))));
        sz.setExtraInfo(c.getString(c.getColumnIndexOrThrow(KEY_EXTRA)));

        Hours h = new Hours();
        h.setMonOpen(c.getString(c.getColumnIndexOrThrow(KEY_MON_OPEN)));
        h.setMonClose(c.getString(c.getColumnIndexOrThrow(KEY_MON_CLOSE)));
        h.setTueOpen(c.getString(c.getColumnIndexOrThrow(KEY_TUE_OPEN)));
        h.setTueClose(c.getString(c.getColumnIndexOrThrow(KEY_TUE_CLOSE)));
        h.setWedOpen(c.getString(c.getColumnIndexOrThrow(KEY_WED_OPEN)));
        h.setWedClose(c.getString(c.getColumnIndexOrThrow(KEY_WED_CLOSE)));
        h.setThursOpen(c.getString(c.getColumnIndexOrThrow(KEY_THUR_OPEN)));
        h.setThursClose(c.getString(c.getColumnIndexOrThrow(KEY_THUR_CLOSE)));
        h.setFriOpen(c.getString(c.getColumnIndexOrThrow(KEY_FRI_OPEN)));
        h.setFriClose(c.getString(c.getColumnIndexOrThrow(KEY_FRI_CLOSE)));
        h.setSatOpen(c.getString(c.getColumnIndexOrThrow(KEY_SAT_OPEN)));
        h.setSatClose(c.getString(c.getColumnIndexOrThrow(KEY_SAT_CLOSE)));
        h.setSunOpen(c.getString(c.getColumnIndexOrThrow(KEY_SUN_OPEN)));
        h.setSunClose(c.getString(c.getColumnIndexOrThrow(KEY_SUN_CLOSE)));


        sz.setHours(h);

        return sz;
    }

    public void open() {
        this.db = this.getWritableDatabase();
    }

    public void close() {
        this.db.close();
    }

    public long addSafeZone(SafeZone sz){
        ContentValues vals = getContentValuesFromScore(sz);
        return this.db.insert(TABLE_NAME, null, vals);
    }

    public SafeZone getSafeZone(long id){
        String selection = KEY_ID + "=" + id;

        Cursor c = db.query(true, TABLE_NAME, PROJECTION, selection, null,null,null,null,null);

        if(c != null && c.moveToFirst()) {
            return getSafeZoneFromCursor(c);
        }

        return null;
    }

    public ArrayList<SafeZone> getAllSafeZones() {
        ArrayList<SafeZone> list = new ArrayList<SafeZone>();

        Cursor c = db.query(true, TABLE_NAME, PROJECTION, null, null,null,null,null,null);

        if(c!=null && c.moveToFirst()){
           do {
               list.add(getSafeZoneFromCursor(c));
           } while (c.moveToNext());
        }

        return list;
    }

}
