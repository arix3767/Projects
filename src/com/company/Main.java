package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static com.company.Danie.*;
import static com.company.Menu.*;
import static com.company.Osoba.zatrudnijOsobe;


public class Main {

    public static void main(String[] args) {

        try {
            new ZapisywanieIWczytywanie().wczytajZPLiku();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        new Kucharz("Mateusz", "Majchak", 123);
        new Kucharz("Maciek", "Kruszewski", 890);
        new Dostawca("Mateusz", "Dobraj", 456);
        new Kelner("Jan","Kowalski",678);




//        for (int i = 0; i < 10; i++) {
//            Zamowienie.zlozZamowienieLosowe();
//        }

        new Czas().start();
        Kuchnia kuchnia = new Kuchnia();

        System.out.println("");
        int liczba;
        do {
            System.out.println("Naciśnij 1, aby wypisać wszystkie pozycje w Menu");
            System.out.println("Naciśnij 2, aby stworzyć danie");
            System.out.println("Naciśnij 3, aby dodać pozycję do Menu");
            System.out.println("Naciśnij 4, aby usunąć pozycję z Menu");
            System.out.println("Naciśnij 5, aby zmienić status dania");
            System.out.println("Naciśnij 6, aby zamówić danie");
            System.out.println("Naciśnij 7, aby zlozyc zamowienie losowe ");
            System.out.println("Naciśnij 8, aby wypisać niezrealizowane zamowienia ");
            System.out.println("Naciśnij 9, aby rozpoczac prace w kuchni");
            System.out.println("Naciśnij 10, aby zatrudnić nową osobę");
            System.out.println("Naciśnij 11, aby wypisać zamowienia, które zostały zrealizowane w odpowiedniej kolejności ");
            System.out.println("Naciśnij 12, aby wyświetlić dzienny utarg");
            System.out.println("Naciśnij 13, aby wyrzucić pracownika");
            System.out.println("Naciśnij 14, aby wypisać informacje o pracowniku");
            System.out.println("Naciśnij 15, aby wypisać informacje o wszystkich pracownikach");
            System.out.println("Naciśnij 16, aby zatrzymać kuchnię ");
            System.out.println("Naciśnij 17, aby wyjść z aplikacji");


            liczba = new Scanner(System.in).nextInt();
            switch (liczba) {
                case 1 -> wypiszPozycje();
                case 2 -> stworzDanie();
                case 3 -> {
                    wyswietlStworzoneDania();
                    System.out.println("Jakie danie chcesz dodać do Menu");
                    String nazwa = new Scanner(System.in).nextLine();
                    WSZYSTKIE_DANIA.stream()
                            .filter(danie -> danie.getNazwa().equals(nazwa))
                            .findAny()
                            .ifPresentOrElse(Menu::dodajDanie, () -> System.out.println("Nie ma takiego dania"));
                }
                case 4 -> {
                    wypiszPozycje();
                    System.out.println("Jakie danie chcesz usunąć z Menu");
                    String nazwa = new Scanner(System.in).nextLine();
                    usunDanie(nazwa);
                }
                case 5 -> zmienStatus();

                case 6 -> Zamowienie.zlozZamowienie();

                case 7 -> Zamowienie.zlozZamowienieLosowe();

                case 8 -> Zamowienie.wypiszNiezrealizowaneZamowienia();

                case 9 -> kuchnia.rozpocznijGotowanie();

                case 10 -> zatrudnijOsobe();

                case 11 -> Kuchnia.wypiszZrealizowaneZamowienia();

                case 12 -> Kuchnia.wypiszDziennyUtarg();

                case 13 -> {
                    try {
                        Osoba.wyrzucPracownika();
                    } catch (BrakOsobException e) {
                        System.out.println(e);
                    }
                }

                case 14 -> Osoba.wypiszInformacjePracownik();

                case 15 -> Osoba.wypiszInformacjeWszyscyPracownicy();

                case 16 -> Kuchnia.zatrzymajKuchnie();

                case 17 -> System.out.println("Kończę aplikacje");

                default -> System.out.println("Nie ma takiego wyboru");
            }
        } while (liczba != 17);
        try {
            new ZapisywanieIWczytywanie().zapiszDoPliku();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
