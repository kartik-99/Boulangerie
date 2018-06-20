package com.example.kartik.boulangerie.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kartik.boulangerie.objects.Recipe;
import com.example.kartik.boulangerie.R;
import com.example.kartik.boulangerie.ui.RecipeOverviewActivity;

import java.util.ArrayList;

/**
 * Created by lokeshrajarora on 10/11/17.
 */

public class AllRecipesGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<Recipe> recipes;
    LayoutInflater inflater;

    public AllRecipesGridAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int i) {
        return recipes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Recipe recipe = recipes.get(i);
        String summaryString = "("+recipe.getNoOfIngredients()+" ingredients, "+recipe.getNoOfSteps()+" steps)";

        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.card_recipe, viewGroup, false);
            view.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder)view.getTag();

        viewHolder.title = (TextView)view.findViewById(R.id.recipe_title_textview);
        viewHolder.summary = (TextView)view.findViewById(R.id.recipe_summary_textview);
        viewHolder.recipeCard = (CardView)view.findViewById(R.id.recipe_card);

        viewHolder.title.setText(recipe.getName());
        viewHolder.summary.setText(summaryString);
        viewHolder.recipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeOverviewActivity.class);
                intent.putExtra("recipe", recipe);
                context.startActivity(intent);
            }
        });

        return view;

    }



    private static class ViewHolder{
        public TextView title, summary;
        CardView recipeCard;
    }

}
