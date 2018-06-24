package com.example.kartik.boulangerie.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import java.util.List;

public class IngredientsGridWidgetService extends RemoteViewsService {
    List<String> remoteViewingredientsList;

        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return new WidgetRVFactory(this.getApplicationContext(),intent, remoteViewingredientsList);
        }
}

