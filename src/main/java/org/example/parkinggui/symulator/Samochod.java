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
    public String status;

    public Samochod(int nrRzedu, int nrMiejsca, String nrRejestracyjny, double timeRemaining) {
        this.nrRejestracyjny = new SimpleStringProperty(nrRejestracyjny);
        this.rachunek = new SimpleDoubleProperty(0);
        this.dlug = new SimpleDoubleProperty(0);
        this.timeRemaining = new SimpleDoubleProperty(timeRemaining);
        this.nrMiejsca = new SimpleIntegerProperty(nrMiejsca);
        this.nrRzedu = new SimpleIntegerProperty(nrRzedu);
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

    public SimpleStringProperty nrRejestracyjnyProperty() {
        return nrRejestracyjny;
    }

    public double getRachunek() {
        return rachunek.get();
    }

    public void setRachunek(double rachunek) {
        this.rachunek.set(rachunek);
    }

    public DoubleProperty rachunekProperty() {
        return rachunek;
    }

    public double getDlug() {
        return dlug.get();
    }

    public void setDlug(double dlug) {
        this.dlug.set(this.dlug.get() + dlug);
    }

    public DoubleProperty dlugProperty() {
        return dlug;
    }

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
}