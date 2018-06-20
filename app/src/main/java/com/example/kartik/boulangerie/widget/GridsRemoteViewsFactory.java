package com.example.kartik.boulangerie.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kartik.boulangerie.R;

import java.util.List;

import static com.example.kartik.boulangerie.widget.IngredientsWidgetProvider.ingredientsList;

class GridsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext = null;
    List<String> remoteViewingredientsList;
    public GridsRemoteViewsFactory(Context context, Intent intent, List<String> remoteViewingredientsList) {
        mContext = context;
        this.remoteViewingredientsList = remoteViewingredientsList;
    }

    @Override
    public void onCreate() {}

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);
        remoteViews.setTextViewText(R.id.widget_item, remoteViewingredientsList.get(position));
        Intent fillInIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.widget_item, fillInIntent);
        return remoteViews;
    }

    @Override
    public void onDataSetChanged() {
        remoteViewingredientsList = ingredientsList;
    }

    @Override
    public int getCount() {
        return remoteViewingredientsList.size();
    }

    @Override
    public void onDestroy() {}

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
