package com.example.g15_bugkiller;

// import com.example.g15_bugkiller.GameReplay.GameReplay;
import MapGeneration.Gamesaver;
import com.example.g15_bugkiller.GameReplay.GameReplay;
import javafx.animation.KeyFrame;
import com.example.g15_bugkiller.LevelEditor.LevelEditor;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GUIController {

    private GUIView view;
    private Game game;
    private Timeline timer;
    private final KeyPressListener keyPressListener;

    private List<LevelButtonSelector> levelButtonSelectorList;

    private boolean levelInProgress = false;
    private boolean restartLevel;
    private double currentStartY = 120;

    public GUIController(GUIView view, Game game, KeyPressListener keyPressListener) {
        this.view = view;
        this.game = game;

        this.keyPressListener = keyPressListener;

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
        // Scroll berechnet StartY von drawLevelOverview neu -> Scroll Effekt
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
        //Buttons in der Levelübersicht
        if (this.levelButtonSelectorList != null) {
            // Play und Replay Buttons von den verschiedenen Leveln
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
                    // Fortschritte speichern Button
                    Gamesaver gamesaver = new Gamesaver("src/main/java/com/example/g15_bugkiller/SavedGames");
                    gamesaver.getGameData(game);
                    TextInputDialog dialog = new TextInputDialog("player01");
                    dialog.setHeaderText("Für wen sollen die Fortschritte gespeichert werden? ");
                    dialog.setContentText("Bitte Name eingeben: ");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        gamesaver.createJson(result.get());
                    }
                }
                else if (x >= 800 && x <= 950) {
                    //Fortschritte laden Button
                    Gamesaver gamesaver = new Gamesaver("src/main/java/com/example/g15_bugkiller/SavedGames");
                    gamesaver.readGameData(game);
                    this.updateOverview();
                }
            }

            if (y >= 55 && y <= 75) {
                if (x >= 425 && x <= 575)  {
                    //neues Level erstellen Button
                    LevelEditor.openLevelEditor();
                }
            }
        }
        else {
            // Buttons im Spiel
            if (y >= 80 && y <= 100) {
                if (x >= 50 && x <= 130) {
                    // Neustart Button
                    restartLevel = true;
                }
                else if (x >= 850 && x <= 930) {
                    // Zurück Button
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
                    // wenn restart Button gedrückt wurde
                    LevelLogic.resetLevel(level);
                    GameReplay.clearSavedMap();
                    restartLevel = false;
                }

                if (!(level.isPlayerDead() ||  level.isTimeUp())) {
                    //Spielablauf
                    KeyPressListener currentKeysPressed = keyPressListener.getClone();
                    LevelLogic.tick(level, currentKeysPressed);
                    GameReplay.saveMapFrame(level.getLevelMap());
                    updateView(level);
                }
                else {
                    // Spiel friert ein, Game Over erscheint
                    gameOver();
                }

                if(!levelInProgress || level.isExitReached()) {
                    // Level erfolgreich absolviert
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