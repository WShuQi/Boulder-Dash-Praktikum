package com.example.g15_bugkiller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.json.JSONObject;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}