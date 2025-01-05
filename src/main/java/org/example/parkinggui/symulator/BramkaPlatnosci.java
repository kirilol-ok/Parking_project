package org.example.parkinggui.symulator;

import java.util.Random;
import java.util.random.*;

public class BramkaPlatnosci {
    String kodFlik = "";

    public String getKodFlik(){
        return kodFlik;
    }

    public void wegenerujKod(){
        Random rand = new Random();
        for(int i = 0; i<6; i++){
            kodFlik = kodFlik + rand.nextInt(10);
        }
        System.out.println(kodFlik);
    }

    public void resetujKod(){
        kodFlik = "";
    }

    public static void main(String[] args) {
        BramkaPlatnosci b = new BramkaPlatnosci();
        b.wegenerujKod();
        b.getKodFlik();
    }
}
