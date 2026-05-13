package com.sleeplab.model.routine;

public class EveningType extends SleepRoutine {

    public EveningType() {
        super(
            "저녁형 (Evening Type)",
            "야간 각성, 오전 기상 어려움, 주말 기상 편차 큼",
            "23:30",
            "08:00"
        );
    }

    @Override
    public String getCaffeineDeadline() { 
        return "14:00"; 
    }

    @Override
    public int getNapAllowedMinutes() { 
        return 20; 
    }

    @Override
    public void prescribeMorningRoutine() {
        System.out.println("  08:00   기상 알람 (권장)");
        System.out.println("  08:10   햇빛 10분 쬐기 → 세로토닌 촉진");
        System.out.println("  08:30   단백질 위주 아침 식사");
    }

    @Override
    public void prescribeAfternoonRoutine() {
        System.out.println("  14:00   카페인 차단 시각");
        System.out.println("  15:00   낮잠 허용 (20분 이내)");
    }

    @Override
    public void prescribeNightRoutine() {
        System.out.println("  22:00   블루라이트 차단 (야간모드 ON)");
        System.out.println("  22:30   조명 40% 이하로 낮추기");
        System.out.println("  23:30   취침 준비 및 입면");
    }

    @Override
    public void savePrescription(String prescription) {
        System.out.println(">>> 저녁형 맞춤 루틴 처방이 저장되었습니다.");
    }
}