package com.example.g15_bugkiller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import org.json.JSONObject;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // for testing
        Level level = TestInputData.createLevelData();


        scene.addEventFilter(KeyEvent.KEY_PRESSED, KeyPressListener.keyPressed);

        scene.addEventFilter(KeyEvent.KEY_RELEASED, KeyPressListener.keyReleased);
    }

    public static void main(String[] args) {
        launch();
    }
}