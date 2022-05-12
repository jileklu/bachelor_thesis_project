package com.jileklu2.bakalarska_prace_app.handlers.responseStatus.enums;

/**
 *
 */
public enum HereOptimizationStatus {
    OK(200),
    CREATED(201),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404);

    public final int value;

    HereOptimizationStatus(int value) {
        this.value = value;
    }
}
