package org.example.parkinggui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader adminLoader = new FXMLLoader(HelloApplication.class.getResource("admin-window.fxml"));
        adminLoader.load();
        AdminController adminController = adminLoader.getController();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        LoginController loginController = fxmlLoader.getController();

        FXMLLoader paymentLoader = new FXMLLoader(getClass().getResource("payment-window.fxml"));
        Scene paymentScene = new Scene(paymentLoader.load());
        PaymentController paymentController = paymentLoader.getController();

        loginController.setAdminController(adminController);
        paymentController.setAdminController(adminController);
        adminController.setPaymentController(paymentController);

        stage.setTitle("Okno Logowania");
        stage.setScene(scene);
        stage.show();
    }


    public void openAdminWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-window.fxml"));
        Scene adminScene = new Scene(loader.load());
        AdminController adminController = loader.getController();

        Stage adminStage = new Stage();
        adminStage.setTitle("Panel Administratora");
        adminStage.setScene(adminScene);
        adminStage.show();
}

public void openPaymentWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("payment-window.fxml"));
        Scene paymentScene = new Scene(loader.load());
        PaymentController paymentController = loader.getController();

        Stage paymentStage = new Stage();
        paymentStage.setTitle("Payment Panel");
        paymentStage.setScene(paymentScene);
        paymentStage.show();
}

    public static void main(String[] args) { launch(); }
}
