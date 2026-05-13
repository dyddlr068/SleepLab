package com.sleeplab.exception;

public class OutOfRangeQualityException extends Exception {

    private int inputValue;

    public OutOfRangeQualityException(int inputValue) {
        super("수면 품질은 1~5 사이의 숫자만 입력 가능합니다. 입력값: " + inputValue);
        this.inputValue = inputValue;
    }

    public int getInputValue() { return inputValue; }
}