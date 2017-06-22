package com.rasco.dev.smarttimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rasco.dev.smarttimer.base.Program;
import com.rasco.dev.smarttimer.controller.Controller;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView programList;

    ProgramAdapter programAdapter;
    ArrayList<Program> programs;
    Controller controller = new Controller(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        programList = (ListView) findViewById(R.id.programList);
        programs = controller.getAllPrograms();
        programAdapter = new ProgramAdapter(this, programs);
        programList.setAdapter(programAdapter);

        programList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Program programClicked = programAdapter.getProgram(position);

                Intent intent = new Intent(getApplicationContext(),ProgramActivity.class);

                int programId = programClicked.get_id();
                intent.putExtra("id", programId);
                //based on item add info to intent
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Adding new Program", Snackbar.LENGTH_LONG)
                        .setAction("Add new", null).show();
                addNewProgram(view);
            }
        });
    }

    private void addNewProgram(View view) {
        Intent intent = new Intent(getApplicationContext(),ProgramActivity.class);
        startActivity(intent);
    }

}
