package com.sleeplab.service;

import java.io.*;

public class GoalManager {

    private static final String FILE_PATH = "data/goal.txt";
    private int goalMinutes;      // 목표 수면 시간 (분)
    private String goalPeriod;    // 목표 기간 (일주일/한달)

    public GoalManager() {
        goalMinutes = 480;        // 기본값 8시간
        goalPeriod  = "일주일";
        load();
    }

    // 목표 저장
    public void save(int goalMinutes, String goalPeriod) {
        this.goalMinutes = goalMinutes;
        this.goalPeriod  = goalPeriod;
        new File("data").mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            bw.write(goalMinutes + "," + goalPeriod);
            bw.newLine();
        } catch (IOException e) {
            System.out.println(" 목표 저장 실패: " + e.getMessage());
        }
    }

    // 목표 불러오기
    private void load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = br.readLine();
            if (line != null && !line.isBlank()) {
                String[] parts = line.split(",");
                goalMinutes = Integer.parseInt(parts[0]);
                goalPeriod  = parts[1];
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(" 목표 불러오기 실패: " + e.getMessage());
        }
    }

    // 메달 계산
    public String getMedal(double avgMins) {
        double ratio = avgMins / goalMinutes;
        if (ratio >= 1.0)       return "GOLD";
        else if (ratio >= 0.875) return "SILVER";
        else if (ratio >= 0.75)  return "BRONZE";
        else                     return "NONE";
    }

    // 수면 빚 계산 (분)
    public int getDebtMinutes(int actualMins) {
        return Math.max(0, goalMinutes - actualMins);
    }

    public int getGoalMinutes()  { return goalMinutes; }
    public String getGoalPeriod(){ return goalPeriod; }
    public int getGoalHours()    { return goalMinutes / 60; }
    public int getGoalMins()     { return goalMinutes % 60; }
}