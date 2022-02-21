package com.example.g15_bugkiller;

// import com.example.g15_bugkiller.GameReplay.GameReplay;
import com.example.g15_bugkiller.GameReplay.GameReplay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.List;

public class GUIController {

    private GUIView view;
    private Game game;
    private KeyPressListener keyPressListener;
    private Timeline timer;

    private List<LevelButtonSelector> levelButtonSelectorList;

    private boolean levelInProgress;
    private boolean restartLevel;

    public GUIController(GUIView view, Game game, KeyPressListener keyPressListener) {
        this.view = view;
        this.game = game;
        this.keyPressListener = keyPressListener;

        this.levelButtonSelectorList = view.drawLevelOverview(game.levels);
    }

    private void updateView(Level level) {
        view.drawLevel(level);
    }       //Todo: Game(davon List<Level>)anpassen

    public void mousePressed(double x, double y) {
        if (this.levelButtonSelectorList != null) {
            for (LevelButtonSelector selector : this.levelButtonSelectorList) {
                if (selector.onPlayButton(x, y)) {
                    playLevel(selector.getLevelName());
                    return;
                }
                else if (selector.onReplayButton(x, y)) {
                    Level level = game.getLevels().get(selector.getLevelName());
                    if (level.getReplaySaveData() != null && level.getReplaySaveData().size() > 0) {
                        new GameReplay(600, 600).openReplayWindow(level.getReplaySaveData());
                    }
                }
            }
        }
        else {
            // wir spielen und brauchen die anderen buttons
            if (y >= 80 && y <= 100) {
                if (x >= 50 && x <= 130) {
                    // Neustart
                    restartLevel = true;
                }
                else if (x >= 850 && x <= 930) {
                    // Zurück
                    levelInProgress = false;
                }
            }
        }
    }

    public void executeTimeline(Level level){

        view.resetLevelView();
        levelInProgress = true;

        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                if (restartLevel) {
                    LevelLogic.resetLevel(level);
                    GameReplay.clearSavedMap();
                    restartLevel = false;
                }

                KeyPressListener currentKeysPressed = keyPressListener.getClone();
                LevelLogic.tick(level, currentKeysPressed);
                GameReplay.saveMapFrame(level.getLevelMap());
                updateView(level);

                if(!levelInProgress || level.isTimeUp() || level.isExitReached() || level.isPlayerDead()) {
                    level.setReplaySaveData(GameReplay.getSavedMapData());

                    if(level.isPassed()){
                        LevelLogic.updateBestValues();
                    }

                    LevelLogic.resetLevel(level);

                    game.updateTotalPoints();
                    game.unlockNextLevelAsNecessary();
                    timer.stop();
                    timer.getKeyFrames().clear();
                    timer = null;
                    returnToOverview();
                }
            }
        };

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), handler);
        timer = new Timeline(keyFrame);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public void playLevel(String selectedLevelName){
        this.levelButtonSelectorList = null;
        GameReplay.clearSavedMap();
        Level selectedLevel = game.getLevels().get(selectedLevelName);

        if(selectedLevel.isUnlocked()){
            executeTimeline(selectedLevel);
        }

    }
    public void returnToOverview (){
        this.levelButtonSelectorList = view.drawLevelOverview(game.levels);
    }

}