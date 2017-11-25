package com.example.kartik.boulangerie;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by lokeshrajarora on 11/11/17.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<String> strings = new ArrayList();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        strings.add("a");
        strings.add("b");
        strings.add("c");
        strings.add("d");
        strings.add("e");


    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        String a = strings.get(i);
        remoteView.setTextViewText(R.id.widget_item_textview, a);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
