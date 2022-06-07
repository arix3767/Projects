package com.company;

import java.util.ArrayList;
import java.util.List;

public class Dostawca extends Osoba {
    public static List<Dostawca> dostawcy = new ArrayList<>();
    private int iloscZrealizowanychZamowien;
    private double napiwek;

    private boolean czyDostepny = true;

    public Dostawca(String imie, String nazwisko, int nrTelefonu) {
        super(imie, nazwisko, nrTelefonu);
        dostawcy.add(this);
        this.iloscZrealizowanychZamowien = 0;
        this.napiwek = 0;
    }

    public void zawiez(double napiwek) {
        Thread jedz = new Thread(() -> {
            czyDostepny = false;
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("zawiozlem");
            iloscZrealizowanychZamowien++;
            this.napiwek += napiwek;
            czyDostepny = true;
        });

        jedz.start();
    }

    public boolean czyDostepny() {
        return czyDostepny;
    }

    @Override
    public String toString() {
        return "Dostawca{" +
                ", iloscZrealizowanychZamowien=" + iloscZrealizowanychZamowien +
                ", napiwek=" + napiwek +
                '}' + super.toString();
    }
}
