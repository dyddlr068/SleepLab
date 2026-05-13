package com.sleeplab.interfaces;

import com.sleeplab.exception.InsufficientDataException;
import com.sleeplab.model.SleepRecord;
import com.sleeplab.model.routine.SleepRoutine;
import java.util.List;

public interface Analyzable {

    SleepRoutine analyze(List<SleepRecord> records) throws InsufficientDataException;

    String getAverageBedTime(List<SleepRecord> records);

    String getAverageWakeTime(List<SleepRecord> records);
}