package com.jileklu2.bakalarska_prace_app.exceptions.strings;

/**
 *
 */
public class BlankStringException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public BlankStringException(String errorMessage) {
        super(errorMessage);
    }
}
