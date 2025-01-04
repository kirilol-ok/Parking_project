package org.example.parkinggui;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PaymentController {

    @FXML
    private Label remainingTimeLabel;

    @FXML
    private Label paymentAmountLabel;

    @FXML
    private Button payButton;

    @FXML
    private Label confirmationLabel;

    @FXML
    private Label errorLabel;

    private int delayMinutes = 0;
    private final int ratePerMinute = 2; // 2zl

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
            paymentAmountLabel.setText((delayMinutes * ratePerMinute) + " zł");
            payButton.setDisable(false);
        }
    }

    private void handlePayment() {
        if (delayMinutes > 0) {
            confirmationLabel.setText("Płatność za " + (delayMinutes * ratePerMinute) + " zł zakończona sukcesem!");
            confirmationLabel.setVisible(true);
            errorLabel.setVisible(false);

            System.out.println("Zapłacono za przekroczony czas parkowania.");
        } else {
            errorLabel.setText("Nie ma opłat do uiszczenia.");
            errorLabel.setVisible(true);
            confirmationLabel.setVisible(false);
        }
    }
}
