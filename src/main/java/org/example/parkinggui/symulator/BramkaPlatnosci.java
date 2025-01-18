package org.example.parkinggui.symulator;

import java.util.Random;

public class BramkaPlatnosci {
    String kodFlik = "";

    public BramkaPlatnosci() {}

    public String getCodeFlik(){
        this.generateCode();
        return kodFlik;
    }

    public void generateCode(){
        Random rand = new Random();
        kodFlik = "";
        for(int i = 0; i<6; i++){
            kodFlik = kodFlik + rand.nextInt(10);
        }
        System.out.println(kodFlik);
    }

    public void resetCode(){
        kodFlik = "";
    }

    public static void main(String[] args) {
        BramkaPlatnosci b = new BramkaPlatnosci();
        b.generateCode();
        b.getCodeFlik();
    }
}
