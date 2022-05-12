package com.jileklu2.bakalarska_prace_app.exceptions.routes;

/**
 *
 */
public class EmptyTimeStampsSetException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public EmptyTimeStampsSetException(String errorMessage) {
        super(errorMessage);
    }
}
