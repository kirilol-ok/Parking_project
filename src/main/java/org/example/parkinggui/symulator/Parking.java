package org.example.parkinggui.symulator;

import java.util.ArrayList;

public class Parking {

    private final int iloscRzedow = 3;
    private final int iloscMiejscWRzedzie = 5;
    private final Object[][] parkingData;

    public Parking() {
        parkingData = new Object[iloscRzedow][iloscMiejscWRzedzie];
        for (int i = 0; i < iloscRzedow; i++) {
            for (int j = 0; j < iloscMiejscWRzedzie; j++) {
                parkingData[i][j] = null; // wolne miejsce
            }
        }
    }

    public int[] zajmijMiejsce(String nrRejestracyjny, int czasParkowania) {
        for (int i = 0; i < iloscRzedow; i++) {
            for (int j = 0; j < iloscMiejscWRzedzie; j++) {
                if (parkingData[i][j] == null) { // znaleziono wolne miejsce
                    parkingData[i][j] = new Object[]{nrRejestracyjny, czasParkowania};
                    return new int[]{i + 1, j + 1}; // zwraca rząd i miejsce
                }
            }
        }
        return null; // brak wolnych miejsc
    }


    public ArrayList<Samochod> getZajeteMiejsca() {
        ArrayList<Samochod> zajete = new ArrayList<>();
        for (int i = 0; i < iloscRzedow; i++) {
            for (int j = 0; j < iloscMiejscWRzedzie; j++) {
                if (parkingData[i][j] != null) {
                    zajete.add((Samochod) parkingData[i][j]);
                }
            }
        }
        return zajete;
    }


    public ArrayList<Samochod> getWolneMiejsca() {
        ArrayList<Samochod> wolne = new ArrayList<>();
        for (int i = 0; i < iloscRzedow; i++) {
            for (int j = 0; j < iloscMiejscWRzedzie; j++) {
                if (parkingData[i][j] == null) {
                    wolne.add(new Samochod(i + 1, j + 1, "", 0)); // Puste miejsce
                }
            }
        }
        return wolne;
    }
}
