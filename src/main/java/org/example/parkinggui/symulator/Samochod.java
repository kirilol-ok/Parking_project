package org.example.parkinggui.symulator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Samochod {
    private final SimpleStringProperty nrRejestracyjny;
    private final DoubleProperty rachunek;
    private final DoubleProperty dlug;
    private final DoubleProperty timeRemaining;
    private final SimpleIntegerProperty nrMiejsca;
    private final SimpleIntegerProperty nrRzedu;
    private boolean zajete;
    public String status;

    public Samochod(int nrRzedu, int nrMiejsca, String nrRejestracyjny, double timeRemaining, double price) {
        this.nrRejestracyjny = new SimpleStringProperty(nrRejestracyjny);
        this.rachunek = new SimpleDoubleProperty(price);
        this.dlug = new SimpleDoubleProperty(0);
        this.timeRemaining = new SimpleDoubleProperty(timeRemaining);
        this.nrMiejsca = new SimpleIntegerProperty(nrMiejsca);
        this.nrRzedu = new SimpleIntegerProperty(nrRzedu);
        this.zajete = true;
    }

    public Samochod(Samochod samochod) {
        this.nrRejestracyjny = new SimpleStringProperty(samochod.getNrRejestracyjny());
        this.rachunek = new SimpleDoubleProperty(samochod.getRachunek());
        this.dlug = new SimpleDoubleProperty(samochod.getDlug());
        this.timeRemaining = new SimpleDoubleProperty(samochod.getTimeRemaining());
        this.nrMiejsca = new SimpleIntegerProperty(samochod.getNrMiejsca());
        this.nrRzedu = new SimpleIntegerProperty(samochod.getNrRzedu());
    }

    public String getNrRejestracyjny() {
        return nrRejestracyjny.get();
    }

    public void setNrRejestracyjny(String nrRejestracyjny) {
        this.nrRejestracyjny.set(nrRejestracyjny);
    }

    public double getRachunek() {
        return rachunek.get();
    }

    public void setRachunek(double rachunek) {
        this.rachunek.set(rachunek);
    }

    public double getDlug() {
        return dlug.get();
    }

    public void setDlug(double dlug) { this.dlug.set(dlug); }

    public double getTimeRemaining() {
        return timeRemaining.get();
    }

    public void setTimeRemaining(double timeRemaining) {
        this.timeRemaining.set(timeRemaining);
    }

    public DoubleProperty timeRemainingProperty() {
        return timeRemaining;
    }

    public int getNrMiejsca() {
        return nrMiejsca.get();
    }

    public void setNrMiejsca(int nrMiejsca) {
        this.nrMiejsca.set(nrMiejsca);
    }

    public SimpleIntegerProperty nrMiejscaProperty() {
        return nrMiejsca;
    }

    public int getNrRzedu() {
        return nrRzedu.get();
    }

    public void setNrRzedu(int nrRzedu) {
        this.nrRzedu.set(nrRzedu);
    }

    public SimpleIntegerProperty nrRzeduProperty() {
        return nrRzedu;
    }

    public int getTimeOverdue() {
        return (int) Math.max(0, -timeRemaining.get()); // zwraca spoznienie jeśli czas na minusie
    }

    public boolean isZajete() {
        return zajete;
    }

    public void calculateFine() {
        if (timeRemaining.get() < 0) {
            dlug.set(dlug.get() + 1);
        }
    }

    public void opuscParking() {
        zajete = false;
        timeRemaining.set(0);
        System.out.println("Samochód z rejestracją " + nrRejestracyjny + " opuścił parking.");
    }
}