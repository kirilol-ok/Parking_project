package org.example.parkinggui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parkinggui.symulator.Samochod;
import org.example.parkinggui.symulator.Parking;

public class AdminController {
    LoginController loginController;
    public void getLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

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
    private Button refreshButton;

    @FXML
    public void initialize() {
        rowColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNrRzedu()));
        spotColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getNrMiejsca()));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimeRemaining() > 0 ? "Zajęte" : "Wolne"));
        licenseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNrRejestracyjny()));
        timeLeftColumn.setCellValueFactory(data -> new SimpleStringProperty(formatTime(data.getValue().getTimeRemaining())));
        parkingTable.setItems(samochody);
//        samochody.addAll(generateSampleData());
        refreshButton.setOnAction(event -> refreshParkingData());
    }

    public void refreshParkingData() {
        samochody.clear();
        samochody.addAll(generateSampleData());
    }

    private ObservableList<Samochod> generateSampleData() {
        ObservableList<Samochod> sampleData = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            Samochod samochod = new Samochod();
            samochod.setNrRzedu(i % 3 + 1);
            samochod.setNrMiejsca(i);
            samochod.setNrRejestracyjny("TEST" + i);
            samochod.setTimeRemaining(i * 10);
            sampleData.add(samochod);
        }
        return sampleData;
    }

    public void addSamochod(Samochod samochod) {
        samochody.add(samochod);
    }


    public ObservableList<Samochod> getSamochody() {
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

    public void refreshTable() {
        parkingTable.refresh();
    }
}
