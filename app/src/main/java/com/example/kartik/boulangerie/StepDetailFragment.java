package com.example.kartik.boulangerie;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartik.boulangerie.Objects.Step;
import com.example.kartik.boulangerie.dummy.DummyContent;


public class StepDetailFragment extends Fragment {

    Step step;
    TextView description;

    public static StepDetailFragment newInstance(Step step){
        StepDetailFragment detailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    public StepDetailFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        step = getArguments().getParcelable("step");
        description = (TextView) rootView.findViewById(R.id.item_detail);
        description.setText(step.getDescription());

        return rootView;
    }
}
