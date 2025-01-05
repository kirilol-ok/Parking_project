package org.example.parkinggui.symulator;

import java.util.ArrayList;

public class Parking {
    int iloscRzedow;
    int iloscMiejsc;

    ArrayList<Object[]> parkingData = new ArrayList();

    public Parking() {
        this.iloscRzedow = 10;
        this.iloscMiejsc = 54;
        for(int i = 1; i < iloscRzedow; i++){
            for(int j = 1; j < iloscMiejsc/iloscRzedow+1; j++){
                parkingData.add(new Object[]{i, j, true});
            }
        }

        ArrayList<Integer> row = new ArrayList();
        for(int j = 1; j < iloscMiejsc%iloscRzedow+1; j++){
            parkingData.add(new Object[]{iloscRzedow, j, true});
        }

        printDatabase();
    }

    public Parking(int iloscRzedow, int iloscMiejsc) {
        this.iloscRzedow = iloscRzedow;
        this.iloscMiejsc = iloscMiejsc;
        for(int i = 1; i < iloscRzedow; i++){
            for(int j = 1; j < iloscMiejsc/iloscRzedow+1; j++){
                parkingData.add(new Object[]{i, j, true});
            }
        }

        ArrayList<Integer> row = new ArrayList();
        for(int j = 1; j < iloscMiejsc%iloscRzedow+1; j++){
            parkingData.add(new Object[]{iloscRzedow, j, true});
        }

        printDatabase();

    }

    public boolean getStatusMiejsca(int rzad, int miejsce) {
        for (Object[] row : parkingData) {
            int currentRzad = (int) row[0];
            int currentMiejsce = (int) row[1];
            if (currentRzad == rzad && currentMiejsce == miejsce) {
                return (boolean) row[2];
            }
        }
        throw new IllegalArgumentException("Lokalizacja o wskazanym rzadzie i numerze nie zostala znaleziona!");
    }

        public void printDatabase() {
        for (Object[] row : parkingData) {
            for (Object value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Parking parking = new Parking();
        System.out.println(parking.getStatusMiejsca(10, 4));
    }
}
