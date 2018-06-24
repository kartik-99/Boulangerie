package com.example.kartik.boulangerie.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.kartik.boulangerie.objects.Recipe;
import com.example.kartik.boulangerie.objects.Step;
import com.example.kartik.boulangerie.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    FragmentTransaction ft;
    Step step;
    ArrayList<Step> steps;
    int index, totalSteps;
    Recipe recipe;
    TextView previous, next;
    String ingredients = "INGREDIENTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState!=null){
            recipe = savedInstanceState.getParcelable("recipe");
            index = savedInstanceState.getInt("index");
        }else{
            Intent intent = getIntent();
            recipe = intent.getParcelableExtra("recipe");
            index = intent.getIntExtra("index", 0);
        }

        steps = recipe.getSteps();
        totalSteps = steps.size();
        previous = ButterKnife.findById(this, R.id.prev_textview);
        next = ButterKnife.findById(this, R.id.next_textView);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(recipe.getName());
        toolbar.setTitleTextColor(Color.WHITE);
        if(index == -1) {
            setFragment(false);
            previous.setVisibility(View.INVISIBLE);
            return;
        }
        else if(index == totalSteps-1)
            next.setVisibility(View.INVISIBLE);
        setFragment(true);
    }

    public void setFragment(Boolean status){
        if(status){
            step = steps.get(index);
            ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment= StepDetailFragment.newInstance(step);
            ft.replace(R.id.item_detail_container, fragment);
            ft.commit();
        }else{
            ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment= IngredientDetailFragment.newInstance(recipe);
            ft.replace(R.id.item_detail_container, fragment);
            ft.commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void loadPreviousStep(View view) {
        index--;
        if(index == -1){
            setFragment(false);
            previous.setVisibility(View.INVISIBLE);
            return;
        }
        if(index == totalSteps -2)
            next.setVisibility(View.VISIBLE);
        setFragment(true);

    }

    public void loadNextStep(View view) {
        index++;
        if(index ==0)
            previous.setVisibility(View.VISIBLE);
        if(index == totalSteps-1)
            next.setVisibility(View.GONE);
        setFragment(true);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipe", recipe);
        outState.putInt("index", index);
    }
}