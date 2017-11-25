package com.example.kartik.boulangerie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.kartik.boulangerie.Objects.Recipe;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeOverviewActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    Recipe recipe;
    TextView ingredients_textview;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(recipe.getName());

        ingredients_textview = (TextView)findViewById(R.id.ingredients_textView);
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

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepAdapter(recipe.getSteps(), mTwoPane, this, recipe));
    }
}
