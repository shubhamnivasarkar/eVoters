package com.core.exception;

public class InvalidCandidateException extends Exception {
    private static final long serialVersionUID = 37L;

    public InvalidCandidateException (String message) {
        super(message);
    }
}
