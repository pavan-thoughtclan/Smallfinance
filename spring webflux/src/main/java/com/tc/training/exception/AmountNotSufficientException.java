package com.tc.training.exception;

public class AmountNotSufficientException extends RuntimeException{

    public AmountNotSufficientException(String message){

        super(message);
    }
}
