package com.rest.api.expensetrackerapi.error;

public class ItemAlreadyExitException extends RuntimeException{

    private static final  long serialVersionUID = 1L;

    public ItemAlreadyExitException(String message) {

        super(message);
    }
}
