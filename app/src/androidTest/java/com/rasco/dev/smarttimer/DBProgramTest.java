package com.rasco.dev.smarttimer;

import android.support.test.runner.AndroidJUnit4;

import com.rasco.dev.smarttimer.db.DBHandler;
import com.rasco.dev.smarttimer.db.ProgramDB;
import com.rasco.dev.smarttimer.base.Program;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DBProgramTest {

    private ProgramDB database;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(DBHandler.DATABASE_NAME);
        database = new ProgramDB(getTargetContext(), DBHandler.DATABASE_NAME, null, DBHandler.DB_VERSION);
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void shouldAddUpdateDeleteProgram() throws Exception {
        database.addProgram(createProgram());

        List<Program> programs = database.getAllPrograms();
        assertThat(programs.size(), is(1));
        Program program = programs.get(0);
        assertTrue(program.getName().equals("Program1"));

        program.setName("Program2");
        database.updateProgram(program);
        programs = database.getAllPrograms();
        assertThat(programs.size(), is(1));
        program = programs.get(0);
        assertTrue(program.getName().equals("Program2"));

        database.deleteProgram(program);
        programs = database.getAllPrograms();
        assertThat(programs.size(), is(0));


    }

    private Program createProgram() {
        return new Program("Program1");
    }
}
