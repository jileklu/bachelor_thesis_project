package com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep;

public class DurationOutOfBoundsException extends Exception{
    public DurationOutOfBoundsException(String errorMessage) {
        super(errorMessage);
    }
}
