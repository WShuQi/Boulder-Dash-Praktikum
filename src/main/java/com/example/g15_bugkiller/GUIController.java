package com.example.g15_bugkiller;

// import com.example.g15_bugkiller.GameReplay.GameReplay;
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
        if (this.levelButtonSelectorList == null) {
            return;
        }
        for (LevelButtonSelector selector : this.levelButtonSelectorList) {
            if (selector.contains(x, y)) {
                playLevel(selector.getLevelName());
                return;
            }
        }
    }

    public void executeTimeline(Level level){

        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){
                KeyPressListener currentKeysPressed = keyPressListener.getClone();
                LevelLogic.tick(level, currentKeysPressed); // TODO rule Ausführung führt nur zu Explosionen
                TerminalMap.drawMap(level.getLevelMap());
                // GameReplay.saveMapFrame(level.getLevelMap());
                updateView(level);

                if(level.isTimeUp() | level.isExitReached() | level.isPlayerDead()){
                    LevelLogic.resetLevel(level);

                    game.updateTotalPoints();
                    game.unlockNextLevelAsNecessary();
                    timer.stop();
                    timer.getKeyFrames().clear();
                    timer = null;
                    returnToOverview();
                    //just for testing
                    // GameReplay.openReplayWindow();
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
        Level selectedLevel = game.getLevels().get(selectedLevelName);
        executeTimeline(selectedLevel);
    }
    public void returnToOverview (){
        this.levelButtonSelectorList = view.drawLevelOverview(game.levels);
    }

}