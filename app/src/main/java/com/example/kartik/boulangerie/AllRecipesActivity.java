package com.example.kartik.boulangerie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartik.boulangerie.Objects.Ingredient;
import com.example.kartik.boulangerie.Objects.Recipe;
import com.example.kartik.boulangerie.Objects.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllRecipesActivity extends AppCompatActivity {

    String mJSONURLString = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    JSONArray jsonArray;
    static ArrayList<Recipe> recipes = new ArrayList<>();
    int i, j;


    int recipeId, servings, quantity, stepId,noOfIngredients, noOfSteps;
    String recipeName, recipeImage, measure, ingredient, shortDescription, description, videoUrl, thumbnailUrl;
    JSONObject recipeObject, ingredientObject, stepObject;
    JSONArray ingredientArray, stepArray;
    RecyclerView recyclerView;
    AllRecipesAdapter recipesAdapter;
    GridView gridView;
    Boolean phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipes);


        makeVolleyRequest(true);

        Log.d("Recipe size : ", Integer.toString(this.recipes.size()));

        if(findViewById(R.id.all_recipes_recyclerview)!=null){
            phone = true;
            recyclerView = (RecyclerView) findViewById(R.id.all_recipes_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else if(findViewById(R.id.all_recipes_gridview)!= null){
            phone = false;
            gridView = (GridView)findViewById(R.id.all_recipes_gridview);
        }


    }

    protected void makeVolleyRequest(final Boolean app) {
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest request = new JsonArrayRequest(mJSONURLString,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        jsonArray = array;
                        parseJsonArray(app);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(AllRecipesActivity.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(request);

    }

    private void parseJsonArray(boolean app) {


        for(i = 0; i<jsonArray.length(); i++){

            noOfIngredients = 0;
            noOfSteps = 0;


            try {
                recipeObject = jsonArray.getJSONObject(i);


                recipeId = recipeObject.getInt("id");
                recipeName = recipeObject.getString("name");
                servings = recipeObject.getInt("servings");
                recipeImage= recipeObject.getString("image");

                ArrayList<Ingredient> ingredients= new ArrayList<>();
                ingredientArray = recipeObject.getJSONArray("ingredients");
                for(j = 0; j<ingredientArray.length(); j++){
                    ingredientObject = ingredientArray.getJSONObject(j);
                    quantity = ingredientObject.getInt("quantity");
                    measure = ingredientObject.getString("measure");
                    ingredient = ingredientObject.getString("ingredient");
                    ingredients.add(new Ingredient(quantity, measure, ingredient));
                    noOfIngredients++;
                }

                ArrayList<Step> steps = new ArrayList<>();
                stepArray = recipeObject.getJSONArray("steps");
                for(j = 0; j<stepArray.length(); j++){
                    stepObject = stepArray.getJSONObject(j);
                    stepId = stepObject.getInt("id");
                    shortDescription = stepObject.getString("shortDescription");
                    description = stepObject.getString("description");
                    videoUrl = stepObject.getString("videoURL");
                    thumbnailUrl = stepObject.getString("thumbnailURL");
                    steps.add(new Step(stepId, shortDescription, description, videoUrl, thumbnailUrl));
                    noOfSteps++;
                }

                this.recipes.add(new Recipe(recipeId, recipeName, ingredients, steps, servings, recipeImage, noOfIngredients, noOfSteps));
                Log.d("Recipe added: ", recipeName);

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

        if(phone){
            recipesAdapter = new AllRecipesAdapter(recipes, this, app);
            recyclerView.setAdapter(recipesAdapter);
        }else{
            AllRecipesGridAdapter gridAdapter = new AllRecipesGridAdapter(this, recipes);
            gridView.setAdapter(gridAdapter);
        }

    }
}
