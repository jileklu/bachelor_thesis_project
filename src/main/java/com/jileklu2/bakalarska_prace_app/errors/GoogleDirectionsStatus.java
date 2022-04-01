package com.jileklu2.bakalarska_prace_app.errors;

public enum GoogleDirectionsStatus {
    OK(200),
    NOT_FOUND(404),
    ZERO_RESULTS(204),
    MAX_WAYPOINTS_EXCEEDED(4130),
    MAX_ROUTE_LENGTH_EXCEEDED(4131),
    INVALID_REQUEST(400),
    OVER_DAILY_LIMIT(402),
    OVER_QUERY_LIMIT(429),
    REQUEST_DENIED(403),
    UNKNOWN_ERROR(520);

    public final int value;

    GoogleDirectionsStatus(int value) {
        this.value = value;
    }
}
