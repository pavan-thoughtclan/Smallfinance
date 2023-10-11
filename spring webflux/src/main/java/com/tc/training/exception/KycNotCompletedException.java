package com.tc.training.exception;

public class KycNotCompletedException extends RuntimeException {
    public KycNotCompletedException(String completeKyc) {
        super(completeKyc);
    }
}
