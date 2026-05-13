package com.sleeplab.service;

import com.sleeplab.exception.InsufficientDataException;
import com.sleeplab.interfaces.Analyzable;
import com.sleeplab.model.SleepRecord;
import com.sleeplab.model.routine.EveningType;
import com.sleeplab.model.routine.IrregularType;
import com.sleeplab.model.routine.MorningType;
import com.sleeplab.model.routine.SleepRoutine;
import java.util.List;

public class SleepAnalyzer implements Analyzable {

    private static final int MIN_RECORDS = 3;

    @Override
    public SleepRoutine analyze(List<SleepRecord> records)
            throws InsufficientDataException {

        if (records.size() < MIN_RECORDS) {
            throw new InsufficientDataException(records.size(), MIN_RECORDS);
        }

        double avgBed = records.stream()
            .mapToInt(r -> toMinutes(r.getBedTime()))
            .average().orElse(0);

        double variance = records.stream()
            .mapToDouble(r -> Math.abs(toMinutes(r.getBedTime()) - avgBed))
            .average().orElse(0);

        if (variance >= 60) return new IrregularType();
        if (avgBed >= 23 * 60) return new EveningType();
        return new MorningType();
    }

    @Override
    public String getAverageBedTime(List<SleepRecord> records) {
        double avg = records.stream()
            .mapToInt(r -> toMinutes(r.getBedTime()))
            .average().orElse(0);
        return fromMinutes((int) avg);
    }

    @Override
    public String getAverageWakeTime(List<SleepRecord> records) {
        double avg = records.stream()
            .mapToInt(r -> toMinutes(r.getWakeTime()))
            .average().orElse(0);
        return fromMinutes((int) avg);
    }

    private int toMinutes(String time) {
        String[] p = time.split(":");
        int m = Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
        return m < 12 * 60 ? m + 24 * 60 : m;
    }

    private String fromMinutes(int minutes) {
        minutes = minutes % (24 * 60);
        return String.format("%02d:%02d", minutes / 60, minutes % 60);
    }
}