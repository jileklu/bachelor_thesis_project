package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */
public class WaypointsNumberExceededException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public WaypointsNumberExceededException(String errorMessage) {
        super(errorMessage);
    }
}
