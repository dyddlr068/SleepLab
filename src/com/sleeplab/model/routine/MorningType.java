package com.sleeplab.model.routine;

public class MorningType extends SleepRoutine {

    public MorningType() {
        super(
            "아침형 (Morning Type)",
            "이른 취침·기상, 오전 집중력 높음",
            "22:30",
            "06:30"
        );
    }

    @Override
    public String getCaffeineDeadline() { return "13:00"; }

    @Override
    public int getNapAllowedMinutes() { return 0; }

    @Override
    public void prescribeMorningRoutine() {
        System.out.println("  06:30   기상 알람 (권장)");
        System.out.println("  06:40   스트레칭 10분");
        System.out.println("  07:00   단백질 위주 아침 식사");
    }

    @Override
    public void prescribeAfternoonRoutine() {
        System.out.println("  13:00   카페인 차단 시각");
        System.out.println("  14:00   집중 업무 마무리 권장");
    }

    @Override
    public void prescribeNightRoutine() {
        System.out.println("  21:00   블루라이트 차단");
        System.out.println("  21:30   조명 낮추기");
        System.out.println("  22:30   권장 취침 시각");
    }

    @Override
    public void savePrescription(String date) {
        System.out.println("  morning_routine_" + date + ".txt 저장 완료!");
    }
}