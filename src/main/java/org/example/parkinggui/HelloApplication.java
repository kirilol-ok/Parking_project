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
        adminLoader.load(); // Загружаем admin-window.fxml
        AdminController adminController = adminLoader.getController(); // Получаем контроллер после load()

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        LoginController loginController = fxmlLoader.getController(); // Получаем контроллер после load()

        FXMLLoader paymentLoader = new FXMLLoader(getClass().getResource("payment-window.fxml"));
        Scene paymentScene = new Scene(paymentLoader.load());
        PaymentController paymentController = paymentLoader.getController();
        paymentController.setAdminController(adminController);

        loginController.setAdminController(adminController);
        paymentController.setAdminController(adminController);

        stage.setTitle("Logging window");
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

    public static void main(String[] args) { launch(); }
}
