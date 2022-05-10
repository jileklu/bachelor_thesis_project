package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

public class OverQueryLimitException extends Exception {
    public OverQueryLimitException(String errorMessage) {
        super(errorMessage);
    }
}
