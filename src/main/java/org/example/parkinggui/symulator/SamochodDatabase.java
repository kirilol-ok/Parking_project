package org.example.parkinggui.symulator;

import java.util.ArrayList;
import java.util.ArrayList.*;
import org.example.parkinggui.symulator.Samochod;

public class SamochodDatabase {
    ArrayList<Object[]> samochodData = new ArrayList<>();

    public SamochodDatabase() {}

    public void setSamochodData(Samochod samochod) {
        samochodData.add(new Object[]{samochod.nrRzedu, samochod.nrMiejsca,
                samochod.status, samochod.nrRzedu, samochod.nrMiejsca});
    }
}
