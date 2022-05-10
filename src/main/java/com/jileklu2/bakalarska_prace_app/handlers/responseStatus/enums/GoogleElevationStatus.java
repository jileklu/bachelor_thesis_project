package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums;

public enum GoogleElevationStatus {
    OK(200),
    DATA_NOT_AVAILABLE (404),
    INVALID_REQUEST(400),
    OVER_DAILY_LIMIT(402),
    OVER_QUERY_LIMIT(429),
    REQUEST_DENIED(403),
    UNKNOWN_ERROR(520);

    public final int value;
    GoogleElevationStatus(int value) {
        this.value = value;
    }
}
