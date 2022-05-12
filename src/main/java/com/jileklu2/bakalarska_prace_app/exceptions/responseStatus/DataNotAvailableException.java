package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */

public class DataNotAvailableException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public DataNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
}
