package org.example.parkinggui.symulator;

import java.util.ArrayList;

public class Parking {

    private final int iloscRzedow = 3;
    private final int iloscMiejscWRzedzie = 5;
    private final Samochod[][] parkingData;

    public Parking() {
        parkingData = new Samochod[iloscRzedow][iloscMiejscWRzedzie];
    }

    public int[] reserveSpot(String nrRejestracyjny, int czasParkowania, double price) {
        for (int i = 0; i < iloscRzedow; i++) {
            for (int j = 0; j < iloscMiejscWRzedzie; j++) {
                if (parkingData[i][j] == null) { // znaleziono wolne miejsce
                    parkingData[i][j] = new Samochod(i + 1, j + 1, nrRejestracyjny, czasParkowania, price);
                    return new int[]{i + 1, j + 1}; // zwraca rząd i miejsce
                }
            }
        }
        return null; // brak wolnych miejsc
    }

    public ArrayList<Samochod> getReservedSpots() {
        ArrayList<Samochod> zajete = new ArrayList<>();
        for (int i = 0; i < iloscRzedow; i++) {
            for (int j = 0; j < iloscMiejscWRzedzie; j++) {
                if (parkingData[i][j] != null) {
                    zajete.add(parkingData[i][j]);
                }
            }
        }
        return zajete;
    }

    public ArrayList<Samochod> getFreeSpots() {
        ArrayList<Samochod> wolne = new ArrayList<>();
        for (int i = 0; i < iloscRzedow; i++) {
            for (int j = 0; j < iloscMiejscWRzedzie; j++) {
                if (parkingData[i][j] == null) {
                    wolne.add(new Samochod(i + 1, j + 1, "", 0, 0)); // Puste miejsce
                }
            }
        }
        return wolne;
    }

    public void leaveParking(int rzad, int miejsce) {
        if (parkingData[rzad][miejsce] != null) {
            parkingData[rzad][miejsce] = null;
        }
    }

    public int getIloscRzedow() {
        return iloscRzedow;
    }

    public int getIloscMiejscWRzedzie() {
        return iloscMiejscWRzedzie;
    }

    public ArrayList<String> getLicensePlates() {
        ArrayList<String> licensePlates = new ArrayList<>();
        for (int i = 0; i < iloscRzedow; i++) {
            for (int j = 0; j < iloscMiejscWRzedzie; j++) {
                if (parkingData[i][j] != null && parkingData[i][j].getNrRejestracyjny() != null) {
                    licensePlates.add(parkingData[i][j].getNrRejestracyjny().toUpperCase().replaceAll("\\s+", ""));
                }
            }
        }
        return licensePlates;
    }
}
