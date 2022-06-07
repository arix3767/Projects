package com.company;

import java.time.LocalTime;

public class Czas extends Thread {

    private static LocalTime czas = LocalTime.now();

    @Override
    public void run() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        czas = czas.plusMinutes(1);
    }

    public static LocalTime getCzas() {
        return czas;
    }
}
