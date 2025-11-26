module com.example.mco2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;

    opens com.example.mco2 to javafx.graphics, javafx.fxml;

    exports com.example.mco2;
}