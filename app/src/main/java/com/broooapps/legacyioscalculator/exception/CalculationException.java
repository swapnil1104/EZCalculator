package com.broooapps.legacyioscalculator.exception;

public class CalculationException extends Throwable {
    String err;
    String desc;
    public CalculationException(String err, String description) {
        this.err = err;
        this.desc = description;
    }

    public String getErr() {
        return err;
    }

    public String getDesc() {
        return desc;
    }
}
