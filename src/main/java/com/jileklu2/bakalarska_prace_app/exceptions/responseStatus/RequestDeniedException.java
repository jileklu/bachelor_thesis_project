package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */
public class RequestDeniedException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public RequestDeniedException(String errorMessage) {
        super(errorMessage);
    }
}
