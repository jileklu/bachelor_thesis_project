package com.example.bakalarska_prace_app.errors;

public class GoogleRoutingError extends RoutingError{
    public GoogleRoutingError(GoogleDirectionsStatus errorName) {
        super(errorName.value);
    }
}
