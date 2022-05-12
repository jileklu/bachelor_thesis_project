package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */
public class UnknownStatusException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public UnknownStatusException(String errorMessage) {
        super(errorMessage);
    }
}
