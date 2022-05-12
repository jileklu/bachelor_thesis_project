package com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders;

/**
 *
 */
public class EmptyCoordinatesListException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public EmptyCoordinatesListException(String errorMessage) {
        super(errorMessage);
    }
}
