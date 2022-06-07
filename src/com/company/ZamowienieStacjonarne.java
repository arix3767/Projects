package com.company;

import java.util.List;

public class ZamowienieStacjonarne extends Zamowienie {

    int numerStolika;

    public ZamowienieStacjonarne(List<Danie> dania, int numerStolika) {
        super(dania);
        this.numerStolika = numerStolika;
    }

    public int getNumerStolika() {
        return numerStolika;
    }
}
