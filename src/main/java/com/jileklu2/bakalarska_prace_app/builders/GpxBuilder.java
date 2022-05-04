package com.jileklu2.bakalarska_prace_app.builders;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;

public class GpxBuilder {
    public static String buildRouteGpx(Route route) {
        StringBuilder gpxStringBuilder = new StringBuilder("<gpx version=\"1.1\" creator=\"jileklu2\" xmlns=\"http://www.topografix.com/GPX/1/1\">\n");
        gpxStringBuilder.append(route.toGPX()).append("\n");
        gpxStringBuilder.append("</gpx>");
        return gpxStringBuilder.toString();
    }
}
