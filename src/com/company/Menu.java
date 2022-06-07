package com.company;

import java.util.*;

import static com.company.Danie.zamienNumer;

public class Menu {
    public static final HashMap<Danie, Boolean> DANIA = new HashMap<>();

    public static void dodajDanie(Danie danie) {
        DANIA.keySet()
                .stream()
                .filter(danie1 -> danie1.getNazwa().equals(danie.getNazwa()))
                .findAny()
                .ifPresentOrElse(danie1 -> System.out.println("Danie " + danie1.getNazwa() + " juz istnieje"), () -> {
                    danie.setNumer(danie.getNumer() * (-1));
                    DANIA.put(danie, true);
                });
    }

    public static void dodajDanie(Danie danie, boolean bool) {
        DANIA.put(danie, bool);
    }

    public static void usunDanie(String nazwaDania) {
        List<Danie> doUsuniecia = new ArrayList<>();
        DANIA.keySet()
                .stream()
                .filter(danie -> danie.getNazwa().equals(nazwaDania))
                .findAny()
                .ifPresentOrElse(danie -> {
                    zamienNumer(danie.getNumer());
                    doUsuniecia.add(danie);
                }, () -> System.out.println(""));

        for (Danie d : doUsuniecia) {
            DANIA.remove(d);
        }
    }

    public static void wypiszPozycje() {

        for (Map.Entry<Danie, Boolean> entry : DANIA.entrySet()) {
            if (entry.getValue()) {
                System.out.println(entry.getKey() + " Jest dostępne");
            } else {
                System.out.println(entry.getKey() + " Danie nie jest dostępne");
            }
        }
    }
    public static void zmienStatus(){
        System.out.println("Dla jakiego dania chcesz zmienić status? ");
        Scanner scanner = new Scanner(System.in);
        String danie = scanner.nextLine();
        DANIA.entrySet().stream()
                .filter(entry -> entry.getKey().getNazwa().equals(danie))
                .findAny()
                .ifPresentOrElse(entry -> {
                    if (entry.getValue()) {
                        DANIA.put(entry.getKey(), false);
                    } else {
                        DANIA.put(entry.getKey(), true);
                    }
                },() -> System.out.println("Nie ma takiego dania w menu"));

    }

}
