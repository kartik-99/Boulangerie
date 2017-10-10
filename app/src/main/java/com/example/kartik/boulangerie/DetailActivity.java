package com.example.kartik.boulangerie;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kartik.boulangerie.Objects.Step;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    FragmentTransaction ft;
    Step step;
    ArrayList<Step> steps;
    int index, totalSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        step = intent.getParcelableExtra("step");
        /*totalSteps = steps.size();
        index = intent.getIntExtra("index", 1);*/



        setFragment();
    }

    public void setFragment(){
        /*step = steps.get(index);*/
        ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment= StepDetailFragment.newInstance(step);
        ft.replace(R.id.item_detail_container, fragment);
        ft.commit();
    }
}