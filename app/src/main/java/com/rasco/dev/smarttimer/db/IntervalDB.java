package com.rasco.dev.smarttimer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rasco.dev.smarttimer.base.Interval;
import com.rasco.dev.smarttimer.base.Program;

import java.util.ArrayList;

import static com.rasco.dev.smarttimer.db.ProgramDB.COLUMN_PROGRAM_ID;
import static com.rasco.dev.smarttimer.db.ProgramDB.TABLE_PROGRAM;

public class IntervalDB extends DBHandler {

    public static final String TABLE_INTERVAL = "INTERVAL";
    public static final String COLUMN_INTERVAL_ID = "interval_id";
    public static final String COLUMN_INTERVAL_NAME = "interval_name";

    final static String CREATE_TABLE_INTERVAL = "CREATE TABLE " + TABLE_INTERVAL + "(" +
            COLUMN_INTERVAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ProgramDB.COLUMN_PROGRAM_ID + " INTEGER, " +
            COLUMN_INTERVAL_NAME + " TEXT, " +
            "FOREIGN KEY(" + ProgramDB.COLUMN_PROGRAM_ID + ") REFERENCES " +
            TABLE_PROGRAM + "(" + ProgramDB.COLUMN_PROGRAM_ID + "));";

    public IntervalDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Add a new row to the database
    public void addInterval(Interval interval){
        ContentValues values = new ContentValues();
        values.put(COLUMN_INTERVAL_NAME, interval.getName());
        values.put(ProgramDB.COLUMN_PROGRAM_ID, interval.getProgram_id());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_INTERVAL, null, values);
        db.close();
    }

    public void updateInterval(Interval interval) {
        String updateClause = "interval_id=" + interval.getId();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INTERVAL_NAME, interval.getName());
        values.put(ProgramDB.COLUMN_PROGRAM_ID, interval.getProgram_id());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_INTERVAL, values, updateClause, null);
        db.close();
    }

    public void deleteInterval(Interval interval) {
        SQLiteDatabase db = getWritableDatabase();
        String query = COLUMN_PROGRAM_ID + " = ? AND " +
                COLUMN_INTERVAL_ID + " = ?";

        db.delete(TABLE_INTERVAL, query, new String[] {
                new Integer(interval.getProgram_id()).toString(),
                new Integer(interval.getId()).toString()});
        db.close();
    }

    public ArrayList<Interval> getIntervals(Program program) {
        ArrayList<Interval> intervals = new ArrayList<Interval>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INTERVAL + " WHERE "
                + COLUMN_PROGRAM_ID + " = " + program.get_id();

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_INTERVAL_NAME)) != null) {
                intervals.add(new Interval(c.getInt(c.getColumnIndex(COLUMN_INTERVAL_ID)),
                        program.get_id(),
                        c.getString(c.getColumnIndex(COLUMN_INTERVAL_NAME))));
            }
            c.moveToNext();
        }
        db.close();

        return intervals;
    }
}
