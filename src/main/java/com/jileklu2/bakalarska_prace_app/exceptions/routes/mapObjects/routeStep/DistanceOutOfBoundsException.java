package com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.routeStep;

/**
 *
 */
public class DistanceOutOfBoundsException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public DistanceOutOfBoundsException(String errorMessage) {
        super(errorMessage);
    }
}
