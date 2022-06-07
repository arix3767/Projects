package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.company.Dostawca.dostawcy;
import static com.company.Kelner.kelnerzy;
import static com.company.Zamowienie.LISTA_ZAMOWIEN;

public class Kuchnia {

    private static double dziennyUtarg = 0;
    private static final List<Zamowienie> LISTA_ZREALIZOWANYCH_ZAMOWIEN = new ArrayList<>();
    private static final List<Zamowienie> LISTA_DO_ZAWIEZIENIA = Collections.synchronizedList(new ArrayList<>());

    private boolean czyOpoznione;

    private static boolean isRunning;

    public void rozpocznijGotowanie() {
        isRunning = true;
        System.out.println("rozpocznijGotowanie");

        Thread gotuj = new Thread(() -> {
            System.out.println("KUCHNIA DZIALA");
            while (isRunning) {
                if (Kucharz.kucharze.size() == 0 || kelnerzy.size() == 0) {
                    System.out.println("Nie można wystartowac kuchni, brak kucharzy lub kelnerow ");
                    isRunning = false;
                } else {
                    List<Zamowienie> doUsuniecia = new ArrayList<>();
                    LISTA_ZAMOWIEN.stream()
                            .filter(zamowienie -> !zamowienie.getCzyZrealizowane())
                            .sorted((z1, z2) -> z2.getCzas().compareTo(z1.getCzas()))
                            .filter(zamowienie -> zamowienie.getClass().getSimpleName().equals("ZamowienieStacjonarne"))
                            .findAny()
                            .ifPresentOrElse(zamowienie -> {
                                ugotuj(zamowienie);
                                doUsuniecia.add(zamowienie);
                            }, () -> {
                                LISTA_ZAMOWIEN.stream()
                                        .filter(zamowienie -> !zamowienie.getCzyZrealizowane())
                                        .sorted((z1, z2) -> z2.getCzas().compareTo(z1.getCzas()))
                                        .filter(zamowienie -> zamowienie.getClass().getSimpleName().equals("ZamowienieOnline"))
                                        .findFirst()
                                        .ifPresent(zamowienie -> {
                                            ugotuj(zamowienie);
                                            doUsuniecia.add(zamowienie);
                                        });
                            });
                    for (Zamowienie z : doUsuniecia) {
                        LISTA_ZAMOWIEN.remove(z);
                    }
                }
            }
        });

        Thread zawiez = new Thread(() -> {
            while (isRunning) {
                if (LISTA_DO_ZAWIEZIENIA.size() != 0) {
                    int wylosowanyDostawca = (int) (Math.random() * dostawcy.size());
                    if (dostawcy.get(wylosowanyDostawca).czyDostepny()) {
                        if (czyOpoznione) {
                            dostawcy.get(wylosowanyDostawca).zawiez(LISTA_DO_ZAWIEZIENIA.get(0).zwrocCeneZamowienia() * 0.01);
                        } else {
                            Dostawca dostawca = dostawcy.get(wylosowanyDostawca);
                            dostawca.zawiez(LISTA_DO_ZAWIEZIENIA.get(0).zwrocCeneZamowienia() * 0.1);
                        }
                        LISTA_DO_ZAWIEZIENIA.remove(0);
                    }
                }
            }
        });

        System.out.println("Startuje thready");
        gotuj.start();
        zawiez.start();
    }

    private void ugotuj(Zamowienie zamowienie) {
        System.out.println("rozpoczynam gotowanie");
        if (Czas.getCzas().isAfter(zamowienie.getCzas().plusMinutes(15))) {
            if (Math.random() > 0.5) {
                czyOpoznione = true;
                poczekaj(zamowienie);
                dziennyUtarg += zamowienie.zwrocCeneZamowienia() * 0.8;
            }
        } else {
            czyOpoznione = false;
            poczekaj(zamowienie);
            dziennyUtarg += zamowienie.zwrocCeneZamowienia();
        }
        System.out.println("danie zostalo ugotowane");
    }

    private void poczekaj(Zamowienie zamowienie) {
        try {
            Thread.sleep((5_000L * zamowienie.getIloscDanWzamowniu()) / Kucharz.kucharze.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        zamowienie.zmienStatusZamowienia();
        LISTA_ZREALIZOWANYCH_ZAMOWIEN.add(zamowienie);
        if (zamowienie.getClass().getSimpleName().equals("ZamowienieStacjonarne")) {
            int wylosowanyKelner = (int) (Math.random() * kelnerzy.size());
            if (czyOpoznione) {
                kelnerzy.get(wylosowanyKelner).dodajZrealizowaneZamowienie(zamowienie.zwrocCeneZamowienia() * 0.01);
            } else {
                kelnerzy.get(wylosowanyKelner).dodajZrealizowaneZamowienie(zamowienie.zwrocCeneZamowienia() * 0.1);
            }
        } else {
            LISTA_DO_ZAWIEZIENIA.add(zamowienie);
            System.out.println(LISTA_DO_ZAWIEZIENIA.size());
        }
    }

    public static void zatrzymajKuchnie() {
        isRunning = false;
    }

    public static void wypiszZrealizowaneZamowienia(){
        for (Zamowienie zrealizowaneZamowienia : LISTA_ZREALIZOWANYCH_ZAMOWIEN) {
            System.out.println(zrealizowaneZamowienia);
        }
    }
    public static void wypiszDziennyUtarg(){
        System.out.println(dziennyUtarg);
    }
}
