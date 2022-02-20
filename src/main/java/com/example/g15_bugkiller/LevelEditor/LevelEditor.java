package com.example.g15_bugkiller.LevelEditor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LevelEditor {

    public static final int START_WIDTH = 20;
    public static final int START_HEIGHT= 20;

    public static void openLevelEditor() {
        Stage stage = new Stage();
        stage.setTitle("Replay");

        BorderPane border = new BorderPane();

        //top title
        Text titleText = new Text("Bugkiller Level Editor");
        titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        titleText.setFill(Color.RED);
        VBox topBox = new VBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20,0,0,0));
        topBox.getChildren().add(titleText);
        border.setTop(topBox);

        //center map
        GridPane mapEdit = MapEdit.getMapEditGridPane(START_WIDTH, START_HEIGHT);
        mapEdit.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);
        scrollPane.setContent(mapEdit);

        BorderPane.setMargin(scrollPane, new Insets(20, 0, 0, 0));
        border.setCenter(scrollPane);


        //left levelSettings box
        VBox levelSettingsBox = LevelSettings.getLevelSettingsVBox();
        BorderPane.setMargin(levelSettingsBox, new Insets(10));
        border.setLeft(levelSettingsBox);

        //right fieldSettings box
        VBox fieldSettingsBox = FieldSettings.getFieldSettingsVBox();
        BorderPane.setMargin(fieldSettingsBox, new Insets(10));
        border.setRight(fieldSettingsBox);


        //bottom palette
        TypePalette typePalette = new TypePalette();
        BorderPane.setMargin(typePalette, new Insets(20));
        border.setBottom(typePalette);

        stage.setScene(new Scene(border, 1000, 1000));
        stage.show();
    }
}