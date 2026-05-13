package com.sleeplab.model.routine;

public class IrregularType extends SleepRoutine {

    public IrregularType() {
        super(
            "불규칙형 (Irregular Type)",
            "취침·기상 편차 1시간 이상, 수면 리듬 미확립",
            "23:00",
            "07:30"
        );
    }

    @Override
    public String getCaffeineDeadline() { return "13:30"; }

    @Override
    public int getNapAllowedMinutes() { return 30; }

    @Override
    public void prescribeMorningRoutine() {
        System.out.println("  07:30   고정 기상 알람 (주말도 동일하게 유지!)");
        System.out.println("  07:40   햇빛 15분 쬐기 — 리듬 재설정 핵심");
        System.out.println("  08:00   아침 식사 — 매일 같은 시간 권장");
    }

    @Override
    public void prescribeAfternoonRoutine() {
        System.out.println("  13:30   카페인 차단 시각");
        System.out.println("  14:30   낮잠 허용 (30분, 이후 졸음 없앰)");
        System.out.println("  16:00   가벼운 야외 운동 권장");
    }

    @Override
    public void prescribeNightRoutine() {
        System.out.println("  21:30   블루라이트 차단");
        System.out.println("  22:00   취침 준비 루틴 시작 (독서, 스트레칭)");
        System.out.println("  23:00   권장 취침 시각 — 매일 동일 시각 유지!");
    }

    @Override
    public void savePrescription(String date) {
        System.out.println("  irregular_routine_" + date + ".txt 저장 완료!");
    }
}