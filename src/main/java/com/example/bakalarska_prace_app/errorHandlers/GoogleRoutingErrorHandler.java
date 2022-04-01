package com.example.bakalarska_prace_app.errorHandlers;

import com.example.bakalarska_prace_app.errors.GoogleRoutingError;

public class GoogleRoutingErrorHandler implements ErrorHandler {
    GoogleRoutingError error;

    public GoogleRoutingErrorHandler(GoogleRoutingError error) {
        this.error = error;
    }

    @Override
    public void handleError() {
        //todo
    }
}
