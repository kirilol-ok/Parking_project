package org.example.parkinggui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parkinggui.symulator.Samochod;

import java.util.ArrayList;
import java.util.List;

public class AdminController {

    @FXML
    private TableView<Samochod> parkingTable;

    @FXML
    private TableColumn<Samochod, String> rowColumn;

    @FXML
    private TableColumn<Samochod, String> spotColumn;

    @FXML
    private TableColumn<Samochod, String> statusColumn;

    @FXML
    private TableColumn<Samochod, String> licenseColumn;

    @FXML
    private TableColumn<Samochod, String> timeLeftColumn;

    @FXML
    private Button refreshButton;

    private ObservableList<Samochod> parkingData;

    @FXML
    public void initialize() {
        // Powiązanie kolumn tabeli z właściwościami klasy Samochod
        rowColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNrRzedu())));
        spotColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNrPietra())));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimeRemaining() > 0 ? "Zajęte" : "Wolne"));
        licenseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNrRejestracyjny()));
        timeLeftColumn.setCellValueFactory(data -> new SimpleStringProperty(formatTime(data.getValue().getTimeRemaining())));

        // Inicjalizacja danych
        parkingData = FXCollections.observableArrayList(generateSampleData());
        parkingTable.setItems(parkingData);

        // Obsługa przycisku odświeżania
        refreshButton.setOnAction(event -> refreshParkingData());
    }

    private void refreshParkingData() {
        // Tutaj można zaimplementować logikę pobierania aktualnych danych z bazy danych lub API
        parkingData.setAll(generateSampleData()); // Przykład - zastąpienie danymi testowymi
    }

    private List<Samochod> generateSampleData() {
        // Generowanie przykładowych danych o samochodach
        List<Samochod> samochody = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Samochod samochod = new Samochod();
            samochod.nrRejestracyjny = ;
            samochod.rachunek = ;
            samochod.timeRemaining = ;
            samochod.nrRzedu = ;
            samochod.nrPietra = ;
            samochody.add(samochod);
        }
        return samochody;
    }

    private String formatTime(double timeInMinutes) {
        if (timeInMinutes <= 0) {
            return "Brak";
        }
        int hours = (int) timeInMinutes / 60;
        int minutes = (int) timeInMinutes % 60;
        return hours + "h " + minutes + "min";
    }
}
