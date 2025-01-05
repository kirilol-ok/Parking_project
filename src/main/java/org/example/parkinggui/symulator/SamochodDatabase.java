package org.example.parkinggui.symulator;

import java.util.ArrayList;
import java.util.ArrayList.*;
import org.example.parkinggui.symulator.Samochod;

public class SamochodDatabase {
    ArrayList<Object[]> samochodData = new ArrayList<>();

    public SamochodDatabase() {}

    public void dodajSamochod(Samochod samochod) {
        samochodData.add(new Object[]{samochod.nrRzedu, samochod.nrMiejsca,
                samochod.status, samochod.nrRejestracyjny, samochod.timeRemaining});
    }

    public boolean usunSamochod(String nrRejestracyjny) {
        for (int i = 0; i < samochodData.size(); i++) {
            Object[] row = samochodData.get(i);
            if (row[3].equals(nrRejestracyjny)) {
                samochodData.remove(i);
                return true;
            }
        }
        return false;
    }

    public void printDatabase() {
        for (Object[] row : samochodData) {
            for (Object value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
