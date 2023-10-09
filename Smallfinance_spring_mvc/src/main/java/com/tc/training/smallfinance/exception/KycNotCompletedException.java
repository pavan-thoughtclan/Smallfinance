package com.tc.training.smallfinance.exception;

public class KycNotCompletedException extends RuntimeException {
    public KycNotCompletedException(String completeKyc) {
        super(completeKyc);
    }
}
