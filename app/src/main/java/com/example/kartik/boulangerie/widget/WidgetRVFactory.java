package com.example.kartik.boulangerie.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kartik.boulangerie.R;

import java.util.List;

import static com.example.kartik.boulangerie.widget.IngredientsWidgetProvider.ingredientsList;

class WidgetRVFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;

    List<String> widgetIngredientsList;
    public WidgetRVFactory(Context context, Intent intent, List<String> widgetIngredientsList) {
        mContext = context;
        this.widgetIngredientsList = widgetIngredientsList;
    }

    @Override
    public void onCreate() {}

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);
        views.setTextViewText(R.id.widget_item, widgetIngredientsList.get(position));
        Intent fillInIntent = new Intent();
        views.setOnClickFillInIntent(R.id.widget_item, fillInIntent);
        return views;
    }

    @Override
    public void onDataSetChanged() {
        widgetIngredientsList = ingredientsList;
    }

    @Override
    public void onDestroy() {}

    @Override
    public int getViewTypeCount() {return 1;}

    @Override
    public RemoteViews getLoadingView() {return null;}

    @Override
    public int getCount() {
        return widgetIngredientsList.size();
    }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public boolean hasStableIds() {return true;}
}