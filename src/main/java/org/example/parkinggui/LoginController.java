package org.example.parkinggui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.example.parkinggui.symulator.Parking;
import org.example.parkinggui.symulator.Samochod;
import org.example.parkinggui.symulator.EmpDatabase;

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
        Samochod samochod = new Samochod();

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

            samochod.setNrRejestracyjny(licensePlate);
            samochod.setTimeRemaining(extractNumberFromString(duration));
            for(int i = 1; i < parking.getDatabase().size(); i++){
                Object[] row = parking.getDatabase().get(i);
                if (row[2].equals(true)) {
                    samochod.setNrRzedu((Integer) row[0]);
                    samochod.setNrMiejsca((Integer) row[0]);
                }
            }
        }
        if (adminController == null) { //test
            System.out.println("Ошибка: adminController равен null.");
        } else if (adminController.getSamochody() == null) {
            System.out.println("Ошибка: список samochody равен null.");
        } else {
            adminController.getSamochody().add(samochod);
            adminController.refreshTable();
            System.out.println("Машина добавлена: " + samochod.getNrRejestracyjny());
            System.out.println("Текущий список: " + adminController.getSamochody());
        }
    }

    //wejscie do systemu admin z kluczem
    private void handleLogin() {
        String password = passwordField.getText();
        EmpDatabase empDatabase = new EmpDatabase();
        boolean isLoggedIn = false;

        for (int i = 0; i < empDatabase.getEmpDatabase().size(); i++) {
            Object[] row = empDatabase.getEmpDatabase().get(i);
            if (row[3].equals(password)) {
                openAdminWindow();
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

            //Stage currentStage = (Stage) loginButton.getScene().getWindow(); login-window zamyka sie po zalogowaniu admina
            //currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            loginErrorLabel.setText("Błąd podczas otwierania panelu administratora.");
            loginErrorLabel.setVisible(true);
        }
    }



    private int extractNumberFromString(String input) {
        String number = input.replaceAll("[^0-9]", "");
        return number.isEmpty() ? 0 : Integer.parseInt(number); //wyciaga liczby z textu
    }

    public static void main(String[] args) {
        LoginController loginController = new LoginController();
    }
}
