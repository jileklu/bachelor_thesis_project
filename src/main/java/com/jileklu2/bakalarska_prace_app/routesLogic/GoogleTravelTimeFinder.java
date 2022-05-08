package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.HttpRequestStringBuilder;
import com.jileklu2.bakalarska_prace_app.errors.GoogleDirectionsStatus;
import com.jileklu2.bakalarska_prace_app.parsers.GoogleJsonParser;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import javafx.util.Pair;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GoogleTravelTimeFinder {
    public static void FindRouteStepsTravelTime(Route route, HashSet<LocalDateTime> timeStamps) {
        List<Coordinates> origins = new ArrayList<>();
        List<Coordinates> destinations = new ArrayList<>();
        List<Double> durations = new ArrayList<>();

        List<Pair<List<Coordinates>,List<Coordinates>>> orgDestListPairs = new ArrayList<>();

       // int counter = 0;

        for(RouteStep routeStep : route.getRouteSteps() ) {
            /*
            if(counter == 10) {
                orgDestListPairs.add(new Pair<>(new ArrayList<>(origins), new ArrayList<>(destinations)));
                origins.clear();
                destinations.clear();
                counter = 0;
            }
            */
            origins.add(routeStep.getOrigin());
            destinations.add(routeStep.getDestination());
            durations.add(0.0);
           // counter++;
        }

        for(LocalDateTime timeStamp : timeStamps) {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpRequestStringBuilder.googleMatrixRequest(origins, destinations, timeStamp)))
                .build();

            String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join());

            JSONObject jsonResponse = new JSONObject(response);

            String status = jsonResponse.getString("status");

            if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name())){
                GoogleDirectionsStatus errorStatus = GoogleDirectionsStatus.valueOf(status);
                //RoutingError error = new GoogleElevationError(errorStatus);
                //ErrorHandler errorHandler = new GoogleElevationErrorHandler((GoogleElevationError) error); todo
                //errorHandler.handleError();
            } else {
                List<Double> receivedDurations = GoogleJsonParser.parseDurations(jsonResponse);

                for (int i = 0; i < durations.size(); i++) {
                    Double tmpDuration = durations.get(i);
                    tmpDuration += receivedDurations.get(i);
                    durations.set(i, tmpDuration);
                }
            }
        }

        for (int i = 0; i < durations.size(); i++) {
            Double tmpDuration = durations.get(i);
            tmpDuration /= timeStamps.size();
            route.getRouteSteps().get(i).setDuration(tmpDuration);
        }
    }
}
