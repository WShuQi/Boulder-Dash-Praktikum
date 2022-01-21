package com.example.g15_bugkiller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.json.JSONObject;

public class HelloController {

    Level level;
    KeyPressListener keyPressListener;

    public HelloController(Level level, KeyPressListener keyPressListener) {
        this.level = level;
        this.keyPressListener = keyPressListener;
    }

    public void executeTimeline(){

        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                KeyPressListener currentKeysPressed = keyPressListener.getClone();
                LevelLogic.tick(level, currentKeysPressed);
                //updateGraphics();
            }
        };

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), handler);
        Timeline timer = new Timeline(keyFrame);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }


}