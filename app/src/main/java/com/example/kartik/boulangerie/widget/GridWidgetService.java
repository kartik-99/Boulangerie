package com.example.kartik.boulangerie.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kartik.boulangerie.R;

import java.util.List;

import static com.example.kartik.boulangerie.widget.IngredientsWidgetProvider.ingredientsList;
public class GridWidgetService extends RemoteViewsService {
    List<String> remoteViewingredientsList;

        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return new GridsRemoteViewsFactory(this.getApplicationContext(),intent, remoteViewingredientsList);
        }
    }

