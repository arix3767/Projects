package com.company;

import java.util.List;

public class ZamowienieOnline extends Zamowienie {

    private String adresDostawy;

    public ZamowienieOnline(List<Danie> dania,String adresDostawy) {
        super(dania);
        this.adresDostawy = adresDostawy;
    }

    public String getAdresDostawy() {
        return adresDostawy;
    }
}
