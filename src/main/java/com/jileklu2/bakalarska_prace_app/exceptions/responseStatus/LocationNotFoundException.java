package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */

public class LocationNotFoundException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public LocationNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
