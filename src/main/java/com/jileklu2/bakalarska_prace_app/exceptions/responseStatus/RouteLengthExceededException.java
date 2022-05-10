package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

public class RouteLengthExceededException extends Exception {
    public RouteLengthExceededException(String errorMessage) {
        super(errorMessage);
    }
}
