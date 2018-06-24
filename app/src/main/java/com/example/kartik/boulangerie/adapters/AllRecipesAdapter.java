package com.example.kartik.boulangerie.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.kartik.boulangerie.objects.Recipe;
import com.example.kartik.boulangerie.R;
import com.example.kartik.boulangerie.ui.RecipeOverviewActivity;

import java.util.ArrayList;


public class AllRecipesAdapter extends RecyclerView.Adapter<AllRecipesAdapter.RecipePlaceHolder> {

    private ArrayList<Recipe> recipes;
    private Context context;
    private LayoutInflater inflater;
    String summaryString;
    Boolean appOrWidget;


    public AllRecipesAdapter(ArrayList<Recipe> recipes, Context context, Boolean appOrWidget) {
        this.recipes = recipes;
        this.context = context;
        this.appOrWidget = appOrWidget;
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
        if(!TextUtils.isEmpty(recipe.getImage())){
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

        holder.recipeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appOrWidget) {
                    Intent intent = new Intent(context, RecipeOverviewActivity.class);
                    intent.putExtra("recipe", recipe);
                    context.startActivity(intent);
                }
            }
        });
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
