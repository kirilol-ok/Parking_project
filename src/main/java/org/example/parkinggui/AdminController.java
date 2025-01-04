package org.example.parkinggui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminController {

    // do polaczenia z backendem
    @FXML
    private TableView<ParkingSpot> parkingTable;

    @FXML
    private TableColumn<ParkingSpot, String> rowColumn;

    @FXML
    private TableColumn<ParkingSpot, String> spotColumn;

    @FXML
    private TableColumn<ParkingSpot, String> statusColumn;

    @FXML
    private TableColumn<ParkingSpot, String> licenseColumn;

    @FXML
    private TableColumn<ParkingSpot, String> timeLeftColumn;

    @FXML
    private Button refreshButton;

    private ObservableList<ParkingSpot> parkingData;

    @FXML
    public void initialize() {
        // do uzupelnienia
        rowColumn.setCellValueFactory(data -> data.getValue().rowProperty());
        spotColumn.setCellValueFactory(data -> data.getValue().spotProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
        licenseColumn.setCellValueFactory(data -> data.getValue().licensePlateProperty());
        timeLeftColumn.setCellValueFactory(data -> data.getValue().timeLeftProperty());

        // do uzupelnienia
        parkingData = FXCollections.observableArrayList(generateSampleData());
        parkingTable.setItems(parkingData);

        refreshButton.setOnAction(event -> refreshParkingData());
    }

    private void refreshParkingData() {
        // Tutaj można zaimplementować logikę pobierania aktualnych danych z bazy danych lub API
        parkingData.setAll(generateSampleData()); // Przykład - zastąpienie danymi testowymi
    }

    /* tutaj jakis generator miejsc, powinien pozwolic na testy bez polaczenia z backendem
    private ObservableList<ParkingSpot> generateSampleData() {
        ObservableList<ParkingSpot> data = FXCollections.observableArrayList();

        for (int row = 1; row <= 6; row++) {
            for (int spot = 1; spot <= 8; spot++) {
                String status = (row % 2 == 0 && spot % 2 == 0) ? "Zajęte" : "Wolne";
                String license = status.equals("Zajęte") ? "ABC123" + row + spot : "";
                String timeLeft = status.equals("Zajęte") ? "1h 20m" : "0h 0m";

                data.add(new ParkingSpot(
                        "Rząd " + row,
                        "Miejsce " + spot,
                        status,
                        license,
                        timeLeft
                ));
            }
        }
        return data;
    }

     */
}

/*
MODEL PARKING SPOT ------------------------------------------------------------------

package org.example.parkinggui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ParkingSpot {

    private final StringProperty row;
    private final StringProperty spot;
    private final StringProperty status;
    private final StringProperty licensePlate;
    private final StringProperty timeLeft;

    public ParkingSpot(String row, String spot, String status, String licensePlate, String timeLeft) {
        this.row = new SimpleStringProperty(row);
        this.spot = new SimpleStringProperty(spot);
        this.status = new SimpleStringProperty(status);
        this.licensePlate = new SimpleStringProperty(licensePlate);
        this.timeLeft = new SimpleStringProperty(timeLeft);
    }

    public StringProperty rowProperty() {
        return row;
    }

    public StringProperty spotProperty() {
        return spot;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty licensePlateProperty() {
        return licensePlate;
    }

    public StringProperty timeLeftProperty() {
        return timeLeft;
    }
}



 */
