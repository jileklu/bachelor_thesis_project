package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums;

/**
 *
 */
public enum GoogleMatrixStatus {
    OK(200),
    NOT_FOUND  (404),
    ZERO_RESULTS(204),
    MAX_ROUTE_LENGTH_EXCEEDED(4131),
    UNKNOWN_ERROR(520);

    public final int value;

    GoogleMatrixStatus(int value) {
        this.value = value;
    }
}
