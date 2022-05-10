package com.jileklu2.bakalarska_prace_app.errors;

import com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums.GoogleDirectionsStatus;

public class GoogleRoutingError extends RoutingError{
    public GoogleRoutingError(GoogleDirectionsStatus errorName) {
        super(errorName.value);
    }
}
