package com.example.kartik.boulangerie;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kartik.boulangerie.Objects.Recipe;
import com.example.kartik.boulangerie.Objects.Step;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    FragmentTransaction ft;
    Step step;
    ArrayList<Step> steps;
    int index, totalSteps;
    Recipe recipe;
    TextView previous, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");
        steps = recipe.getSteps();
        totalSteps = steps.size();
        index = intent.getIntExtra("index", 1);

        previous = (TextView) findViewById(R.id.prev_textview);
        next = (TextView) findViewById(R.id.next_textView);

        if(index == 0)
            previous.setVisibility(View.INVISIBLE);
        if(index == totalSteps-1)
            next.setVisibility(View.INVISIBLE);
        setFragment();
    }

    public void setFragment(){
        step = steps.get(index);
        ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment= StepDetailFragment.newInstance(step);
        ft.replace(R.id.item_detail_container, fragment);
        ft.commit();
    }

    public void loadPreviousStep(View view) {
        if (index == 0)
            return;
        if(index == 1)
            previous.setVisibility(View.INVISIBLE);
        if (index == totalSteps-1)
            next.setVisibility(View.VISIBLE);
        index--;
        setFragment();
    }

    public void loadNextStep(View view) {
        if (index == totalSteps-1)
            return;
        if(index == totalSteps-2)
            next.setVisibility(View.INVISIBLE);
        if(index == 0)
            previous.setVisibility(View.VISIBLE);
        index++;
        setFragment();
    }
}