package com.tc.training.smallfinance.exception;

public class AmountNotSufficientException extends RuntimeException{

    public AmountNotSufficientException(String message){

        super(message);
    }
}
