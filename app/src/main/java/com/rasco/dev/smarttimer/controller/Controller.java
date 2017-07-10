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

    ProgramDB programDB;
    Context context;

    private static class Loader {
        static volatile Controller INSTANCE = new Controller();
    }

    public Controller() {
        programDB = new ProgramDB(context, null, null, 1);
    }

    public Controller(Context context) {
        this.context = context;
        programDB = new ProgramDB(context, null, null, 1);
    }

    public static Controller getInstance(Context context) {
        Loader.INSTANCE.context = context;
        return Loader.INSTANCE;
    }

    public ArrayList<Program> getAllPrograms() {
        return programDB.getAllPrograms();
    }

    public Program getProgram(int id) {
        Program program = programDB.getProgram(id);

        IntervalDB intervalDB = new IntervalDB(context, null, null, 1);
        program.setIntervals(intervalDB.getIntervals(program));
        return program;
    }

    public void addProgram(Program program) {
        programDB.addProgram(program);

    }

    public void deleteProgram(Program program) {
        programDB.deleteProgram(program);
    }
}
