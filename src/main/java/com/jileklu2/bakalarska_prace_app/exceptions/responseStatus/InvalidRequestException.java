package com.jileklu2.bakalarska_prace_app.exceptions.responseStatus;

public class InvalidRequestException extends Exception {
    public InvalidRequestException(String errorMessage) {
        super(errorMessage);
    }
}
