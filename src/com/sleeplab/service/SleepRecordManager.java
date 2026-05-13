package com.sleeplab.service;

import com.sleeplab.interfaces.Recordable;
import com.sleeplab.model.SleepRecord;
import java.io.*;
import java.util.*;

public class SleepRecordManager implements Recordable {

    private static final String FILE_PATH = "data/sleep_records.txt";
    private List<SleepRecord> records = new ArrayList<>();

    public SleepRecordManager() {
        records = loadAll();
    }

    @Override
    public void save(SleepRecord record) {
        records.add(record);
        new File("data").mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(record.toLine());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("   파일 저장 실패: " + e.getMessage());
        }
    }

    @Override
    public List<SleepRecord> loadAll() {
        List<SleepRecord> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    list.add(SleepRecord.fromLine(line));
                }
            }
        } catch (IOException e) {
            System.out.println("   파일 불러오기 실패: " + e.getMessage());
        }
        return list;
    }

    @Override
    public SleepRecord findByDate(String date) {
        return records.stream()
            .filter(r -> r.getDate().equals(date))
            .findFirst()
            .orElse(null);
    }

    // 특정 날짜 기록 삭제
    public boolean delete(String date) {
        boolean removed = records.removeIf(r -> r.getDate().equals(date));
        if (removed) saveAll();
        return removed;
    }

    // 중복 날짜 확인
    public boolean isDuplicate(String date) {
        return records.stream()
            .anyMatch(r -> r.getDate().equals(date));
    }

    // 전체 파일 덮어쓰기
    private void saveAll() {
        new File("data").mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (SleepRecord r : records) {
                bw.write(r.toLine());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("   파일 저장 실패: " + e.getMessage());
        }
    }

    
    public List<SleepRecord> getRecords() { return records; }

 // 여기 아래에 추가
 public boolean update(String date, SleepRecord newRecord) {
     for (int i = 0; i < records.size(); i++) {
         if (records.get(i).getDate().equals(date)) {
             records.set(i, newRecord);
             saveAll();
             return true;
         }
     }
     return false;
 }
}