package com.jileklu2.bakalarska_prace_app.exceptions.dateSelection;

/**
 *
 */

public class TooManyTimeStampsException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public TooManyTimeStampsException(String errorMessage) {
        super(errorMessage);
    }
}
