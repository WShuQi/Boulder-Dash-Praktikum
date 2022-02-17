/* package com.example.g15_bugkiller.GameReplay;

import com.example.g15_bugkiller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.example.g15_bugkiller.GUIApplication.SCREEN_HEIGHT;
import static com.example.g15_bugkiller.GUIApplication.SCREEN_WIDTH;

public class GameReplay {

    public static final int BLOCK_SIZE = 32;
    public static final int START_FIELD_Y = 30;

    static List<Field[][]> savedMapData = new ArrayList<>();
    static GraphicsContext gc;

    public static void saveMapFrame(Field[][] map){
        savedMapData.add(map);
    }

    public static void openReplayWindow(){
        Stage stage = new Stage();
        stage.setTitle("Replay");

        Group root = new Group();

        Canvas canvas = new Canvas(1000, 1000);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        stage.setScene(new Scene(root, 1000, 1000));

        stage.show();
        replay();
    }

    static int currentFrame = 0;
    static Timeline timer;

    private static void replay(){
        int totalMapFrames = savedMapData.size();

        System.out.println(totalMapFrames);

        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                drawLevel(savedMapData.get(currentFrame));
                currentFrame++;

                System.out.println(currentFrame);

                if(currentFrame >= totalMapFrames){
                    timer.stop();
                }
            }
        };

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), handler);
        timer = new Timeline(keyFrame);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private static void drawLevel(Field[][] map) {
        gc.clearRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);

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
}

*/