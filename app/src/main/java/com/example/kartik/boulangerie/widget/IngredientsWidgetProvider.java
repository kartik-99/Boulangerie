package com.example.kartik.boulangerie.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;
import com.example.kartik.boulangerie.R;

import java.util.ArrayList;

import static com.example.kartik.boulangerie.widget.UpdateIngredientsService.FROM_ACTIVITY_INGREDIENTS_LIST;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class IngredientsWidgetProvider extends AppWidgetProvider {
    static ArrayList<String> ingredientsList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);
        Intent intent = new Intent(context, IngredientsGridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {}

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientsWidgetProvider.class));

        final String action = intent.getAction();

        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
            ingredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
            IngredientsWidgetProvider.updateBakingWidgets(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onEnabled(Context context) {}

    @Override
    public void onDisabled(Context context) {}

}

