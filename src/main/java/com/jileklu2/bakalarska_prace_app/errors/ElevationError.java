package com.jileklu2.bakalarska_prace_app.errors;

public abstract class ElevationError implements Error{
    public int errorNumber;

    public ElevationError(Integer errorNumber) {
        this.errorNumber = errorNumber;
    }

    public ElevationError(ElevationError error) {
        this.errorNumber = error.errorNumber;
    }
}
