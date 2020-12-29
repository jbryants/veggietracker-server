package com.example.veggietracker.exceptions;

public class VeggieTrackerException extends RuntimeException{
    public VeggieTrackerException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public VeggieTrackerException(String exMessage) {
        super(exMessage);
    }
}
