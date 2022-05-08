package com.jileklu2.bakalarska_prace_app.errors;

public enum HereResponseStatus {
    OK(200),
    CREATED(201),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404);

    public final int value;

    HereResponseStatus(int value) {
        this.value = value;
    }
}
