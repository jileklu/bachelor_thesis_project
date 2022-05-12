package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */

public class OverDailyLimitException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public OverDailyLimitException(String errorMessage) {
        super(errorMessage);
    }
}
