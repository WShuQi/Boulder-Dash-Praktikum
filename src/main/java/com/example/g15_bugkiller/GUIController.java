package com.example.g15_bugkiller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.json.JSONObject;

public class GUIController {

    private GUIView view;
    private Level level;
    private KeyPressListener keyPressListener;

    public GUIController(GUIView view, Level level, KeyPressListener keyPressListener) {
        this.view = view;
        this.level = level;
        this.keyPressListener = keyPressListener;

        executeTimeline();
    }

    private void updateView() {
        view.drawLevel(level);
    }

    public void executeTimeline(){

        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                KeyPressListener currentKeysPressed = keyPressListener.getClone();
                LevelLogic.tick(level, currentKeysPressed);
                TerminalMap.drawMap(level.levelMap);
                updateView();
            }
        };

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), handler);
        Timeline timer = new Timeline(keyFrame);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }


}