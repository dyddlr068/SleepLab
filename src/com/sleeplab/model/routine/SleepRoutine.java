package com.sleeplab.model.routine;

import com.sleeplab.interfaces.Prescribable;

public abstract class SleepRoutine implements Prescribable {

    private String typeName;
    private String description;
    private String recommendedBedTime;
    private String recommendedWakeTime;

    public SleepRoutine(String typeName, String description,
                        String recommendedBedTime, String recommendedWakeTime) {
        this.typeName = typeName;
        this.description = description;
        this.recommendedBedTime = recommendedBedTime;
        this.recommendedWakeTime = recommendedWakeTime;
    }

    public void printTypeInfo() {
        System.out.println("  판정: " + typeName);
        System.out.println("  특징: " + description);
        System.out.println("  권장 취침: " + recommendedBedTime
                         + " / 권장 기상: " + recommendedWakeTime);
    }

    public abstract String getCaffeineDeadline();
    public abstract int getNapAllowedMinutes();

    public String getTypeName() { return typeName; }
    public String getRecommendedBedTime() { return recommendedBedTime; }
    public String getRecommendedWakeTime() { return recommendedWakeTime; }
}