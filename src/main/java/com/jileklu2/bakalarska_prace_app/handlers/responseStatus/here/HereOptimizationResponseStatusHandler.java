package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.here;

import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;

import static com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.HereOptimizationStatus.*;

public class HereOptimizationResponseStatusHandler implements HereResponseStatusHandler {
    @Override
    public void handle(String status) throws CreatedException, OverDailyLimitException, RequestDeniedException,
    UnknownStatusException, LocationNotFoundException {
        int statusNum = Integer.parseInt(status);
        if(statusNum == CREATED.value) {
            throw new CreatedException("Request succeeded, and a new resource was created as a result.");
        } else if (statusNum == UNAUTHORIZED.value) {
            throw new OverDailyLimitException("Problems with API billing.");
        } else if (statusNum == FORBIDDEN.value) {
            throw new RequestDeniedException("Service denied use.");
        } else if (statusNum == NOT_FOUND.value) {
            throw new LocationNotFoundException("Waypoints could not be geocoded.");
        } else {
            throw new UnknownStatusException("Unknown response status.");
        }
    }
}
