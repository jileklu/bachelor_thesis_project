package com.example.bakalarska_prace_app.errors;

public enum TomTomRoutingStatus {
    OK(200),
    ACCEPTED(202),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    REQUEST_TIMEOUT(408),
    URI_TOO_LONG(414),
    TOO_MANY_REQUESTS(429),
    PROCESSING_ERROR(500),
    CONNECTIVITY_ISSUES(502),
    SERVICE_UNAVAILABLE(503),
    INTERNAL_NETWORK_CONNECTION_ERROR(504),
    SERVICE_NOT_FOUND(596);

    public final int value;

    TomTomRoutingStatus(int value) {
        this.value = value;
    }
}
