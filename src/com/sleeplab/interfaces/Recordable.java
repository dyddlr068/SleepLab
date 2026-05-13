package com.sleeplab.interfaces;

import com.sleeplab.model.SleepRecord;
import java.util.List;

public interface Recordable {

    void save(SleepRecord record);

    List<SleepRecord> loadAll();

    SleepRecord findByDate(String date);
}