package com.example.kartik.boulangerie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.example.kartik.boulangerie.objects.Ingredient;
import com.example.kartik.boulangerie.objects.Recipe;
import com.example.kartik.boulangerie.R;
import com.example.kartik.boulangerie.adapters.StepAdapter;
import com.example.kartik.boulangerie.widget.UpdateIngredientsService;

import java.util.ArrayList;

import butterknife.ButterKnife;
public class RecipeOverviewActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private boolean mTwoPane;
    static Recipe recipe;
    TextView ingredients_textview;
    FragmentTransaction ft;
    ArrayList<String> widgetIngredients=new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(recipe.getName());

        ingredients_textview = ButterKnife.findById(this, R.id.ingredients_textView);
        ingredients_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTwoPane){
                    ft = getSupportFragmentManager().beginTransaction();
                    Fragment fragment= IngredientDetailFragment.newInstance(recipe);
                    ft.replace(R.id.item_detail_container, fragment);
                    ft.commit();
                }else{
                    Intent intent = new Intent(RecipeOverviewActivity.this, DetailActivity.class);
                    intent.putExtra("recipe", recipe);
                    intent.putExtra("index", -1);
                    startActivity(intent);
                }
            }
        });

        View recyclerView = ButterKnife.findById(this, R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        ArrayList<Ingredient> ingredients = recipe.getIngredients();
        Ingredient a;
        for(int i = 0; i<ingredients.size(); i++){
            a = ingredients.get(i);
            widgetIngredients.add(a.getIngredient()+"\n"+Float.toString(a.getQuantity())+" "+a.getMeasure()+"\n");
        }
        UpdateIngredientsService.startBakingService(getApplication(), widgetIngredients);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepAdapter(recipe.getSteps(), mTwoPane, this, recipe));
    }


}
