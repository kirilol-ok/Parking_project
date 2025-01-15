package org.example.parkinggui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parkinggui.symulator.Parking;
import org.example.parkinggui.symulator.Samochod;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AdminController {

    private Parking parking;

    private ObservableList<Samochod> samochody = FXCollections.observableArrayList();

    @FXML
    private TableView<Samochod> parkingTable;

    @FXML
    private TableColumn<Samochod, Integer> rowColumn;

    @FXML
    private TableColumn<Samochod, Integer> spotColumn;

    @FXML
    private TableColumn<Samochod, String> statusColumn;

    @FXML
    private TableColumn<Samochod, String> licenseColumn;

    @FXML
    private TableColumn<Samochod, String> timeLeftColumn;

    @FXML
    private TableColumn<Samochod, String> licensePlateColumn;

    @FXML
    private TableColumn<Samochod, Double> remainingTimeColumn;

    @FXML
    private Button refreshButton;


    @FXML
    private TextField licensePlateCheckTextField;

    @FXML
    private TableColumn<Samochod, Double> amountDueColumn;

    @FXML
    private Label remainingTimeLabel;

    @FXML
    private Label amountDueLabel;

    @FXML
    private Button leaveButton;

    @FXML
    public void initialize() {
        rowColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNrRzedu()));
        spotColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNrMiejsca()));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimeRemaining() > 0 ? "Zajęte" : "Wolne"));
        licenseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNrRejestracyjny()));
        timeLeftColumn.setCellValueFactory(data -> new SimpleStringProperty(formatTime(data.getValue().getTimeRemaining())));
        amountDueColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRachunek()));

        // 15 wolnych miejsc
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 5; j++) {
                samochody.add(new Samochod(i, j, "", 0, 0)); // Rząd, miejsce, brak rejestracji, czas = 0
            }
        }

        parkingTable.setItems(samochody);

        startTimer(); // miara czasu aby aktualizowac ten czas parkowania

        leaveButton.setOnAction(event -> handleLeave());
    }

    private double calculateAmountDue(Samochod samochod) {
        double baseRate = 2.0; // example rate per hour
        double timeParked = samochod.getTimeRemaining();
        return Math.max(0, (timeParked / 60) * baseRate);
    }

    public void addSamochod(Samochod samochod) {
        samochody.add(samochod);
        refreshTable();
    }

    public ObservableList<Samochod> getSamochody() {
        return samochody;
    }

    public void ustawWolneMiejsca() {
        samochody.clear();
        int iloscRzedow = 3;
        int iloscMiejscWRzedzie = 5;
        for (int i = 1; i <= iloscRzedow; i++) {
            for (int j = 1; j <= iloscMiejscWRzedzie; j++) {
                samochody.add(new Samochod(i, j, "", 0, 0));
            }
        }
        refreshTable();
    }

    public void zaktualizujMiejsce(int nrRzedu, int nrMiejsca, String nrRejestracyjny, double czasPozostaly, double price) {
        for (Samochod samochod : samochody) {
            if (samochod.getNrRzedu() == nrRzedu && samochod.getNrMiejsca() == nrMiejsca) {
                samochod.setNrRejestracyjny(nrRejestracyjny);
                samochod.setTimeRemaining(czasPozostaly);
                samochod.setRachunek(price);
                break; // znajdzie odpowiednie miejsce
            }
        }
        parkingTable.refresh();
    }

    public void synchronizujDaneParkingowe() {
        samochody.clear();
        samochody.addAll(parking.getWolneMiejsca());
        samochody.addAll(parking.getZajeteMiejsca());
        parkingTable.refresh();
    }

    public void setParking(Parking parking) {
        this.parking = parking;
        synchronizujDaneParkingowe();
    }

    private void startTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60), event -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTime() {
        for (Samochod samochod : samochody) {
            if (samochod.getTimeRemaining() > 0) {
                samochod.setTimeRemaining(samochod.getTimeRemaining() - 1); // -1 minuta
            } else if (samochod.getTimeRemaining() < 0) {
                samochod.calculateFine();
            }
        }
        parkingTable.refresh();
    }

    private void handleLeave() {
        Samochod selectedSamochod = parkingTable.getSelectionModel().getSelectedItem();
        if (selectedSamochod != null && selectedSamochod.isZajete()) {
            double dlug = selectedSamochod.getDlug();
            selectedSamochod.opuscParking();
            parking.opuscParking(selectedSamochod.getNrRzedu() - 1, selectedSamochod.getNrMiejsca() - 1);
            synchronizujDaneParkingowe(); // synchronizujemy dane po opuszczeniu parkingu
            refreshTable();
            if (selectedSamochod.getTimeRemaining() >= 0) {
                System.out.println("Samochód z miejsca " + selectedSamochod.getNrRzedu() + "-" + selectedSamochod.getNrMiejsca() +
                        " uiścił należność w wysokości " + selectedSamochod.getRachunek() + " zł i opuścił parking.");
            } else {
                System.out.println("Samochód z miejsca " + selectedSamochod.getNrRzedu() + "-" + selectedSamochod.getNrMiejsca() +
                        " uiścił należność w wysokości " + selectedSamochod.getRachunek() + " zł + " + dlug + " zl kary za " +
                        Math.abs(selectedSamochod.getTimeRemaining()) + " minut spoznienia" + " i opuścił parking.");;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "To miejsce jest już wolne.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void handleSamochodLeaving(Samochod selectedSamochod) {
        if (selectedSamochod != null) {
            selectedSamochod.opuscParking();
            parkingTable.refresh();
            System.out.println("Samochód z miejsca " + selectedSamochod.getNrRzedu() + "-" + selectedSamochod.getNrMiejsca() + " opuścił parking.");
        }
    }


    private String formatTime(double timeInMinutes) {
        int totalMinutes = (int) timeInMinutes; // zaokraglenie

        int hours = Math.abs(totalMinutes) / 60;
        int minutes = Math.abs(totalMinutes) % 60;

        if (totalMinutes >= 0) {
            return hours + "h " + minutes + "min";
        } else {
            return "-" + hours + "h " + minutes + "min";
        }
    }

    public void refreshTable() {
        parkingTable.refresh();
    }
}
