package com.jileklu2.bakalarska_prace_app.builders;

import com.jileklu2.bakalarska_prace_app.mapObjects.Route;

/**
 *  Class dedicated to building GPX format
 */
public class GpxBuilder {
    /**
     *
     * @param route Correct route builder is using
     * @return Created GPX string
     */
    public static String buildRouteGpx(Route route) {
        if(route == null)
            throw new NullPointerException("Arguments can't be null.");

        StringBuilder gpxStringBuilder = new StringBuilder("<gpx version=\"1.1\" creator=\"jileklu2\" xmlns=\"http://www.topografix.com/GPX/1/1\">\n");
        gpxStringBuilder.append(route.toGPX()).append("\n");
        gpxStringBuilder.append("</gpx>");
        return gpxStringBuilder.toString();
    }
}
