package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.here;

import com.jileklu2.bakalarska_prace_app.exceptions.responseStatus.*;

public interface HereResponseStatusHandler {
    void handle(String status) throws CreatedException, OverDailyLimitException, RequestDeniedException,
                                      UnknownStatusException, LocationNotFoundException;
}
