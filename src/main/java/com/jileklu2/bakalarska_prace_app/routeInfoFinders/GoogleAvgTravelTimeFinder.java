package com.jileklu2.bakalarska_prace_app.routeInfoFinders;

import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.HttpRequestStringBuilder;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.GoogleDirectionsStatus;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyDestinationsListException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.EmptyTimeStampsSetException;
import com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep.DurationOutOfBoundsException;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google.GoogleResponseStatusHandler;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google.GoogleMatrixResponseStatusHandler;
import com.jileklu2.bakalarska_prace_app.mapObjects.splitters.RouteSplitter;
import com.jileklu2.bakalarska_prace_app.parsers.GoogleJsonParser;
import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.mapObjects.RouteStep;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class GoogleAvgTravelTimeFinder {
    private static final GoogleResponseStatusHandler responseStatusHandler = new GoogleMatrixResponseStatusHandler();

    /**
     *
     * @param route
     * @param timeStamps
     * @throws EmptyTimeStampsSetException
     * @throws InterruptedException
     * @throws RouteLengthExceededException
     * @throws RequestDeniedException
     * @throws OverDailyLimitException
     * @throws OverQueryLimitException
     * @throws WaypointsNumberExceededException
     * @throws ZeroResultsException
     * @throws InvalidRequestException
     * @throws DataNotAvailableException
     * @throws LocationNotFoundException
     * @throws UnknownStatusException
     */
    public static void findRouteStepsAvgTravelTime(Route route, HashSet<LocalDateTime> timeStamps)
        throws EmptyTimeStampsSetException, InterruptedException, RouteLengthExceededException, RequestDeniedException,
        OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException, ZeroResultsException,
        InvalidRequestException, DataNotAvailableException, LocationNotFoundException, UnknownStatusException {
        if(route == null || timeStamps == null)
            throw new NullPointerException("Arguments can't be null.");

        if(timeStamps.isEmpty())
            throw new EmptyTimeStampsSetException("Can't create average travel time from an empty time stamps set");

        List<Thread> threads = new ArrayList<>();
        List<List<RouteStep>> helpingSteps = RouteSplitter.splitRouteSteps(route);
        BlockingQueue<String> responseStatuses = new LinkedBlockingQueue<>();

        for(List<RouteStep> steps : helpingSteps) {
            List<Coordinates> origins = new ArrayList<>();
            List<Coordinates> destinations = new ArrayList<>();
            List<Double> durations = new ArrayList<>();

            for(RouteStep routeStep : steps) {
                origins.add(routeStep.getOrigin());
                destinations.add(routeStep.getDestination());
                durations.add(0.0);
            }

            Runnable r = () -> {
                try {
                    responseStatuses.add(findAvgTravelTime(new ArrayList<>(origins),
                                                   new ArrayList<>(destinations),
                                                   new ArrayList<>(durations),
                                                   steps,
                                                   timeStamps)
                    );
                } catch (EmptyDestinationsListException | DurationOutOfBoundsException e) {
                    throw new RuntimeException(e);
                }
            };

            Thread t = new Thread(r);
            threads.add(new Thread(r));
            t.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }

        for (String status : responseStatuses) {
            if(status == null)
                throw new NullPointerException("Response status is null");

            if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name()))
                responseStatusHandler.handle(status);
        }
    }

    /**
     *
     * @param origins
     * @param destinations
     * @param durations
     * @param routeSteps
     * @param timeStamps
     * @return
     * @throws EmptyDestinationsListException
     * @throws DurationOutOfBoundsException
     */
    private static String findAvgTravelTime(List<Coordinates> origins,
                                          List<Coordinates> destinations,
                                          List<Double> durations,
                                          List<RouteStep> routeSteps,
                                          HashSet<LocalDateTime> timeStamps) throws EmptyDestinationsListException,
                                                                                    DurationOutOfBoundsException {
        String status = null;
        for(LocalDateTime timeStamp : timeStamps) {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HttpRequestStringBuilder.googleMatrixRequest(origins, destinations, timeStamp)))
                .build();

            JSONObject jsonResponse = getTravelTimeMatrixResponse(request);

            status = jsonResponse.getString("status");

            if(!status.equalsIgnoreCase(GoogleDirectionsStatus.OK.name())){
                return status;
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
            routeSteps.get(i).setDuration(tmpDuration);
        }

        return status;
    }

    /**
     *
     * @param request
     * @return
     */
    private static JSONObject getTravelTimeMatrixResponse(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();

        String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join());

        return new JSONObject(response);
    }
}
