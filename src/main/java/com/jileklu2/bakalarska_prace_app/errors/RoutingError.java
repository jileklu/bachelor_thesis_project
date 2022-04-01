package com.jileklu2.bakalarska_prace_app.errors;

public abstract class RoutingError implements Error{
    public int errorNumber;

    public RoutingError(Integer errorNumber) {
        this.errorNumber = errorNumber;
    }

    public RoutingError(RoutingError error) {
        this.errorNumber = error.errorNumber;
    }
}
