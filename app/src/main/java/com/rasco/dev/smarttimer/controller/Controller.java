package com.rasco.dev.smarttimer.controller;

import android.content.Context;

import com.rasco.dev.smarttimer.base.Program;
import com.rasco.dev.smarttimer.db.IntervalDB;
import com.rasco.dev.smarttimer.db.ProgramDB;

import java.util.ArrayList;

/**
 * Created by user on 2017-05-31.
 */

public class Controller {

    private static Controller instance = null;


    Context context;
    ArrayList<Program> programs;

    public Controller(Context context) {
        this.context = context;
    }

    public static Controller getInstance(Context context) {
        if (instance == null) {
            instance = new Controller(context);
        }
        return instance;
    }

    public ArrayList<Program> getAllPrograms() {
        ProgramDB programDB = new ProgramDB(context, null, null, 1);
        programs = programDB.getAllPrograms();
        return programs;
    }

    public Program getProgram(int id) {
        ProgramDB programDB = new ProgramDB(context, null, null, 1);
        Program program = programDB.getProgram(id);

        IntervalDB intervalDB = new IntervalDB(context, null, null, 1);
        program.setIntervals(intervalDB.getIntervals(program));
        return program;
    }

    public void addProgram(Program program) {

    }
}
