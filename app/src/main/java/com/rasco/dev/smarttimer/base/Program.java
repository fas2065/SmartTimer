package com.rasco.dev.smarttimer.base;

import java.util.ArrayList;

public class Program {

    private int _id;
    private String name;
    private ArrayList<Interval> intervals = new ArrayList<Interval>();

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public void setIntervals(ArrayList<Interval> intervals) {
        this.intervals = intervals;
    }

    public Program() {
    }

    public Program(int id, String name) {
        this._id = id;
        this.name = name;
    }

    public Program(String name) {

        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Program getProgram(int id) {

        return null;

    }
}
