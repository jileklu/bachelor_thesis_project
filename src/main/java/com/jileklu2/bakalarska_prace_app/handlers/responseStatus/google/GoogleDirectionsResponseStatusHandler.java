package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google;

import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.GoogleResponseStatusHandler;

import static com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.GoogleDirectionsStatus.*;

public class GoogleDirectionsResponseStatusHandler implements GoogleResponseStatusHandler {
    @Override
    public void handle(String status) throws LocationNotFoundException, ZeroResultsException,
    WaypointsNumberExceededException, RouteLengthExceededException, InvalidRequestException, OverDailyLimitException,
    OverQueryLimitException, UnknownStatusException, RequestDeniedException {
        if(status.equalsIgnoreCase(NOT_FOUND.name())) {
            throw new LocationNotFoundException("Origin, destination, or waypoints could not be geocoded.");
        } else if(status.equalsIgnoreCase(ZERO_RESULTS.name())) {
            throw new ZeroResultsException("No route could be found between the origin and destination.");
        } else if (status.equalsIgnoreCase(MAX_WAYPOINTS_EXCEEDED.name())) {
            throw new WaypointsNumberExceededException("Too many waypoints were provided.");
        } else if (status.equalsIgnoreCase(MAX_ROUTE_LENGTH_EXCEEDED.name())) {
            throw new RouteLengthExceededException("Route is too long and cannot be processed.");
        } else if (status.equalsIgnoreCase(INVALID_REQUEST.name())) {
            throw new InvalidRequestException("Provided request was invalid.");
        } else if (status.equalsIgnoreCase(OVER_DAILY_LIMIT.name())) {
            throw new OverDailyLimitException("Problems with API billing.");
        } else if (status.equalsIgnoreCase(OVER_QUERY_LIMIT.name())) {
            throw new OverQueryLimitException("Service has received too many requests.");
        } else if (status.equalsIgnoreCase(REQUEST_DENIED.name())) {
            throw new RequestDeniedException("Service denied use.");
        } else {
            throw new UnknownStatusException("Unknown response status.");
        }
    }
}
