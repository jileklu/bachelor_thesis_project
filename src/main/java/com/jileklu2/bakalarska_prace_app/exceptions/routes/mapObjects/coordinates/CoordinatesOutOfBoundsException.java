package com.jileklu2.bakalarska_prace_app.exceptions.routes.mapObjects.coordinates;

/**
 *
 */
public class CoordinatesOutOfBoundsException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public CoordinatesOutOfBoundsException(String errorMessage) {
        super(errorMessage);
    }
}
