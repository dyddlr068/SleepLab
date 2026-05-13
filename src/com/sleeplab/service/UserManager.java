package com.sleeplab.service;

import java.io.*;

public class UserManager {

    private static final String FILE_PATH = "data/users.txt";
    private static final String DELIM     = ",";

    // 회원가입
    public boolean register(String userId, String password) {
        // 중복 아이디 확인
        if (findUser(userId) != null) return false;

        new File("data").mkdirs();
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(FILE_PATH, true))) {
            bw.write(userId + DELIM + password);
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println(" 회원가입 실패: " + e.getMessage());
            return false;
        }
    }

    // 로그인
    public boolean login(String userId, String password) {
        String[] user = findUser(userId);
        if (user == null) return false;
        return user[1].equals(password);
    }

    // 아이디로 사용자 찾기
    private String[] findUser(String userId) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(
                new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(DELIM);
                if (parts.length >= 2 && parts[0].equals(userId)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            System.out.println(" 사용자 조회 실패: " + e.getMessage());
        }
        return null;
    }

    // 아이디 존재 여부
    public boolean exists(String userId) {
        return findUser(userId) != null;
    }
}