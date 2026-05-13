package com.sleeplab.exception;

public class InsufficientDataException extends Exception {

    private int currentCount;
    private int requiredCount;

    public InsufficientDataException(int currentCount, int requiredCount) {
        super("분석에 필요한 기록이 부족합니다. 현재: " + currentCount + "일, 필요: " + requiredCount + "일 이상");
        this.currentCount = currentCount;
        this.requiredCount = requiredCount;
    }

    public int getCurrentCount() { return currentCount; }
    public int getRequiredCount() { return requiredCount; }
}