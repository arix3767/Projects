package com.company;

import java.util.List;

public interface WypisywanieListy {
    static <T> String wypiszListe(List<T> lista) {
        StringBuilder doZwrocenia = new StringBuilder();
        for (T t: lista) {
            doZwrocenia.append(t).append("\n");
        }
        return doZwrocenia.toString();
    }
}