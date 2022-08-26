package com.rest.api.expensetrackerapi.error;

public class ResoureNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResoureNotFoundException() {
        super();
    }

    public ResoureNotFoundException(String message) {
        super(message);
    }

    public ResoureNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResoureNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ResoureNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
