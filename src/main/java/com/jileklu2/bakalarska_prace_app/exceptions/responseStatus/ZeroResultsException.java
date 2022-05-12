package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */
public class ZeroResultsException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public ZeroResultsException(String errorMessage) {
        super(errorMessage);
    }
}
