package com.rasco.dev.smarttimer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rasco.dev.smarttimer.base.Program;

import java.util.ArrayList;

public class ProgramDB extends DBHandler {

    public static final String TABLE_PROGRAM = "PROGRAM";
    public static final String COLUMN_PROGRAM_ID = "program_id";
    public static final String COLUMN_PROGRAM_NAME = "program_name";

    final static String CREATE_TABLE_PROGRAM = "CREATE TABLE " + TABLE_PROGRAM + "(" +
            COLUMN_PROGRAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PROGRAM_NAME + " TEXT " +
            ");";

    public ProgramDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Program getProgram(int programId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROGRAM + " WHERE " +
                COLUMN_PROGRAM_ID + " = " + programId;

        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();
        Program program = new Program(c.getInt(c.getColumnIndex(COLUMN_PROGRAM_ID)),
                c.getString(c.getColumnIndex(COLUMN_PROGRAM_NAME)));

        return program;
    }

    //Add a new row to the database
    public void addProgram(Program program){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROGRAM_NAME, program.getName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PROGRAM, null, values);
        db.close();
    }

    public boolean updateProgram(Program program) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PROGRAM_NAME, program.getName());

        db.update(TABLE_PROGRAM, contentValues, COLUMN_PROGRAM_ID + " = ? ",
                new String[]{Integer.toString(program.get_id())});
        return true;
    }

    public void deleteProgram(Program program) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PROGRAM + " WHERE " +
                COLUMN_PROGRAM_ID + " = ?" ;

        db.execSQL(query, new Integer[] {program.get_id()});
        db.close();
    }

    public ArrayList<Program> getAllPrograms(){
        ArrayList<Program> programs = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROGRAM + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_PROGRAM_NAME)) != null) {
                programs.add(new Program(c.getInt(c.getColumnIndex(COLUMN_PROGRAM_ID)), c.getString(c.getColumnIndex(COLUMN_PROGRAM_NAME))));
            }
            c.moveToNext();
        }
        db.close();

        return programs;
    }
}