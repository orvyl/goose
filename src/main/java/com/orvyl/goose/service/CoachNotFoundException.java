package com.orvyl.goose.service;

public class CoachNotFoundException extends Exception {
    public CoachNotFoundException() {
        super();
    }

    public CoachNotFoundException(String message) {
        super(message);
    }

    public CoachNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoachNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CoachNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
