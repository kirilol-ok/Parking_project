package org.example.parkinggui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.parkinggui.symulator.BramkaPlatnosci;

public class PaymentController {

    @FXML
    private Label remainingTimeLabel;

    @FXML
    private Label paymentAmountLabel;

    @FXML
    private TextField paymentCodeField;

    @FXML
    private Button payButton;

    @FXML
    private Label confirmationLabel;

    @FXML
    private Label errorLabel;

    private int delayMinutes = 0;
    private final int ratePerMinute = 2; // 2 zl
    private final BramkaPlatnosci bramkaPlatnosci = new BramkaPlatnosci();

    @FXML
    public void initialize() {
        updatePaymentDetails();
        payButton.setOnAction(event -> handlePayment());
    }

    public void setDelayMinutes(int delay) {
        this.delayMinutes = delay;
        updatePaymentDetails();
    }

    private void updatePaymentDetails() {
        if (delayMinutes <= 0) {
            remainingTimeLabel.setText("Wyjazd przed czasem. Brak opłat.");
            paymentAmountLabel.setText("0 zł");
            payButton.setDisable(true);
        } else {
            remainingTimeLabel.setText(delayMinutes + " minut spóźnienia");
            int amount = delayMinutes * ratePerMinute;
            paymentAmountLabel.setText(amount + " zł");

            // generowanie kodu z BramkaPlatnosci.java
            bramkaPlatnosci.resetujKod();
            bramkaPlatnosci.wegenerujKod();
            payButton.setDisable(false);
        }
    }

    private void handlePayment() {
        String userCode = paymentCodeField.getText();
        String generatedCode = bramkaPlatnosci.getKodFlik();

        if (delayMinutes > 0 && userCode.equals(generatedCode)) {
            confirmationLabel.setText("Płatność za " + (delayMinutes * ratePerMinute) + " zł zakończona sukcesem!");
            confirmationLabel.setVisible(true);
            errorLabel.setVisible(false);

            System.out.println("Zapłacono za przekroczony czas parkowania.");
        } else if (!userCode.equals(generatedCode)) {
            errorLabel.setText("Niepoprawny kod płatności.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
        } else {
            errorLabel.setText("Brak opłat do uiszczenia.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
        }
    }
}
