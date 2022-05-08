package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.errors.GoogleDirectionsStatus;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.HttpRequestStringBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.List;

public class GoogleElevationFinder {
    public static void findRouteWaypointsElevations(Route route) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(HttpRequestStringBuilder.googleElevationRequest(route)))
            .build();

        JSONObject jsonResponse = getElevationResponse(request);

        String status = jsonResponse.getString("status");

        if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name())){
            GoogleDirectionsStatus errorStatus = GoogleDirectionsStatus.valueOf(status);
            //RoutingError error = new GoogleElevationError(errorStatus);
            //ErrorHandler errorHandler = new GoogleElevationErrorHandler((GoogleElevationError) error); todo
            //errorHandler.handleError();
        } else {
            JSONArray elevations = jsonResponse.getJSONArray("results");
            route.getOrigin().setElevation(getElevationOnIndex(elevations,0));
            route.getDestination().setElevation(getElevationOnIndex(elevations,1));
            Iterator<Coordinates> it = route.getWaypoints().iterator();
            for(int i = 2; i < elevations.length() - route.getRouteSteps().size() * 2; i++) {
                it.next().setElevation(getElevationOnIndex(elevations, i));
            }

            for(int i = route.getWaypoints().size() + 2; i < elevations.length(); i += 2) {
                route.getRouteSteps().get((int) Math.floor((i-2-route.getWaypoints().size())/2))
                    .getOrigin().setElevation((getElevationOnIndex(elevations,i)));
                route.getRouteSteps().get((int) Math.floor((i-2-route.getWaypoints().size())/2))
                    .getDestination().setElevation((getElevationOnIndex(elevations,i + 1)));
            }
        }
    }

    public static void findCoordinatesElevation(List<Coordinates> coordinatesList) {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(HttpRequestStringBuilder.googleElevationRequest(coordinatesList)))
            .build();

        JSONObject jsonResponse = getElevationResponse(request);

        String status = jsonResponse.getString("status");

        if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name())){
            GoogleDirectionsStatus errorStatus = GoogleDirectionsStatus.valueOf(status);
            //RoutingError error = new GoogleElevationError(errorStatus);
            //ErrorHandler errorHandler = new GoogleElevationErrorHandler((GoogleElevationError) error); todo
            //errorHandler.handleError();
        } else {
            JSONArray elevations = jsonResponse.getJSONArray("results");
            for(int i = 0; i < elevations.length(); i++) {
                coordinatesList.get(i).setElevation(getElevationOnIndex(elevations, i));
            }
        }
    }

    private static JSONObject getElevationResponse(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();

        String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join());

        return new JSONObject(response);
    }

    private static Double getElevationOnIndex(JSONArray elevations, int i) {
        return elevations.getJSONObject(i).getDouble("elevation");
    }
}
