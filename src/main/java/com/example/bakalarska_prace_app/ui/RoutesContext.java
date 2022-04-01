package com.example.bakalarska_prace_app.ui;

import com.example.bakalarska_prace_app.mapObjects.Route;
import org.json.JSONException;

public interface RoutesContext {
    void setDefaultRoute(Route defaultRoute) throws JSONException;
    Route getDefaultRoute();
}
