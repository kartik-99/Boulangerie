package com.example.kartik.boulangerie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartik.boulangerie.objects.Ingredient;
import com.example.kartik.boulangerie.R;

import java.util.ArrayList;

/**
 * Created by lokeshrajarora on 06/11/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    ArrayList<Ingredient> ingredients;
    LayoutInflater inflater;
    Context context;

    public IngredientAdapter(ArrayList<Ingredient> ingredients, Context context){
        this.ingredients = ingredients;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.card_ingredient, parent, false);
        return new IngredientAdapter.IngredientViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        String finalMeasure = Float.toString(ingredient.getQuantity()) + " " + ingredient.getMeasure();
        //holder.number.setText(Integer.toString(position+1));
        holder.name.setText(ingredient.getIngredient());
        holder.amount.setText(finalMeasure);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView number, name, amount;
        public IngredientViewHolder(View itemView) {
            super(itemView);
            //number = (TextView)itemView.findViewById(R.id.ingredient_no);
            name = (TextView)itemView.findViewById(R.id.ingredient_name);
            amount = (TextView)itemView.findViewById(R.id.ingredient_qty);
        }
    }
}
