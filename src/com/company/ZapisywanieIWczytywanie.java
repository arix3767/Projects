package com.company;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

import static com.company.Menu.DANIA;

public class ZapisywanieIWczytywanie {


    public void zapiszDoPliku() throws IOException {
        String sciezka = ".\\src\\com\\company\\Menu.txt";
        BufferedWriter pisz = new BufferedWriter(new FileWriter(sciezka));
        for (Map.Entry<Danie, Boolean> entry : DANIA.entrySet()) {
            pisz.write(entry.getKey() + " " + entry.getValue() + "\n");
        }
        pisz.close();
    }

    public void wczytajZPLiku() throws FileNotFoundException {
        File plik = new File(".\\src\\com\\company\\Menu.txt");
        Scanner scanner = new Scanner(plik);
        while (scanner.hasNextLine()) {
            String[] linijka = scanner.nextLine().split(" ");

            String nazwaDania = "";
            String opisDania = "";
            for (int i = 0; i < linijka.length; i++) {
                if (!linijka[i].equals("->")) {
                    nazwaDania += linijka[i] + " ";
                } else {
                    for (int j = i + 1; j < linijka.length; j++) {
                        if (!linijka[j].equals("cena:")) {
                            opisDania += linijka[j] + " ";
                        } else {
                            break;
                        }
                    }
                    break;
                }
            }

            Menu.dodajDanie(new Danie(nazwaDania.substring(0,nazwaDania.length()-1), opisDania.substring(0,nazwaDania.length()-1), Double.parseDouble(linijka[linijka.length - 2])), Boolean.parseBoolean(linijka[linijka.length - 1]));

        }
    }
}
