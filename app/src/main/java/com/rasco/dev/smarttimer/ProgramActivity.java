package com.rasco.dev.smarttimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rasco.dev.smarttimer.base.Program;
import com.rasco.dev.smarttimer.controller.Controller;


public class ProgramActivity extends AppCompatActivity {

    final static String TAG = "MY LOGS:   ";
    State programState;
    View recyclerView;
    Toolbar toolbar;

    private Program program;
    private boolean mTwoPane;

    SimpleItemRecyclerAdapter adapter;

    EditText programNameEdit;
    TextView programNameView;

    MenuItem editMenuItem;
    MenuItem saveMenuItem;
    MenuItem deleteMenuItem;
    MenuItem searchMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        programNameEdit = (EditText) findViewById(R.id.editTextProgName);
        programNameView = (TextView) findViewById(R.id.programNameView);
        editMenuItem = (MenuItem) findViewById(R.id.app_bar_edit);

        Intent intent = getIntent();
        if (null != intent.getStringExtra("id")){
            int id = Integer.valueOf(intent.getStringExtra("id"));
            program = new Controller(this).getProgram(id);
            programState = State.VIEW;
            uiViewProgram();
        } else {
            program = new Program();
            programState = State.NEW;
            uiNewProgram();
        }

        recycleView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adding new Interval", Snackbar.LENGTH_LONG)
                        .setAction("Add new", null).show();
                addNewInterval();
            }
        });
    }

    private void recycleView() {
        recyclerView = findViewById(R.id.interval_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        if (findViewById(R.id.interval_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new SimpleItemRecyclerAdapter(program.getIntervals(), mTwoPane, getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        switch(programState){
            case EDIT:
                menu.removeItem(R.id.app_bar_edit);
                menu.removeItem(R.id.app_bar_search);
                break;
            case NEW:
                menu.removeItem(R.id.app_bar_edit);
                menu.removeItem(R.id.app_bar_search);
                menu.removeItem(R.id.app_bar_delete);
                break;
            case VIEW:
                menu.removeItem(R.id.app_bar_save);
                menu.removeItem(R.id.app_bar_search);
                break;
            default:
                menu.removeItem(R.id.app_bar_save);
                menu.removeItem(R.id.app_bar_search);
                break;
        }
        inflater.inflate(R.menu.program_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.app_bar_save) {
            saveProgram();
        } else if (itemId == R.id.app_bar_edit) {
            uiEditProgram();
        } else if (itemId == R.id.app_bar_delete) {
            deleteProgram();
        }
//        else if (itemId == R.id.app_bar_search) {
//            uiEditProgram();
//        }

        return super.onOptionsItemSelected(item);
    }

    private void uiNewProgram() {
        programNameEdit.setVisibility(View.VISIBLE);
        programNameView.setVisibility(View.GONE);
    }

    private void uiEditProgram() {
        programNameEdit.setVisibility(View.GONE);
        programNameView.setVisibility(View.VISIBLE);
    }

    private void uiViewProgram() {
        programNameEdit.setVisibility(View.GONE);
        programNameView.setVisibility(View.VISIBLE);

    }

    private void deleteProgram() {

    }

    private void addNewInterval() {
        program = new Program("name");
        recycleView();
    }

    private void removeInterval(int id) {
//        Program.removeInterval(id);
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private boolean saveProgram() {
        return true;
    }

//    new AlertDialog.Builder(this)
//            .setTitle("Metaphorical Sandwich Dialog")
//    .setMessage("Metaphorical message to please use the spicy mustard.")
//    .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
//        @Override public void onClick(DialogInterface dialogInterface, int i) {
//            // "No thanks" button was clicked
//        }
//    })
//            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//        @Override public void onClick(DialogInterface dialogInterface, int i) {
//            // "OK" button was clicked
//        }
//    })
//            .show();


    public enum State {
        EDIT, VIEW, NEW
    }
}
