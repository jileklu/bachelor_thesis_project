package com.jileklu2.bakalarska_prace_app.exceptions.files;

/**
 *
 */

public class IllegalFilePathFormatException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public IllegalFilePathFormatException(String errorMessage) {
        super(errorMessage);
    }
}
