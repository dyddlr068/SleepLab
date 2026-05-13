package com.sleeplab.interfaces;

public interface Prescribable {

    void prescribeMorningRoutine();

    void prescribeAfternoonRoutine();

    void prescribeNightRoutine();

    void savePrescription(String date);
}