package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google;


import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.LocationNotFoundException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.RouteLengthExceededException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.UnknownStatusException;
import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.ZeroResultsException;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.GoogleResponseStatusHandler;

import static com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.GoogleMatrixStatus.*;

public class GoogleMatrixResponseStatusHandler implements GoogleResponseStatusHandler {
    @Override
    public void handle(String status) throws LocationNotFoundException, ZeroResultsException,
    UnknownStatusException, RouteLengthExceededException {
        if(status.equalsIgnoreCase(NOT_FOUND.name())) {
            throw new LocationNotFoundException("Origin and/or destination of this pairing could not be geocoded.");
        } else if (status.equalsIgnoreCase(ZERO_RESULTS.name())) {
            throw new ZeroResultsException("No route could be found between the origin and destination.");
        } else if (status.equalsIgnoreCase(MAX_ROUTE_LENGTH_EXCEEDED.name())) {
            throw new RouteLengthExceededException("Route is too long and cannot be processed.");
        } else {
            throw new UnknownStatusException("Unknown response status.");
        }
    }
}
