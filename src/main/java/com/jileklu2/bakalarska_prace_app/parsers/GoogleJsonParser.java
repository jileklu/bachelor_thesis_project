package com.jileklu2.bakalarska_prace_app.parsers;

import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoogleJsonParser {
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

    public static List<Double> parseDurations(JSONObject object) {
        List<Double> durations = new ArrayList<>();

        JSONArray rows = object.getJSONArray("rows");
        for(int i = 0; i < rows.length(); i++) {
            JSONObject row = (JSONObject) rows.get(i);
            JSONArray elements = row.getJSONArray("elements");
            JSONObject element = (JSONObject) elements.get(i);
            JSONObject durationJson = element.getJSONObject("duration_in_traffic");
            Double duration = durationJson.getDouble("value");
            durations.add(duration);
        }

        return durations;
    }
}
