package com.rasco.dev.smarttimer;

import android.support.test.runner.AndroidJUnit4;

import com.rasco.dev.smarttimer.base.Interval;
import com.rasco.dev.smarttimer.base.Program;
import com.rasco.dev.smarttimer.db.DBHandler;
import com.rasco.dev.smarttimer.db.IntervalDB;
import com.rasco.dev.smarttimer.db.ProgramDB;

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
public class DBIntervalTest {

    private IntervalDB intervalTable;
    private ProgramDB programTable;

    private Program prog;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(DBHandler.DATABASE_NAME);
        programTable = new ProgramDB(getTargetContext(), DBHandler.DATABASE_NAME, null, DBHandler.DB_VERSION);
        programTable.addProgram(new Program("Program"));
        prog = programTable.getAllPrograms().get(0);

        intervalTable = new IntervalDB(getTargetContext(), DBHandler.DATABASE_NAME, null, DBHandler.DB_VERSION);
    }

    @After
    public void tearDown() throws Exception {
        intervalTable.close();
    }

    @Test
    public void shouldAddUpdateDeleteInterval() throws Exception {
        intervalTable.addInterval(createInterval());

        List<Interval> intervals = intervalTable.getIntervals(prog);
        assertThat(intervals.size(), is(1));
        Interval interval = intervals.get(0);
        assertTrue(interval.getName().equals("Interval1"));

        interval.setName("Interval2");
        intervalTable.updateInterval(interval);
        intervals = intervalTable.getIntervals(prog);
        assertThat(intervals.size(), is(1));
        interval = intervals.get(0);
        assertTrue(interval.getName().equals("Interval2"));

        intervalTable.deleteInterval(interval);
        intervals = intervalTable.getIntervals(prog);
        assertThat(intervals.size(), is(0));


    }

    private Interval createInterval() {
        return new Interval(prog.get_id(), "Interval1");
    }


}
