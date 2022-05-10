package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

public class RequestDeniedException extends Exception {
    public RequestDeniedException(String errorMessage) {
        super(errorMessage);
    }
}
