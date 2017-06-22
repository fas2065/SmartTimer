package com.rasco.dev.smarttimer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public static final String DATABASE_NAME = "intervallerDB.db";




    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProgramDB.CREATE_TABLE_PROGRAM);
        db.execSQL(IntervalDB.CREATE_TABLE_INTERVAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProgramDB.TABLE_PROGRAM);
        db.execSQL("DROP TABLE IF EXISTS " + IntervalDB.TABLE_INTERVAL);
        onCreate(db);
    }

}
