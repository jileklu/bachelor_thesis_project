package com.jileklu2.bakalarska_prace_app.handlers.errorHandlers;

import com.jileklu2.bakalarska_prace_app.errors.GoogleElevationError;

public class GoogleElevationErrorHandler implements ErrorHandler{
    GoogleElevationError error;

    public GoogleElevationErrorHandler(GoogleElevationError error) {
        this.error = error;
    }

    @Override
    public void handleError() {
        //todo
    }
}
