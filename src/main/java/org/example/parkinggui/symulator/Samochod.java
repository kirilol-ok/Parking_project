package org.example.parkinggui.symulator;

public class Samochod {
    String nrRejestracyjny;
    double rachunek;
    double dlug;
    double timeRemaining;

    int nrMiejsca;
    int nrRzedu;

    String status;

    public Samochod(){
        this.nrRejestracyjny = "";
        this.rachunek = 0;
        this.dlug = 0;
        this.timeRemaining = 0;
        this.nrMiejsca = 0;
        this.nrRzedu = 0;
    }

    public String getNrRejestracyjny() {
        return nrRejestracyjny;
    }

    public void setNrRejestracyjny(String nrRejestracyjny) {
        this.nrRejestracyjny = nrRejestracyjny;
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

    public int getNrMiejsca() {
        return nrMiejsca;
    }

    public void setNrMiejsca(int nrMiejsca) {
        this.nrMiejsca = nrMiejsca;
    }

    public void resetNrPietra() {
        this.nrMiejsca = Integer.parseInt(null);
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
