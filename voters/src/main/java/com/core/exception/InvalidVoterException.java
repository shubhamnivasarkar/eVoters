package com.core.exception;

public class InvalidVoterException extends Exception {
    private static final long serialVersionUID = 41L;

    public InvalidVoterException (String message) {
        super(message);
    }
}
