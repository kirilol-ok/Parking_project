package org.example.parkinggui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parkinggui.symulator.BramkaPlatnosci;
import org.example.parkinggui.symulator.Samochod;

public class PaymentController {

    @FXML
    private TextField licensePlateField;

    @FXML
    private Label paymentAmountLabel;

    @FXML
    private Button payButton;

    private AdminController adminController;

    private BramkaPlatnosci bramkaPlatnosci = new BramkaPlatnosci();

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
        if (this.adminController == null) {
            showAlert("Błąd", "AdminController nie został poprawnie ustawiony.");
        }
    }

    @FXML
    public void initialize() {
        payButton.setOnAction(event -> handlePayment());
    }

    private Samochod findCarByLicensePlate(String licensePlate) {
        if (adminController != null && adminController.getSamochody() != null) {
            return adminController.getSamochody()
                    .stream()
                    .filter(samochod -> licensePlate.equalsIgnoreCase(samochod.getNrRejestracyjny()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private void handlePayment() {
        String licensePlate = licensePlateField.getText().trim();

        if (licensePlate.isEmpty()) {
            showAlert("Błąd", "Numer rejestracyjny nie może być pusty.");
            return;
        }

        Samochod samochod = findCarByLicensePlate(licensePlate);

        if (samochod == null) {
            showAlert("Błąd", "Samochód o podanym numerze rejestracyjnym nie został znaleziony.");
            return;
        }

        if (adminController == null) {
            showAlert("Błąd", "AdminController nie został poprawnie ustawiony.");
            return;
        }

        double naleznosc = calculatePayment(samochod);
        samochod.setRachunek(naleznosc);

        samochod.opuscParking();

        paymentAmountLabel.setText("Kwota do zapłaty: " + String.format("%.2f", naleznosc) + " PLN");

        adminController.refreshTable();
        adminController.synchronizujDaneParkingowe();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private double calculatePayment(Samochod samochod) {
        double czasParkowania = samochod.getTimeRemaining();
        double stawkaZaMinute = 0.5;
        return Math.abs(czasParkowania) * stawkaZaMinute;
    }
}
