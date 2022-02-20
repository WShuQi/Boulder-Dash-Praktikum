package com.example.g15_bugkiller.GameReplay;

import com.example.g15_bugkiller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private final double width;
    private final double height;

    public static final int MAX_BLOCK_SIZE = 64;
    public static final int START_FIELD_Y = 30;

    static List<Field[][]> savedMapData = new ArrayList<>();
    static GraphicsContext gc;
    static int currentFrame = 0;
    static Timeline timer;

    public GameReplay(double width, double height) {
        this.width = width;
        this.height = height;
    }

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

    public void openReplayWindow(List<Field[][]> savedReplayData){
        savedMapData = savedReplayData;

        currentFrame = 0;

        Stage stage = new Stage();
        stage.setMaxHeight(height + 70);
        stage.setMaxWidth(width);

        stage.setMinHeight(height + 70);
        stage.setMinWidth(width);

        stage.setTitle("Replay");

        BorderPane borderPane = new BorderPane();

        Canvas canvas = new Canvas(width, height);
        borderPane.setTop(canvas);

        gc = canvas.getGraphicsContext2D();

        Button replayButton = new Button("Watch Again");
        replayButton.setOnAction(actionEvent -> {
            currentFrame = 0;
            timer.stop();
            replay();
        });

        BorderPane buttonPane = new BorderPane();
        buttonPane.setCenter(replayButton);
        borderPane.setBottom(buttonPane);

        stage.setScene(new Scene(borderPane));

        stage.show();

        stage.setOnCloseRequest(event -> {
            timer.stop();
            timer = null;
        });

        replay();
    }

    private void replay(){
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

    private void drawLevel(Field[][] map) {
        gc.clearRect(0,0, width, height);

        int maxSpalten = map.length;
        int maxZeilen = map[0].length;

        int maxBlockWidth = (int) (width / maxSpalten);
        int maxBlockHeight = (int) (height / maxZeilen);

        int blockSize = Math.min(Math.min(MAX_BLOCK_SIZE, maxBlockHeight), maxBlockWidth);

        for(int spalte = 0; spalte < map.length; spalte++){
            for(int zeile = 0; zeile < map[spalte].length; zeile++) {
                Field field = map[spalte][zeile];

                int y = blockSize * zeile;
                int x = blockSize * spalte;

                Image image = PictureRepo.getImage(field.getType().name());
                gc.drawImage(image, x, y, blockSize, blockSize);
            }
        }
    }

    public static void clearSavedMap(){
        savedMapData = new ArrayList<>();
        currentFrame = 0;
    }

    public static List<Field[][]> getSavedMapData() {
        return savedMapData;
    }
}
