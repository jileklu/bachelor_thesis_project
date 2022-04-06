package com.jileklu2.bakalarska_prace_app.gui;

import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import org.json.JSONException;

public interface RoutesContext {
    void setDefaultRoute(Route defaultRoute) throws JSONException;
    Route getDefaultRoute();
}
