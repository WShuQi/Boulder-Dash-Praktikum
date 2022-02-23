package com.example.g15_bugkiller;

// import com.example.g15_bugkiller.GameReplay.GameReplay;
import MapGeneration.Gamesaver;
import com.example.g15_bugkiller.GameReplay.GameReplay;
import javafx.animation.KeyFrame;
import com.example.g15_bugkiller.LevelEditor.LevelEditor;
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
    private MouseScrollListener mouseScrollListener;

    private List<LevelButtonSelector> levelButtonSelectorList;

    private boolean levelInProgress;
    private boolean restartLevel;
    private double currentStartY = 120;

    public GUIController(GUIView view, Game game, KeyPressListener keyPressListener, MouseScrollListener mouseScrollListener) {
        this.view = view;
        this.game = game;

        this.keyPressListener = keyPressListener;
        this.mouseScrollListener = mouseScrollListener;

        this.levelButtonSelectorList = view.drawLevelOverview(game.levels, this.currentStartY);
    }

    private void updateView(Level level) {
        view.drawLevel(level);
    }

    private void gameOver() {
        view.drawGameOver();
    }

    private void updateOverview() {
        this.levelButtonSelectorList = view.drawLevelOverview(game.levels, this.currentStartY);
    }

    public void mouseScrolled (double deltaY) {
        if (this.levelButtonSelectorList != null) {

            if (deltaY > 0 && currentStartY <= 120 && (currentStartY - deltaY) >= -880) {
                currentStartY -= deltaY;
                System.out.println("Scroll: " + deltaY);
            }
            else if (deltaY < 0 && currentStartY >= -880 && (currentStartY - deltaY) <= 120) {
                currentStartY -= deltaY;
                System.out.println("Scroll2: " + deltaY);
            }
            System.out.println("currentStartY: " + currentStartY);
            updateOverview();
        }
    }

    public void mousePressed(double x, double y) {
        if (this.levelButtonSelectorList != null) {

            if (y > 100) {
                for (LevelButtonSelector selector : this.levelButtonSelectorList) {
                    if (selector.onPlayButton(x, y)) {
                        playLevel(selector.getLevelName());
                        return;
                    } else if (selector.onReplayButton(x, y)) {
                        Level level = game.getLevels().get(selector.getLevelName());
                        if (level.getReplaySaveData() != null && level.getReplaySaveData().size() > 0) {
                            new GameReplay(600, 600).openReplayWindow(level.getReplaySaveData());
                        }
                    }
                }
            }

            if (y >= 10 && y <= 30) {
                if (x >= 50 && x <= 200) {
                    //TODO: SHUQI hier ist der Fortschritte speichern Button
                    Gamesaver gamesaver = new Gamesaver("src/main/java/com/example/g15_bugkiller/SavedGames");
                    gamesaver.getGameData(game);
                    gamesaver.createJson("01");
                    System.out.println("Fortschritte speichern"); //nur zur Überprüfung
                }
                else if (x >= 800 && x <= 950) {
                    //TODO: SHUQI hier ist der Fortschritte laden Button
                    Gamesaver gamesaver = new Gamesaver("src/main/java/com/example/g15_bugkiller/SavedGames/01.json");
                    gamesaver.readGameData(game);
                    System.out.println("Fortschritte laden"); //nur zur Überprüfung
                    System.out.println(game.getLevels().get("Die wachsende Wand").getBestScore());
                }
            }

            if (y >= 55 && y <= 75) {
                if (x >= 425 && x <= 575)  {
                    LevelEditor.openLevelEditor();
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

                if (!(level.isPlayerDead() ||  level.isTimeUp())) {
                    KeyPressListener currentKeysPressed = keyPressListener.getClone();
                    LevelLogic.tick(level, currentKeysPressed);
                    GameReplay.saveMapFrame(level.getLevelMap());
                    updateView(level);
                }
                else {
                    gameOver();
                }

                if(!levelInProgress || level.isExitReached()) {
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
        this.levelButtonSelectorList = view.drawLevelOverview(game.levels, currentStartY);
    }

}