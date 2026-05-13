package com.sleeplab.model;

public class SleepRecord {

    private String date;
    private String bedTime;
    private String wakeTime;
    private int sleepQuality;
    private String condition;

    public SleepRecord(String date, String bedTime, String wakeTime,
                       int sleepQuality, String condition) {
        this.date = date;
        this.bedTime = bedTime;
        this.wakeTime = wakeTime;
        this.sleepQuality = sleepQuality;
        this.condition = condition;
    }

    public int getSleepMinutes() {
        int bed = toMinutes(bedTime);
        int wake = toMinutes(wakeTime);
        if (bed > wake) wake += 24 * 60;
        return wake - bed;
    }

    private int toMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    public String toLine() {
        return date + "," + bedTime + "," + wakeTime
             + "," + sleepQuality + "," + condition;
    }

    public static SleepRecord fromLine(String line) {
        String[] parts = line.split(",");
        return new SleepRecord(parts[0], parts[1], parts[2],
                               Integer.parseInt(parts[3]), parts[4]);
    }

    public String getDate() { return date; }
    public String getBedTime() { return bedTime; }
    public String getWakeTime() { return wakeTime; }
    public int getSleepQuality() { return sleepQuality; }
    public String getCondition() { return condition; }

    @Override
    public String toString() {
        int mins = getSleepMinutes();
        String stars = "★".repeat(sleepQuality) + "☆".repeat(5 - sleepQuality);
        return String.format("  %s   %s ~ %s   %dh %02dm   %s   %s",
                date, bedTime, wakeTime,
                mins / 60, mins % 60, stars, condition);
    }
}