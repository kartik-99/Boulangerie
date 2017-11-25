package com.example.kartik.boulangerie;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.Espresso;
import android.view.View;

import com.example.kartik.boulangerie.Objects.Ingredient;
import com.example.kartik.boulangerie.Objects.Recipe;
import com.example.kartik.boulangerie.Objects.Step;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by lokeshrajarora on 12/11/17.
 */
@RunWith(AndroidJUnit4.class)
public class DetailActivityTests {
    Recipe recipe;
    ArrayList<Step> steps = new ArrayList<>();

    void makeRecipe() {

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(90, "very nices", "Water"));

        steps.add(new Step(0, "haha", "haha", "haha", "haha"));
        steps.add(new Step(0, "haha", "haha", "haha", "haha"));
        steps.add(new Step(0, "haha", "haha", "haha", "haha"));

        recipe = new Recipe(0, "haha", ingredients, steps, 0, "haha", 0, 0);
    }

    @Rule public ActivityTestRule<DetailActivity> testRule = new ActivityTestRule<DetailActivity>(DetailActivity.class){
        @Override
        protected Intent getActivityIntent() {
            makeRecipe();
            Intent i = new Intent();

            i.putExtra("recipe", recipe);
            i.putExtra("index", 0);
            return i;
        }
    };


    @Test public void prevButtonAppear(){
        Espresso.onView(withId(R.id.next_textView)).perform(click());
        Espresso.onView(withId(R.id.prev_textview)).check(matches(isDisplayed()));
    }

    @Test public void nextButtonAppear(){
        Intent intent = testRule.getActivity().getIntent();
        int i = intent.getIntExtra("index", 0);
        for(i = 0; i<steps.size()-2; i++){
            Espresso.onView(withId(R.id.next_textView)).perform(click());
        }
        Espresso.onView(withId(R.id.prev_textview)).perform(click());
        Espresso.onView(withId(R.id.next_textView)).check(matches(isDisplayed()));
    }

}
