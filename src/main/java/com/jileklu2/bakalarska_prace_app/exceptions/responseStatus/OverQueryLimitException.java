package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

/**
 *
 */

public class OverQueryLimitException extends Exception {
    /**
     *
     * @param errorMessage error message
     */
    public OverQueryLimitException(String errorMessage) {
        super(errorMessage);
    }
}
