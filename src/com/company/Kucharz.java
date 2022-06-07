package com.company;

import java.util.ArrayList;
import java.util.List;

public class Kucharz extends Osoba {
    public static List<Kucharz> kucharze = new ArrayList<>();

    public Kucharz(String imie, String nazwisko, int nrTelefonu) {
        super(imie, nazwisko, nrTelefonu);
        kucharze.add(this);
    }
    @Override
    public String toString() {
        return "Kucharz{" +
                 super.toString() + '}';
    }
}
