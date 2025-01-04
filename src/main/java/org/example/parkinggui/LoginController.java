package org.example.parkinggui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

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
    private TextField usernameField;

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
        } else if (duration == null) {
            errorLabel.setText("Proszę wybrać czas parkowania.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
        } else {
            confirmationLabel.setText("Bilet zakupiony na " + duration + " dla samochodu: " + licensePlate);
            confirmationLabel.setVisible(true);
            errorLabel.setVisible(false);
        }
    }

    private void handleLogin() {
        String password = passwordField.getText();

        if ("admin".equals(password)) {
            openAdminWindow();
        } else {
            loginErrorLabel.setText("Niepoprawne hasło.");
            loginErrorLabel.setVisible(true);
        }
    }

    private void openAdminWindow() {
        try {
            // admin-window.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-window.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Panel Administratora");
            stage.show();

            // zamkniecie login-window - opcjonalnie
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();

            /*
            pozniej mozna dodac:

                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Błąd podczas otwierania okna administratora", e);
                showAlert("Błąd", "Nie można otworzyć okna administratora.");

             */
        }
    }
}
