package com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders;

/**
 *
 */
public class EmptyOriginsListException extends Exception{
    /**
     *
     * @param errorMessage error message
     */
    public EmptyOriginsListException(String errorMessage) {
        super(errorMessage);
    }
}
