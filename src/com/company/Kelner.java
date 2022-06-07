package com.company;

import java.util.ArrayList;
import java.util.List;

public class Kelner extends Osoba {
    public static List<Kelner> kelnerzy = new ArrayList<>();

    private double napiwek;
    private int iloscZrealizowanychZamowien;

    public Kelner(String imie, String nazwisko, int nrTelefonu) {
        super(imie, nazwisko, nrTelefonu);
        kelnerzy.add(this);
        this.iloscZrealizowanychZamowien = 0;
        this.napiwek = 0;
    }

    @Override
    public String toString() {
        return "Kelner" +
                ", napiwek=" + napiwek +
                ", iloscZrealizowanychZamowien=" + iloscZrealizowanychZamowien +
                '}' + super.toString();
    }

    public void dodajZrealizowaneZamowienie(double napiwek) {
        iloscZrealizowanychZamowien++;
        this.napiwek += napiwek;
    }
}
