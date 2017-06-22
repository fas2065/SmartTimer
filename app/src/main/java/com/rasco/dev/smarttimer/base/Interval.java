package com.rasco.dev.smarttimer.base;

public class Interval {

    public int id;
    public int program_id;
    public String name;

    public Interval(int programId, String name) {
        this.program_id = programId;
        this.name = name;
    }

    public Interval(int id, int programId, String name) {
        this.id = id;
        this.program_id = programId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getProgram_id() {return program_id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}
}
