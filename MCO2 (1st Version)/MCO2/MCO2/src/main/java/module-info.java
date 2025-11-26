module com.example.mco2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mco2 to javafx.fxml;
    exports com.example.mco2;
}