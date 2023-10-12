package com.microFinance1.exceptions;

public class KycNotCompletedException extends RuntimeException {
    public KycNotCompletedException(String completeKyc) {
        super(completeKyc);
    }
}
