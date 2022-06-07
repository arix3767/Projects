package com.company;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static com.company.Menu.DANIA;

public abstract class Zamowienie {

    public static final List<Zamowienie> LISTA_ZAMOWIEN = new ArrayList<>();
    private static int counter = 1;
    private int numer;
    private List<Danie> dania;
    private LocalTime czas;
    private boolean czyZrealizowane;


    public Zamowienie(List<Danie> dania) {
        this.numer = counter++;
        this.dania = dania;
        this.czas = Czas.getCzas();
        LISTA_ZAMOWIEN.add(this);
        this.czyZrealizowane = false;
    }

    public static void zlozZamowienie() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rozpoczynam zamowienie");
        boolean czyJeszcze;
        List<Danie> dania = new ArrayList<>();
        do {
            System.out.println("Czy chcesz zamówić coś jeszcze? ");
            czyJeszcze = scanner.nextBoolean();
            if (czyJeszcze) {
                System.out.println("Podaj nazwe zamowienia");
                String nazwaDania = new Scanner(System.in).nextLine();
                DANIA.entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().getNazwa().equals(nazwaDania))
                        .findAny()
                        .ifPresentOrElse(entry -> {
                            if (entry.getValue()) {
                                dania.add(entry.getKey());
                            } else {
                                System.out.println("Danie jest niedostepne");
                            }
                        }, () -> System.out.println("Nie ma takiego dania"));
            }
        } while (czyJeszcze);
        wybierzDostawe(dania);
    }

    public static void zlozZamowienieLosowe() {
        List<Danie> dania = new ArrayList<>();
        for (final int[] i = {0}; i[0] < (int) (Math.random() * DANIA.size()) + 1; i[0]++) {
            int losoweDanie = (int) (Math.random() * DANIA.size()) + 1;
            DANIA.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().getNumer() == losoweDanie)
                    .findAny()
                    .ifPresent(entry -> {
                        if (entry.getValue()) {
                            dania.add(entry.getKey());
                        } else {
                            i[0]--;
                        }
                    });
        }
        wybierzDostawe(dania);
    }

    private static void wybierzDostawe(List<Danie> dania) {
        System.out.println("Dostawa czy na wynos?");
        Scanner scanner1 = new Scanner(System.in);
        String wyborZamowienia = scanner1.nextLine();
        if (wyborZamowienia.equals("dostawa") || wyborZamowienia.equals("Dostawa")) {
            System.out.println("Podaj adres dostawy");
            String adres = scanner1.nextLine();
            new ZamowienieOnline(dania, adres);
        } else if (wyborZamowienia.equals("Na wynos") || wyborZamowienia.equals("na wynos")) {
            System.out.println("Wybierz stolik");
            int numerStolika = scanner1.nextInt();
            new ZamowienieStacjonarne(dania, numerStolika);
        }
    }

    public static void wypiszNiezrealizowaneZamowienia() {
        LISTA_ZAMOWIEN.stream()
                .filter(zamowienie -> !zamowienie.czyZrealizowane)
                .forEach(System.out::println);
    }

    public void zmienStatusZamowienia() {
        if (czyZrealizowane) {
            czyZrealizowane = false;
        } else {
            czyZrealizowane = true;
        }
    }

    public int getIloscDanWzamowniu() {
        return dania.size();
    }

    public boolean getCzyZrealizowane() {
        return czyZrealizowane;
    }

    public LocalTime getCzas() {
        return czas;
    }

    @Override
    public String toString() {
        return "Zamowienie{" +
                "numer=" + numer +
                ", dania=" + dania +
                ", czas=" + czas +
                ", czyZrealizowane=" + czyZrealizowane +
                '}';
    }
    public double zwrocCeneZamowienia(){
        double sumaZamowienia = 0;
        for (Danie d: dania) {
            sumaZamowienia += d.getCena();
        }
        return sumaZamowienia;
    }
}
