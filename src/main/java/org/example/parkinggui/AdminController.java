package org.example.parkinggui;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.parkinggui.symulator.Listener;
import org.example.parkinggui.symulator.Parking;
import org.example.parkinggui.symulator.Samochod;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AdminController implements Listener {
    @Override
    public void update() {
        Platform.runLater(this::refreshTable);
        Platform.runLater(this::synchronizujDaneParkingowe);
    }

    private PaymentController paymentController;

    public void setPaymentController(PaymentController paymentController) {
        this.paymentController = paymentController;
        if (this.paymentController == null) {
            showAlert("Błąd", "PaymentController nie został poprawnie ustawiony.");
        }
    }

    private Parking parking = new Parking();

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
    private TableColumn<Samochod, Double> amountDueColumn;
    @FXML
    private TableColumn<Samochod, String> generatedCodeColumn;

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
        generatedCodeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodeFlik()));

        // 15 wolnych miejsc
        for (int i = 1; i <= parking.getIloscRzedow(); i++) {
            for (int j = 1; j <= parking.getIloscMiejscWRzedzie(); j++) {
                samochody.add(new Samochod(i, j, "", 0, 0)); // Rząd, miejsce, brak rejestracji, czas = 0
            }
        }

        samochody.forEach(samochod -> {
            samochod.addListener(this);
        });
        parkingTable.setItems(samochody);

        startTimer(); // miara czasu aby aktualizowac ten czas parkowania

        leaveButton.setOnAction(event -> handleLeave());
    }

    public ObservableList<Samochod> getSamochody() {
        return samochody;
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
        samochody.addAll(parking.getFreeSpots());
        samochody.addAll(parking.getReservedSpots());
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
            if(!selectedSamochod.getCodeFlik().equals("")){
//                paymentController.closeWindow();
                selectedSamochod.removeListener(this);
                selectedSamochod.leaveParking();
                parking.leaveParking(selectedSamochod.getNrRzedu() - 1, selectedSamochod.getNrMiejsca() - 1);
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
                Alert alert = new Alert(Alert.AlertType.WARNING, "Samochod " + selectedSamochod.getNrRejestracyjny() + " nie jeszcze nie płacił", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "To miejsce jest już wolne.", ButtonType.OK);
            alert.showAndWait();
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

    public void renewCodeFlik(String licensePlate, String flikCode) {
        for (Samochod samochod : parking.getReservedSpots()) {
            if (samochod.getNrRejestracyjny().equals(licensePlate)) {
                samochod.setCodeFlik(flikCode);
                refreshTable();
                break;
            }
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
