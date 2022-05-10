package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google;

import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;
import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.GoogleResponseStatusHandler;

import static com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.GoogleElevationStatus.*;

public class GoogleElevationResponseStatusHandler implements GoogleResponseStatusHandler {
    @Override
    public void handle(String status) throws InvalidRequestException, OverDailyLimitException, OverQueryLimitException,
    RequestDeniedException, UnknownStatusException, DataNotAvailableException {
        if(status.equalsIgnoreCase(DATA_NOT_AVAILABLE.name())) {
            throw new DataNotAvailableException("There's no available data for the input locations");
        } else if (status.equalsIgnoreCase(INVALID_REQUEST.name())) {
            throw new InvalidRequestException("Provided request was invalid");
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
