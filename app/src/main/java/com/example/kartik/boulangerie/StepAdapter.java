package com.example.kartik.boulangerie;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartik.boulangerie.Objects.Step;

import java.util.ArrayList;

/**
 * Created by Kartik on 08-10-2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    ArrayList<Step> steps;
    boolean twoPane;
    Context c;

    public StepAdapter(ArrayList<Step> steps, boolean twoPane, Context c) {
        this.steps = steps;
        this.twoPane = twoPane;
        this.c = c;
    }

    @Override
    public StepAdapter.StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new StepAdapter.StepHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepHolder holder, final int position) {
        final Step step = steps.get(position);
        holder.mIdView.setText(Integer.toString(step.getId()));
        holder.mContentView.setText(step.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(twoPane){
                }else{
                    Intent intent = new Intent(c, DetailActivity.class);
                    intent.putExtra("step", step);
                    intent.putExtra("index", position);
                    c.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepHolder extends RecyclerView.ViewHolder {
        public TextView mIdView;
        public TextView mContentView;

        public StepHolder(View view) {
            super(view);
            mIdView = (TextView)view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
