package com.example.g15_bugkiller.GameReplay;

import com.example.g15_bugkiller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameReplay {

    public static final int BLOCK_SIZE = 25;
    public static final int START_FIELD_Y = 30;

    static List<Field[][]> savedMapData = new ArrayList<>();
    static GraphicsContext gc;
    static int currentFrame = 0;
    static Timeline timer;

    public static void saveMapFrame(Field[][] map){
        Field[][] mapCopy = new Field[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Field fieldToCopy = map[i][j];
                mapCopy[i][j] = new Field(new Gegenstand(fieldToCopy.getType(), new Values()));
            }
        }

        savedMapData.add(mapCopy);
    }

    public static void openReplayWindow(List<Field[][]> savedReplayData){
        savedMapData = savedReplayData;

        currentFrame = 0;

        Stage stage = new Stage();
        stage.setTitle("Replay");

        BorderPane borderPane = new BorderPane();

        Canvas canvas = new Canvas(GUIView.SCREEN_WIDTH, GUIView.SCREEN_HEIGHT);
        borderPane.setCenter(canvas);

        gc = canvas.getGraphicsContext2D();

        Button replayButton = new Button("Watch Again");
        replayButton.setOnAction(actionEvent -> {
            currentFrame = 0;
            timer.stop();
            replay();
        });

        borderPane.setBottom(replayButton);

        stage.setScene(new Scene(borderPane));

        stage.show();
        replay();
    }

    private static void replay(){
        int totalMapFrames = savedMapData.size();

        //System.out.println(totalMapFrames);

        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                drawLevel(savedMapData.get(currentFrame));
                currentFrame++;

                //System.out.println("Replay currentFrame: " + currentFrame);
            }
        };

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), handler);
        timer = new Timeline(keyFrame);
        timer.setCycleCount(totalMapFrames);
        timer.play();
    }

    private static void drawLevel(Field[][] map) {
        gc.clearRect(0,0, GUIView.SCREEN_WIDTH, GUIView.SCREEN_HEIGHT);

        for(int zeile = 0; zeile < map.length; zeile++){
            for(int spalte = 0; spalte < map[zeile].length; spalte++) {
                Field field = map[zeile][spalte];

                int y = BLOCK_SIZE * spalte + START_FIELD_Y;
                int x = BLOCK_SIZE * zeile;

                Image image = PictureRepo.getImage(field.getType().name());
                gc.drawImage(image, x, y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    public static void clearSavedMap(){
        savedMapData = new ArrayList<>();
    }

    public static List<Field[][]> getSavedMapData() {
        return savedMapData;
    }
}
