package org.example.parkinggui.symulator;

public class Samochod {
    String nrRejestracyjny;
    double rachunek;
    double dlug;
    double timeRemaining;

    int nrPietra;
    int nrRzedu;

    public Samochod(){

    }

    public String getNrRejestracyjny() {
        return nrRejestracyjny;
    }

    public double getRachunek() {
        return rachunek;
    }

    public double getDlug() {
        return dlug;
    }

    public void setDlug(double dlug) {
        this.dlug = this.dlug + dlug;
    }

    public double getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(double timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getNrPietra() {
        return nrPietra;
    }

    public void setNrPietra(int nrPietra) {
        this.nrPietra = nrPietra;
    }

    public void resetNrPietra() {
        this.nrPietra = Integer.parseInt(null);
    }

    public int getNrRzedu() {
        return nrRzedu;
    }

    public void setNrRzedu(int nrRzedu) {
        this.nrRzedu = nrRzedu;
    }

    public void resetNrRzedu() {
        this.nrRzedu = Integer.parseInt(null);
    }
}
