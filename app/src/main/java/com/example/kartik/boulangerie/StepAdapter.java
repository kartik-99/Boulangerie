package com.example.kartik.boulangerie;

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

    public StepAdapter(ArrayList<Step> steps) {
        this.steps = steps;
    }

    @Override
    public StepAdapter.StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new StepAdapter.StepHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepHolder holder, int position) {
        Step step = steps.get(position);
        holder.mIdView.setText(Integer.toString(step.getId()));
        holder.mContentView.setText(step.getShortDescription());
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
