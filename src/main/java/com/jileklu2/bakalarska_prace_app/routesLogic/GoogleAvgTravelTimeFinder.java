package com.jileklu2.bakalarska_prace_app.routesLogic;

import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.HttpRequestStringBuilder;
import com.jileklu2.bakalarska_prace_app.errors.GoogleDirectionsStatus;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.parsers.GoogleJsonParser;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.routesLogic.mapObjects.RouteStep;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GoogleAvgTravelTimeFinder {
    public static void findRouteStepsAvgTravelTime(Route route, HashSet<LocalDateTime> timeStamps)
        throws DurationOutOfBoundsException, EmptyDestinationsListException, EmptyTimeStampsSetException {
        if(route == null || timeStamps == null)
            throw new NullPointerException("Arguments can't be null.");

        if(timeStamps.isEmpty())
            throw new EmptyTimeStampsSetException("Can't create average travel time from an empty time stamps set");

        List<Coordinates> origins = new ArrayList<>();
        List<Coordinates> destinations = new ArrayList<>();
        List<Double> durations = new ArrayList<>();

        for(RouteStep routeStep : route.getRouteSteps() ) {
            /* todo
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
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpRequestStringBuilder.googleMatrixRequest(origins, destinations, timeStamp)))
                .build();

            JSONObject jsonResponse = getTravelTimeMatrixResponse(request);

            String status = jsonResponse.getString("status");

            if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name())){
                // todo
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

    private static JSONObject getTravelTimeMatrixResponse(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();

        String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join());

        return new JSONObject(response);
    }
}
