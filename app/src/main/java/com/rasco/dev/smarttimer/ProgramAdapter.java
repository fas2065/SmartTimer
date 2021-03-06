package com.rasco.dev.smarttimer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rasco.dev.smarttimer.base.Program;

import java.util.ArrayList;

public class ProgramAdapter extends ArrayAdapter<Program> {

    public ProgramAdapter(Context context, ArrayList<Program> programs) {
        super(context, 0, programs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Program program = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_program, parent, false);
        }
        // Lookup view for data population
        TextView programName = (TextView) convertView.findViewById(R.id.program_name);
        // Populate the data into the template view using the data object
        //programId.setText(program.get_id());
        programName.setText(program.getName());
        // Return the completed view to render on screen
        return convertView;
    }

    public Program getProgram(int position) {
        return getItem(position);
    }
}
