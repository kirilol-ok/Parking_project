package org.example.parkinggui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.parkinggui.symulator.EmpDatabase;
import org.example.parkinggui.symulator.Parking;
import org.example.parkinggui.symulator.Samochod;

import java.util.Map;

public class LoginController {

    private AdminController adminController;
    private PaymentController paymentController;

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    public void setPaymentController(PaymentController paymentController) {
        this.paymentController = paymentController;
    }

    Parking parking = new Parking();

    @FXML
    private ComboBox<String> durationComboBox;

    @FXML
    private TextField licensePlateTextField;

    @FXML
    private Button buyTicketButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button deleteCarButton;

    @FXML
    private Label confirmationLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label loginErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
        buyTicketButton.setOnAction(event -> handleBuyTicket());
        loginButton.setOnAction(event -> handleLogin());
        deleteCarButton.setOnAction(event -> handleDeleteCar());
    }

    private void handleBuyTicket() {
        String licensePlate = licensePlateTextField.getText();
        if (licensePlate != null) {
            licensePlate = licensePlate.replaceAll("\\s+", "").toUpperCase(); // Убираем пробелы и переводим в верхний регистр
        }
        String duration = durationComboBox.getValue();

        if (licensePlate == null || licensePlate.isEmpty()) {
            errorLabel.setText("Proszę podać numer rejestracyjny.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
            return;
        } else if (licensePlate.length() > 8) {
            errorLabel.setText("Nieprawidłowy numer rejestracyjny.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
            return;
        }

        if (duration == null) {
            errorLabel.setText("Proszę wybrać czas parkowania.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
            return;
        }

        for (String existingPlate : parking.getLicensePlates()) {
            if (existingPlate.equalsIgnoreCase(licensePlate)) {
                errorLabel.setText("Numer rejestracyjny już istnieje.");
                errorLabel.setVisible(true);
                confirmationLabel.setVisible(false);
                return;
            }
        }

        Map<String, Double> pricingMap = Map.of(
                "1 godzina", 12.0,
                "2 godziny", 20.0,
                "6 godzin", 35.0,
                "12 godzin", 50.0,
                "24 godziny", 90.0,
                "168 godzin (tydzień)", 600.0
        );

        Double price = pricingMap.get(duration);
        if (price == null) {
            errorLabel.setText("Nieprawidłowy czas parkowania.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
            return;
        }

        int czasParkowania = extractNumberFromString(duration) * 60;
        Samochod samochod = new Samochod(0, 0, licensePlate, czasParkowania, price); // Объект Samochod с учётом регистра
        System.out.println("Czas parkowania (minuty): " + czasParkowania); // Debug

        int[] miejsce = parking.reserveSpot(licensePlate, czasParkowania, price);
        if (miejsce != null) {
            adminController.zaktualizujMiejsce(miejsce[0], miejsce[1], licensePlate, czasParkowania, price);
            confirmationLabel.setText("Bilet zakupiony na " + duration + " dla samochodu: " + licensePlate);
            confirmationLabel.setVisible(true);
            errorLabel.setVisible(false);
            adminController.refreshTable();
            adminController.synchronizujDaneParkingowe();
        } else {
            errorLabel.setText("Brak wolnych miejsc.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
        }
    }

    private void handleLogin() {
        String password = passwordField.getText();
        EmpDatabase empDatabase = new EmpDatabase();
        boolean isLoggedIn = false;

        if (adminController == null) {
            loginErrorLabel.setText("AdminController nie został poprawnie ustawiony.");
            loginErrorLabel.setVisible(true);
            return;
        }

        empDatabase.getEmpDatabase().forEach(row -> {
            row[0] = row[0].toString().toUpperCase();
        });

        for (int i = 0; i < empDatabase.getEmpDatabase().size(); i++) {
            Object[] row = empDatabase.getEmpDatabase().get(i);
            if (row[3].equals(password)) {
                openAdminWindow();
                passwordField.setText("");
                adminController.setParking(parking);
                isLoggedIn = true;
                break;
            }
        }

        if (!isLoggedIn) {
            loginErrorLabel.setText("Niepoprawne hasło.");
            loginErrorLabel.setVisible(true);
        }
    }

    private void handleDeleteCar() {
        String licensePlate = licensePlateTextField.getText();
        if (licensePlate != null) {
            licensePlate = licensePlate.replaceAll("\\s+", "").toUpperCase();
        }

        if (licensePlate == null || licensePlate.isEmpty()) {
            errorLabel.setText("Proszę podać numer rejestracyjny.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
            return;
        }

        Samochod targetCar = null;
        for (Samochod car : parking.getReservedSpots()) {
            if (car.getNrRejestracyjny().equals(licensePlate)) {
                targetCar = car;
                break;
            }
        }

        if (targetCar == null) {
            errorLabel.setText("Samochód o podanym numerze nie znajduje się na parkingu.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("payment-window.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Panel Opłat");

            PaymentController paymentController = loader.getController();
            this.setPaymentController(paymentController);

            paymentController.setSamochod(targetCar);
            paymentController.setAdminController(adminController);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Błąd podczas otwierania panelu opłaty.");
            errorLabel.setVisible(true);
        }
    }


    private void openAdminWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-window.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Panel Administratora");

            AdminController adminController = loader.getController();
            this.setAdminController(adminController);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            loginErrorLabel.setText("Błąd podczas otwierania panelu administratora.");
            loginErrorLabel.setVisible(true);
        }
    }

    private int extractNumberFromString(String input) {
        String number = input.replaceAll("[^0-9]", "");
        return number.isEmpty() ? 0 : Integer.parseInt(number);
    }

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
    }
}
