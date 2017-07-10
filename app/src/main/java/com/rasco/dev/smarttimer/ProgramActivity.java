package com.rasco.dev.smarttimer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

    final static String TAG = "MY LOGS: ";
    State programState;
    View recyclerView;
    SimpleItemRecyclerAdapter adapter;
    EditText programNameEdit;
    TextView programNameView;
    FloatingActionButton fab;
    MenuItem editMenuItem;
    MenuItem saveMenuItem;
    MenuItem deleteMenuItem;
    private Program program;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        programNameEdit = (EditText) findViewById(R.id.editTextProgName);
        programNameView = (TextView) findViewById(R.id.programNameView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        Intent intent = getIntent();
        intent.getStringExtra("id");
        if (null != intent.getStringExtra("id")){
            int id = Integer.valueOf(intent.getStringExtra("id"));
            program = new Controller(this).getProgram(id);
            programState = State.VIEW;
            uiViewProgram(program);
        } else {
            program = new Program();
            programState = State.NEW;
            uiNewProgram(program);
        }
        recycleView();
    }

    private void recycleView() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adding new Interval", Snackbar.LENGTH_LONG)
                        .setAction("Add new", null).show();
                addNewInterval();
            }
        });
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
        this.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.program_menu, menu);
        editMenuItem = menu.findItem(R.id.app_bar_edit);
        saveMenuItem = menu.findItem(R.id.app_bar_save);
        deleteMenuItem = menu.findItem(R.id.app_bar_delete);

        switch(programState){
            case EDIT:
                editMenuItem.setVisible(false);
                deleteMenuItem.setVisible(true);
                break;
            case NEW:
                editMenuItem.setVisible(false);
                deleteMenuItem.setVisible(false);
                break;
            case VIEW:
                saveMenuItem.setVisible(false);
                deleteMenuItem.setVisible(true);
                break;
            default:
                saveMenuItem.setVisible(false);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.app_bar_save) {
            saveProgram();
        } else if (itemId == R.id.app_bar_edit) {
            uiEditProgram();
        } else if (itemId == R.id.app_bar_delete) {
            alertDelete();
        }

        return super.onOptionsItemSelected(item);
    }

    private void uiNewProgram(Program program) {
        programNameEdit.setVisibility(View.VISIBLE);
        programNameView.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);

    }

    private void uiEditProgram() {
        programNameEdit.setVisibility(View.GONE);
        programNameView.setVisibility(View.VISIBLE);
        programNameView.setText(program.getName());
        fab.setVisibility(View.VISIBLE);
        programState = State.EDIT;
        recycleView();
    }

    private void uiViewProgram(Program program) {
        programNameEdit.setVisibility(View.GONE);
        programNameView.setVisibility(View.VISIBLE);
        programNameView.setText(program.getName());
        fab.setVisibility(View.GONE);
    }

    private void deleteProgram() {
        Controller.getInstance(this).deleteProgram(program);
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
    }

    private void saveProgram() {
        program = new Program(programNameEdit.getText().toString().trim());
        Controller.getInstance(this).addProgram(program);
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
    }

    private void alertDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Deleting Program")
                .setMessage("Do you want to delete the " + program.getName() + " program?")
                .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteProgram();
            }
        }).show();
    }

    private void addNewInterval() {
        program = new Program("name");
        recycleView();
    }

    private void removeInterval(int id) {
//        Program.removeInterval(id);
        setupRecyclerView((RecyclerView) recyclerView);
    }

    public enum State {
        EDIT, VIEW, NEW
    }
}
