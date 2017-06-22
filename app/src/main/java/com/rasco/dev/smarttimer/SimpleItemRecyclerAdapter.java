package com.rasco.dev.smarttimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rasco.dev.smarttimer.base.Interval;

import java.util.ArrayList;

/**
 * Created by user on 2017-05-29.
 */

public class SimpleItemRecyclerAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerAdapter.ViewHolder> {

    private final ArrayList<Interval> intervals;
    private boolean mTwoPane;
    FragmentManager fragmentManager;

    public SimpleItemRecyclerAdapter(ArrayList<Interval> items, boolean mTwoPane, FragmentManager fm) {
        intervals = items;
        this.mTwoPane = mTwoPane;
        this.fragmentManager = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.interval_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = intervals.get(position);
        holder.mIdView.setText(intervals.get(position).id);
        holder.mContentView.setText(intervals.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(IntervalDetailFragment.ARG_ITEM_ID, Integer.toString(holder.mItem.getId()));
                    IntervalDetailFragment fragment = new IntervalDetailFragment();
                    fragment.setArguments(arguments);
                    fragmentManager.beginTransaction()
                            .replace(R.id.interval_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, IntervalDetailActivity.class);
                    intent.putExtra(IntervalDetailFragment.ARG_ITEM_ID, Integer.toString(holder.mItem.getId()));

                    context.startActivity(intent);
                }
            }
        });
    }

    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    @Override
    public int getItemCount() {
        return intervals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Interval mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
