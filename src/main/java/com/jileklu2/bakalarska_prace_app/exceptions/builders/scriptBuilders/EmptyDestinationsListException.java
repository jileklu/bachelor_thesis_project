package com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders;

/**
 *
 */
public class EmptyDestinationsListException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public EmptyDestinationsListException(String errorMessage) {
        super(errorMessage);
    }
}
