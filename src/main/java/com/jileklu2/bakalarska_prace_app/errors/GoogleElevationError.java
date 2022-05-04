package com.jileklu2.bakalarska_prace_app.errors;

public class GoogleElevationError extends ElevationError{
    public GoogleElevationError(GoogleDirectionsStatus errorName) {
        super(errorName.value);
    }
}
