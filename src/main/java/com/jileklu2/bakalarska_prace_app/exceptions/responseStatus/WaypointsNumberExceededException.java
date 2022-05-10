package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

public class WaypointsNumberExceededException extends Exception{
    public WaypointsNumberExceededException(String errorMessage) {
        super(errorMessage);
    }
}
