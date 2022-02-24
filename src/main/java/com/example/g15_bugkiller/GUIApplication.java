package com.example.g15_bugkiller;

// import com.example.g15_bugkiller.GameReplay.GameReplay;
import com.example.g15_bugkiller.LevelEditor.LevelEditor;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.Map;

public class GUIApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double height = Math.min(screenBounds.getHeight(), 1000d);
        double width = Math.min(screenBounds.getWidth(), 1000d);

        Group root = new Group();
        Scene scene = new Scene(root, width, height);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);

        stage.setMinHeight(height);
        stage.setMinWidth(width);

        stage.setTitle("Boulder Dash");
        stage.setScene(scene);

        LevelReader levelReader = new LevelReader("src/main/java/com/example/g15_bugkiller/level");
        Map<String, Level> levels = levelReader.readLevel();

        Game game = new Game(levels, 0.3, false);

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GUIView view = new GUIView(gc, width, height);

        root.getChildren().add(canvas);

        KeyPressListener keyPressListener = new KeyPressListener();

        stage.addEventFilter(KeyEvent.KEY_PRESSED, keyPressListener.keyPressed);
        stage.addEventFilter(KeyEvent.KEY_RELEASED, keyPressListener.keyReleased);

        GUIController controller = new GUIController(view, game, keyPressListener);

        stage.addEventFilter(MouseEvent.MOUSE_PRESSED,
                mouseEvent -> controller.mousePressed(mouseEvent.getX(), mouseEvent.getY()));
        stage.addEventFilter(ScrollEvent.SCROLL,
                scrollEvent -> controller.mouseScrolled(scrollEvent.getDeltaY()));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}