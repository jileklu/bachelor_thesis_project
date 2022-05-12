package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */
public class RouteLengthExceededException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public RouteLengthExceededException(String errorMessage) {
        super(errorMessage);
    }
}
