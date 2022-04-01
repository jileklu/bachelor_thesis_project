package com.jileklu2.bakalarska_prace_app.parsers;

import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import org.json.JSONObject;

public class GoogleJsonStepParser {
    public static int parseDistance(JSONObject object) {
        JSONObject jsonDistance = object.getJSONObject("distance");
        return jsonDistance.getInt("value");
    }

    public static int parseDuration(JSONObject object) {
        JSONObject jsonDistance = object.getJSONObject("duration");
        return jsonDistance.getInt("value");
    }

    public static Coordinates parseOrigin(JSONObject object) {
        JSONObject jsonOrigin = object.getJSONObject("start_location");
        return new Coordinates(jsonOrigin.getDouble("lat"), jsonOrigin.getDouble("lng"));
    }

    public static Coordinates parseDestination(JSONObject object) {
        JSONObject jsonDestination = object.getJSONObject("end_location");
        return new Coordinates(jsonDestination.getDouble("lat"), jsonDestination.getDouble("lng"));
    }
}
