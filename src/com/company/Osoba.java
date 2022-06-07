package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static com.company.Dostawca.dostawcy;
import static com.company.Kelner.kelnerzy;
import static com.company.Kucharz.kucharze;

public abstract class Osoba {
    private final String imie;
    private final String nazwisko;
    private final int nrTelefonu;
    private static final List<Osoba> LISTA_PRACOWNIKOW = new ArrayList<>();

    public Osoba(String imie, String nazwisko, int nrTelefonu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrTelefonu = nrTelefonu;
        LISTA_PRACOWNIKOW.add(this);
    }

    public static void zatrudnijOsobe() {
        System.out.println("Podaj swoje dane: ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("imię: ");
        String imie = scanner.nextLine();
        System.out.println("nazwisko: ");
        String nazwisko = scanner.nextLine();
        System.out.println("numer telefonu: ");
        int nrTelefonu = scanner.nextInt();
        System.out.println("Podaj stanowisko na jakim chcesz pracować: ");
        Scanner scanner1 = new Scanner(System.in);
        String stanowisko = scanner1.nextLine().toLowerCase(Locale.ROOT);
        switch (stanowisko) {
            case "kucharz" -> new Kucharz(imie, nazwisko, nrTelefonu);

            case "dostawca" -> new Dostawca(imie, nazwisko, nrTelefonu);

            case "kelner" -> new Kelner(imie, nazwisko, nrTelefonu);

            default -> System.out.println("Nie ma takiego stanowiska");
        }
    }
    public static void wyrzucPracownika() throws BrakOsobException {
        System.out.println("Podaj numer telefonu osoby którą chcesz wyrzucić");
        Scanner scanner = new Scanner(System.in);
        int numer = scanner.nextInt();

        List<Osoba> listaPracownikowDoUsuniecia = new ArrayList<>();

        LISTA_PRACOWNIKOW.stream()
                .filter(osoba -> osoba.getNrTelefonu() == numer)
                .findAny()
                .ifPresentOrElse(listaPracownikowDoUsuniecia::add, () -> System.out.println("Nie ma pracownika o takim numerze telefonu"));

        for (Osoba osoba : listaPracownikowDoUsuniecia) {
            LISTA_PRACOWNIKOW.remove(osoba);

            if (osoba.getClass().getSimpleName().equals("Kelner")) {
                kelnerzy.remove(osoba);
            }
            if (osoba.getClass().getSimpleName().equals("Kucharz")) {
                kucharze.remove(osoba);
            }
            if (osoba.getClass().getSimpleName().equals("Dostawca")) {
                dostawcy.remove(osoba);
            }
        }
        if (kelnerzy.size() == 0) {
            throw new BrakOsobException("Brak osob");
        }
        if (kucharze.size() == 0) {
            throw new BrakOsobException("Brak osob");
        }
        if (dostawcy.size() == 0) {
            throw new BrakOsobException("Brak osob");
        }


    }
    public static void wypiszInformacjePracownik(){
        System.out.println("Podaj numer telefonu pracownika ktorego chcesz wyswietlic");
        Scanner scanner = new Scanner(System.in);
        int numer = scanner.nextInt();
        LISTA_PRACOWNIKOW.stream()
                .filter(osoba -> osoba.getNrTelefonu() == numer)
                .findAny()
                .ifPresentOrElse(System.out::println,() -> System.out.println("Nie ma pracownika o takim numerze telefonu"));

    }
    public static void wypiszInformacjeWszyscyPracownicy(){
        System.out.println(WypisywanieListy.wypiszListe(LISTA_PRACOWNIKOW));
    }

    public int getNrTelefonu() {
        return nrTelefonu;
    }

    @Override
    public String toString() {
        return "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", nrTelefonu=" + nrTelefonu;
    }

}
