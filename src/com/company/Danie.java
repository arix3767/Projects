package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Danie {

    private static int counter = 1;
    private int numer;
    private String nazwa;
    private String opis;
    private double cena;
    public static final List<Danie> WSZYSTKIE_DANIA = new ArrayList<>();

    public Danie(String nazwa, String opis, double cena) {
        this.numer = counter++;
        this.nazwa = nazwa;
        this.opis = opis;
        this.cena = cena;
        WSZYSTKIE_DANIA.add(this);
    }

    public static void zamienNumer(int id) {
        WSZYSTKIE_DANIA.stream()
                .filter(danie -> danie.numer > id)
                .forEach(danie -> danie.setNumer(danie.getNumer() - 1));
        counter--;
    }

    public static void stworzDanie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę dania: ");
        String nazwa = scanner.nextLine();
        System.out.println("Podaj opis dania: ");
        String opis = scanner.nextLine();
        System.out.println("Podaj cenę: ");
        double cena = scanner.nextDouble();
        Danie danie = new Danie(nazwa, opis, cena);
        danie.setNumer(danie.getNumer() * (-1));
    }

    public static void wyswietlStworzoneDania() {
        WSZYSTKIE_DANIA.stream()
                .filter(danie -> danie.getNumer() < 0)
                .forEach(System.out::println);
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public double getCena() {
        return cena;
    }

    @Override
    public String toString() {
        return nazwa + " -> " + opis +
                " cena: " +cena;
    }
}

