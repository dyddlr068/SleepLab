package com.sleeplab.exception;

public class InvalidSleepTimeException extends Exception {

    public InvalidSleepTimeException(String message) {
        super(message);
    }

    public InvalidSleepTimeException(String message, Exception cause) {
        super(message, cause);
    }
}