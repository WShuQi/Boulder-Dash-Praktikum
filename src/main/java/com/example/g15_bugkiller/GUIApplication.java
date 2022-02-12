package com.example.g15_bugkiller;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.Map;

public class GUIApplication extends Application {

    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 1000;


    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("Boulder Dash!");
        stage.setScene(scene);


        LevelReader levelReader = new LevelReader("src/main/java/com/example/g15_bugkiller/level");
        Map<String, Level> levels = levelReader.readLevel();

        Game game = new Game(levels, 100); //Todo: zweite Wert habe ich nur random gewaehlt


        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GUIView view = new GUIView(gc);


        root.getChildren().add(canvas);

        KeyPressListener keyPressListener = new KeyPressListener();

        stage.addEventFilter(KeyEvent.KEY_PRESSED, keyPressListener.keyPressed);
        stage.addEventFilter(KeyEvent.KEY_RELEASED, keyPressListener.keyReleased);

        GUIController controller = new GUIController(view, game, keyPressListener);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}