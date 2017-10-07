package com.example.kartik.boulangerie;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.kartik.boulangerie.Objects.Recipe;

import java.util.ArrayList;

/**
 * Created by Kartik on 07-10-2017.
 */

public class AllRecipesAdapter extends RecyclerView.Adapter<AllRecipesAdapter.RecipePlaceHolder> {

    private ArrayList<Recipe> recipes;
    private Context context;
    private LayoutInflater inflater;
    String summaryString;

    public AllRecipesAdapter(ArrayList<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public AllRecipesAdapter.RecipePlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.card_recipe, parent, false);
        return new AllRecipesAdapter.RecipePlaceHolder(rootView);
    }

    @Override
    public void onBindViewHolder(AllRecipesAdapter.RecipePlaceHolder holder, int position) {
        final Recipe recipe= recipes.get(position);
        holder.title.setText(recipe.getName());
        summaryString = "("+recipe.getNoOfIngredients()+" ingredients, "+recipe.getNoOfSteps()+" steps)";
        holder.summary.setText(summaryString);
        if(recipe.getImage()!=""){
            Glide.with(context)
                    .load(recipe.getImage())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.recipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipePlaceHolder extends RecyclerView.ViewHolder {
        TextView title, summary;
        CardView recipeCard;
        ImageView recipeImage;
        public RecipePlaceHolder(View itemView) {
            super(itemView);
            recipeCard = (CardView) itemView.findViewById(R.id.recipe_card);
            title = (TextView) itemView.findViewById(R.id.recipe_title_textview);
            summary = (TextView) itemView.findViewById(R.id.recipe_summary_textview);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_image);
        }
    }
}
