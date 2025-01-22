package org.example.parkinggui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.*;
import org.example.parkinggui.symulator.BramkaPlatnosci;
import org.example.parkinggui.symulator.Listener;
import org.example.parkinggui.symulator.Samochod;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class PaymentController extends Thread implements Listener {


    @FXML
    private javafx.scene.control.Label codeLabel;
    @FXML
    private javafx.scene.control.Label remainLabel;

    @FXML
    private javafx.scene.control.Button copyButton;
    @FXML
    private javafx.scene.control.Button generateButton;

    private AdminController adminController;

    private BramkaPlatnosci bramkaPlatnosci = new BramkaPlatnosci();

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
        if (this.adminController == null) {
            showAlert("Błąd", "AdminController nie został poprawnie ustawiony.");
        }
    }

    private Samochod samochod;

    public void setSamochod(Samochod samochod) {
        this.samochod = samochod;
    }

    @FXML
    public void initialize() {
        copyButton.setOnAction(event -> handleCopyCode());
        generateButton.setOnAction(event -> handleGenerateCode());
    }

    public void handleCopyCode() {
        StringSelection stringSelection = new StringSelection(codeLabel.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        System.out.println("Текст скопирован в буфер обмена: " + codeLabel.getText());
    }

    public void handleGenerateCode() {
        bramkaPlatnosci.resetCode();
        String flikCode = bramkaPlatnosci.getCodeFlik();
        codeLabel.setText(flikCode);
        generateButton.setDisable(true);

        if (adminController != null && samochod != null) {
            adminController.renewCodeFlik(samochod.getNrRejestracyjny(), flikCode);
        }

        new Thread(() -> {
            int timeLeft = 40;
            while (timeLeft > 0) {
                samochod.notifyListeners();
                int finalTimeLeft = timeLeft;
                javafx.application.Platform.runLater(() ->
                        remainLabel.setText("Ważny jeszcze: " + finalTimeLeft + " sec")
                );

                timeLeft--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            bramkaPlatnosci.resetCode();
            if (adminController != null && samochod != null) {
                adminController.renewCodeFlik(samochod.getNrRejestracyjny(), "");
            }

            javafx.application.Platform.runLater(() -> {
                codeLabel.setText("");
                remainLabel.setText("Wygeneruj ponownie");
                generateButton.setDisable(false);
            });
        }).start();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void update() {
        if (samochod == null) {
            javafx.application.Platform.runLater(this::closeWindow);
        } else {
            adminController.refreshTable();
            adminController.synchronizujDaneParkingowe();
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) codeLabel.getScene().getWindow();
        stage.close();
    }

}
