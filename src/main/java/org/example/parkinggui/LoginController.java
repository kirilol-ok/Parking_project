package org.example.parkinggui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.parkinggui.symulator.EmpDatabase;
import org.example.parkinggui.symulator.Parking;
import org.example.parkinggui.symulator.Samochod;

import java.io.IOException;
import java.util.Map;

public class LoginController {

    private AdminController adminController;

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    Parking parking = new Parking();

    @FXML
    private ComboBox<String> durationComboBox;

    @FXML
    private TextField licensePlateTextField;

    @FXML
    private Button buyTicketButton;

    @FXML
    private Label confirmationLabel;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginErrorLabel;

    @FXML
    public void initialize() {
        buyTicketButton.setOnAction(event -> handleBuyTicket());
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleBuyTicket() {
        String licensePlate = licensePlateTextField.getText();
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

        // mapa zaproponowana przez chat, na pewno lepsze niz if else
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
        Samochod samochod = new Samochod(0, 0, licensePlate, czasParkowania, price); // obiekt Samochod z rachunkiem
        System.out.println("Czas parkowania (minuty): " + czasParkowania); // Debug

        // zajmiuje miejsce
        int[] miejsce = parking.zajmijMiejsce(licensePlate, czasParkowania, price);
        if (miejsce != null) { // jesli dane miejsca sa poprawne
            adminController.zaktualizujMiejsce(miejsce[0], miejsce[1], licensePlate, czasParkowania, price);
            confirmationLabel.setText("Bilet zakupiony na " + duration + " dla samochodu: " + licensePlate);
            confirmationLabel.setVisible(true);
            errorLabel.setVisible(false);
        } else {
            errorLabel.setText("Brak wolnych miejsc.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
        }
    }

    // Wejście do systemu admin z kluczem
    private void handleLogin() {
        String password = passwordField.getText();
        EmpDatabase empDatabase = new EmpDatabase();
        boolean isLoggedIn = false;

        if (adminController == null) {
            loginErrorLabel.setText("AdminController nie został poprawnie ustawiony.");
            loginErrorLabel.setVisible(true);
            return;
        }

        for (int i = 0; i < empDatabase.getEmpDatabase().size(); i++) {
            Object[] row = empDatabase.getEmpDatabase().get(i);
            if (row[3].equals(password)) {
                openAdminWindow();
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

    private void openAdminWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-window.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Panel Administratora");

            AdminController adminController = loader.getController();
            this.setAdminController(adminController);

            stage.show();

            // Stage currentStage = (Stage) loginButton.getScene().getWindow(); login-window zamyka sie po zalogowaniu admina
            // currentStage.close();
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
