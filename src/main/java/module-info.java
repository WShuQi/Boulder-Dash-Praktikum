module com.example.g15_bugkiller {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.g15_bugkiller to javafx.fxml;
    exports com.example.g15_bugkiller;
}