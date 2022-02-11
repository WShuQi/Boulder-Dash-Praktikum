package com.example.g15_bugkiller;

import MapGeneration.Json;
import MapGeneration.MainRulesJson;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUIApplication extends Application {

    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 1000;


    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setTitle("Boulder Dash!");
        stage.setScene(scene);

        MainRulesJson mainRulesJson = new MainRulesJson("src/main/java/com/example/g15_bugkiller/mainRules.json");
        List<Level> levels = new ArrayList<>();
        Json textJson = new Json("src/main/java/com/example/g15_bugkiller/level/text.json");
        Level textLevel = textJson.getLevel();
        textLevel.setMainRules(mainRulesJson.readMainRules());
        levels.add(textLevel);

        Json bereicheJson = new Json("src/main/java/com/example/g15_bugkiller/level/bereiche.json");
        Level bereicheLevel = bereicheJson.getLevel();
        bereicheLevel.setMainRules(mainRulesJson.readMainRules());
        levels.add(bereicheLevel);

        Json labyrinthJson = new Json("src/main/java/com/example/g15_bugkiller/level/labyrinth.json");
        Level labyrinthLevel = labyrinthJson.getLevel();
        labyrinthLevel.setMainRules(mainRulesJson.readMainRules());
        levels.add(labyrinthLevel);

        Json smileyJson = new Json("src/main/java/com/example/g15_bugkiller/level/smiley.json");
        Level smileyLevel = smileyJson.getLevel();
        smileyLevel.setMainRules(mainRulesJson.readMainRules());
        levels.add(smileyLevel);

        Json trichterJson = new Json("src/main/java/com/example/g15_bugkiller/level/trichter.json");
        Level trichterLevel = trichterJson.getLevel();
        trichterLevel.setMainRules(mainRulesJson.readMainRules());
        levels.add(trichterLevel);

        Json wandJson = new Json("src/main/java/com/example/g15_bugkiller/level/wand.json");
        Level wandLevel = wandJson.getLevel();
        wandLevel.setMainRules(mainRulesJson.readMainRules());
        levels.add(wandLevel);

        Json schicksalJson = new Json("src/main/java/com/example/g15_bugkiller/level/schicksal.json");
        Level schicksalLevel = schicksalJson.getLevel();
        schicksalLevel.setMainRules(mainRulesJson.readMainRules());
        levels.add(schicksalLevel);
        //Level level = TestInputData.createLevelData();

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