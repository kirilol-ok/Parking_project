module org.example.parkinggui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.example.parkinggui to javafx.fxml;
    exports org.example.parkinggui;
}