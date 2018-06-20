package com.example.kartik.boulangerie.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartik.boulangerie.objects.Ingredient;
import com.example.kartik.boulangerie.objects.Recipe;
import com.example.kartik.boulangerie.R;
import com.example.kartik.boulangerie.adapters.IngredientAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientDetailFragment extends Fragment {

    ArrayList<Ingredient> ingredients;
    Recipe recipe;
    RecyclerView recyclerView;

    public static IngredientDetailFragment newInstance(Recipe recipe){
        IngredientDetailFragment detailFragment = new IngredientDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }


    public IngredientDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ingredient_detail, container, false);

        recipe = getArguments().getParcelable("recipe");
        ingredients = recipe.getIngredients();
        recyclerView = ButterKnife.findById(view, R.id.ingredients_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        IngredientAdapter adapter = new IngredientAdapter(ingredients, getContext());
        recyclerView.setAdapter(adapter);

        return view;

    }

}
