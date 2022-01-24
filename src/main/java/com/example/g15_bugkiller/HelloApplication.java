package com.example.g15_bugkiller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import org.json.JSONObject;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        // for testing
        Level level = TestInputData.createLevelData();
        KeyPressListener keyPressListener = new KeyPressListener();
        HelloController controller = new HelloController(level, keyPressListener);

       // scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressListener.keyPressed);
        // scene.addEventFilter(KeyEvent.KEY_RELEASED, keyPressListener.keyReleased);

       // controller.executeTimeline();
    }

    public static void main(String[] args) {
        launch();
    }
}