module org.example.parkinggui {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.parkinggui to javafx.fxml;
    exports org.example.parkinggui;
}