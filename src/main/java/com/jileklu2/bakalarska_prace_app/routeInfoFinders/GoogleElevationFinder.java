package com.jileklu2.bakalarska_prace_app.routeInfoFinders;

import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.GoogleElevationStatus;
import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.EmptyCoordinatesListException;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google.GoogleElevationResponseStatusHandler;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google.GoogleResponseStatusHandler;
import com.jileklu2.bakalarska_prace_app.mapObjects.Coordinates;
import com.jileklu2.bakalarska_prace_app.mapObjects.Route;
import com.jileklu2.bakalarska_prace_app.builders.scriptBuilders.HttpRequestStringBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.List;

import static com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.GoogleElevationStatus.*;

/**
 *
 */
public class GoogleElevationFinder {
    private static final GoogleResponseStatusHandler responseStatusHandler = new GoogleElevationResponseStatusHandler();

    /**
     *
     * @param route
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
    public static void findRouteWaypointsElevations(Route route) throws RouteLengthExceededException,
    RequestDeniedException, OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException,
    ZeroResultsException, InvalidRequestException, DataNotAvailableException, LocationNotFoundException, UnknownStatusException {
        if(route == null)
            throw new NullPointerException("Arguments can't be null.");

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(HttpRequestStringBuilder.googleElevationRequest(route)))
            .build();

        JSONObject jsonResponse = getElevationResponse(request);

        String status = jsonResponse.getString("status");

        if(!status.equalsIgnoreCase(OK.name())){
            responseStatusHandler.handle(status);
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

    /**
     *
     * @param coordinatesList
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
    public static void findCoordinatesElevation(List<Coordinates> coordinatesList) throws RouteLengthExceededException,
    RequestDeniedException, OverDailyLimitException, OverQueryLimitException, WaypointsNumberExceededException,
    ZeroResultsException, InvalidRequestException, DataNotAvailableException, LocationNotFoundException,
    UnknownStatusException {
        if(coordinatesList == null)
            throw new NullPointerException("Arguments can't be null.");

        if(coordinatesList.isEmpty())
            return;

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(URI.create(HttpRequestStringBuilder.googleElevationRequest(coordinatesList)))
                .build();
        } catch (EmptyCoordinatesListException e) {
            e.printStackTrace();
            return;
        }

        JSONObject jsonResponse = getElevationResponse(request);

        String status = jsonResponse.getString("status");

        if(!status.equalsIgnoreCase(GoogleElevationStatus.OK.name())){
            responseStatusHandler.handle(status);
        } else {
            JSONArray elevations = jsonResponse.getJSONArray("results");
            for(int i = 0; i < elevations.length(); i++) {
                coordinatesList.get(i).setElevation(getElevationOnIndex(elevations, i));
            }
        }
    }

    /**
     *
     * @param request
     * @return
     */
    private static JSONObject getElevationResponse(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();

        String response = String.valueOf(client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .join());

        return new JSONObject(response);
    }

    /**
     *
     * @param elevations
     * @param i
     * @return
     */
    private static Double getElevationOnIndex(JSONArray elevations, int i) {
        return elevations.getJSONObject(i).getDouble("elevation");
    }
}
