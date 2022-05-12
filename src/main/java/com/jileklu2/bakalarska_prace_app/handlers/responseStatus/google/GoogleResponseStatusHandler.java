package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.google;

import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;

/**
 *
 */
public interface GoogleResponseStatusHandler {
    void handle(String status) throws LocationNotFoundException, ZeroResultsException, WaypointsNumberExceededException,
                                      RouteLengthExceededException, InvalidRequestException, OverDailyLimitException,
                                      OverQueryLimitException, UnknownStatusException, RequestDeniedException,
                                      DataNotAvailableException;
}
