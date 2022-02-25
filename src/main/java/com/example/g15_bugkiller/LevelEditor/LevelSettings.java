package com.example.g15_bugkiller.LevelEditor;

import MapGeneration.Gamesaver;
import MapGeneration.Input;
import com.example.g15_bugkiller.Field;
import com.example.g15_bugkiller.Level;
import com.example.g15_bugkiller.TerminalMap;
import com.example.g15_bugkiller.Type;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LevelSettings {


    static VBox levelSettings = new VBox();

    public static VBox getLevelSettingsVBox(){

        //level name
        Label levelNameLabel = new Label("Level Name:");
        TextField levelNameInput = new TextField("mylevel");
        Separator levelNameSeparator = new Separator();
        levelNameSeparator.setPadding(new Insets(10, 0, 10, 0));

        VBox levelNameInputBox = new VBox();
        levelNameInputBox.getChildren().addAll(levelNameLabel, levelNameInput, levelNameSeparator);

        //map size
        //Width
        Label widthInputLabel = new Label("Width:");
        Spinner<Integer> widthInput = new Spinner<>(3, 1000, 20);
        VBox widthInputBox = new VBox();
        widthInputBox.getChildren().addAll(widthInputLabel, widthInput);

        //Height
        Label heightInputLabel = new Label("Height:");
        Spinner<Integer> heightInput = new Spinner<>(3, 1000, 20);
        VBox heightInputBox = new VBox();
        heightInputBox.getChildren().addAll(heightInputLabel, heightInput);

        Separator mapSizeSeparator = new Separator();
        mapSizeSeparator.setPadding(new Insets(10, 0, 10, 0));

        Label mapSizeLabel = new Label("Map Size:");
        VBox mapSizeBox = new VBox();
        mapSizeBox.getChildren().addAll(mapSizeLabel, widthInputBox, heightInputBox, mapSizeSeparator);

        //map size listen
        heightInput.valueProperty().addListener(event -> {
            MapEdit.updateSize(widthInput.getValue(), heightInput.getValue());
        });
        widthInput.valueProperty().addListener(event -> {
            MapEdit.updateSize(widthInput.getValue(), heightInput.getValue());
        });

        //Ticks Input
        //1
        Label ticksInputLabel1 = new Label("1");
        Spinner<Integer> ticksInput1 = new Spinner<>(1, 10000, 100);
        VBox ticksBox1 = new VBox();
        ticksBox1.getChildren().addAll(ticksInputLabel1, ticksInput1);
        //2
        Label ticksInputLabel2 = new Label("2");
        Spinner<Integer> ticksInput2 = new Spinner<>(1, 10000, 500);
        VBox ticksBox2 = new VBox();
        ticksBox2.getChildren().addAll(ticksInputLabel2, ticksInput2);
        //3
        Label ticksInputLabel3 = new Label("3");
        Spinner<Integer> ticksInput3 = new Spinner<>(1, 10000, 1000);
        VBox ticksBox3 = new VBox();
        ticksBox3.getChildren().addAll(ticksInputLabel3, ticksInput3);

        Label ticksBoxLabel = new Label("Ticks");

        Separator ticksSeparator = new Separator();
        ticksSeparator.setPadding(new Insets(10, 0, 10, 0));

        VBox ticksBox = new VBox();
        ticksBox.getChildren().addAll(ticksBoxLabel, ticksBox1, ticksBox2, ticksBox3, ticksSeparator);

        //Gems
        Label gemsInputLabel1 = new Label("1");
        Spinner<Integer> gemsInput1 = new Spinner<>(1, 10000, 2);
        VBox gemsBox1 = new VBox();
        gemsBox1.getChildren().addAll(gemsInputLabel1, gemsInput1);
        //2
        Label gemsInputLabel2 = new Label("2");
        Spinner<Integer> gemsInput2 = new Spinner<>(1, 10000, 5);
        VBox gemsBox2 = new VBox();
        gemsBox2.getChildren().addAll(gemsInputLabel2, gemsInput2);
        //3
        Label gemsInputLabel3 = new Label("3");
        Spinner<Integer> gemsInput3 = new Spinner<>(1, 10000, 10);
        VBox gemsBox3 = new VBox();
        gemsBox3.getChildren().addAll(gemsInputLabel3, gemsInput3);

        Separator gemsSeparator = new Separator();
        gemsSeparator.setPadding(new Insets(10, 0, 10, 0));

        Label gemsBoxLabel = new Label("Gems");
        VBox gemsBox = new VBox();
        gemsBox.getChildren().addAll(gemsBoxLabel, gemsBox1, gemsBox2, gemsBox3, gemsSeparator);

        //Lives
        Label livesInputLabel = new Label("Lives");
        Spinner<Integer> livesInput = new Spinner<>(0, 10000, 3);

        Separator livesSeparator = new Separator();
        livesSeparator.setPadding(new Insets(10, 0, 10, 0));

        VBox livesBox = new VBox();
        livesBox.getChildren().addAll(livesInputLabel, livesInput, livesSeparator);

        //save button
        Button saveButton = new Button("Save Level");
        saveButton.setOnAction(event -> {
            String name = levelNameInput.getText();
            int[] gems = {gemsInput1.getValue(), gemsInput2.getValue(), gemsInput3.getValue()};
            int[] ticks = {ticksInput1.getValue(), ticksInput2.getValue(), ticksInput3.getValue()};
            Input mapData = MapEdit.getInput();
            int lives = livesInput.getValue();

            Level level = new Level(name, gems, mapData, ticks, lives);

            if(!levelHasMeAndExit(level)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Map");
                alert.setHeaderText("Level has to include ME and EXIT");
                alert.showAndWait();
                return;
            }

            Gamesaver gamesaver = new Gamesaver("src/main/java/com/example/g15_bugkiller/level");
            gamesaver.getLevelData(level);
            gamesaver.createJson(level.getLevelName());

            System.out.println("saving level: " + level.getLevelName());
            TerminalMap.drawMap(level.getLevelMap());
        });

        Separator buttonSeparator = new Separator();
        buttonSeparator.setPadding(new Insets(10, 0, 10, 0));

        //reset button
        Button resetMapButton = new Button("Reset Map");
        resetMapButton.setOnAction(event -> {
            MapEdit.resetMap();

        });

        levelSettings.getChildren().clear();
        levelSettings.getChildren().addAll(levelNameInputBox, mapSizeBox, ticksBox, gemsBox, livesBox, saveButton, buttonSeparator, resetMapButton);
        return levelSettings;
    }


    private static boolean levelHasMeAndExit(Level level){
        Field[][] map = level.getLevelMap();

        boolean hasMe = false;
        boolean hasExit = false;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j].getType() == Type.ME){
                    hasMe = true;
                }
                if(map[i][j].getType() == Type.EXIT){
                    hasExit = true;
                }
            }
        }
        return hasMe && hasExit;
    }
}