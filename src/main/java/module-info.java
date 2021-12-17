module com.example.g15_bugkiller {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.g15_bugkiller to javafx.fxml;
    exports com.example.g15_bugkiller;
}