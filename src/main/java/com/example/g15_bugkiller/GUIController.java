package com.example.g15_bugkiller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class GUIController {

    private GUIView view;
    private Game game;
    private KeyPressListener keyPressListener;

    public GUIController(GUIView view, Game game, KeyPressListener keyPressListener) {
        this.view = view;
        this.game = game;
        this.keyPressListener = keyPressListener;

        playLevel(0);  //TODO: delete if level overview is implemented
    }

    private void updateView(Level level) {
        view.drawLevel(level);
    }

    public void executeTimeline(Level level){

        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                KeyPressListener currentKeysPressed = keyPressListener.getClone();
                LevelLogic.tick(level, currentKeysPressed);
                TerminalMap.drawMap(level.getLevelMap());
                updateView(level);

                if(level.isTimeUp() | level.isExitReached()){
                    LevelLogic.resetLevel(level);
                    //return to level overview
                    game.unlockNextLevelAsNecessary();
                    //timer.stop();
                }
            }
        };

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), handler);
        Timeline timer = new Timeline(keyFrame);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public void playLevel(int indexOfSelectedLevel){
        executeTimeline(game.getLevels().get(indexOfSelectedLevel));
    }

}