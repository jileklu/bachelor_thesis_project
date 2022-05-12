package com.jileklu2.bakalarska_prace_app.exceptions.dateSelection;

/**
 *
 */
public class DayNotSelectedException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public DayNotSelectedException(String errorMessage) {
        super(errorMessage);
    }
}
