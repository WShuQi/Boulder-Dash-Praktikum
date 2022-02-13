package com.example.g15_bugkiller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.g15_bugkiller.GUIApplication.SCREEN_HEIGHT;
import static com.example.g15_bugkiller.GUIApplication.SCREEN_WIDTH;

public class GUIView {

    public final int BLOCK_SIZE = 32;

    public final int START_FIELD_Y = 30;

    private GraphicsContext gc;

    public GUIView(GraphicsContext gc) {
        this.gc = gc;
    }

    public void drawLevel(Level level) {
        this.gc.clearRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);

        Field[][] fields = level.getLevelMap();

        /**Graphische Grundlage für den Counter
        ImageView counterBackground = new ImageView("Counter_Vorlage.png");

         Image counterBackground = new Image(getClass().getResourceAsStream("Counter_Vorlage.png"));
         counterBackground.getHeight();
         counterBackground.getWidth();

        counterBackground.fitHeightProperty();
        counterBackground.fitWidthProperty();
         **/

        drawGemCounter(level.getCollectedGems());

        drawTimePassed(level.getTicksPast());

        for(int zeile = 0; zeile < fields.length; zeile++){
            for(int spalte = 0; spalte < fields[zeile].length; spalte++) {
                Field field = fields[zeile][spalte];

                int y = BLOCK_SIZE * spalte + START_FIELD_Y;
                int x = BLOCK_SIZE * zeile;

                Image image = PictureRepo.getImage(field.getType().name());
                gc.drawImage(image, x, y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    private void drawGemCounter (int gemCounter)  {

        Image counterBackground;
        counterBackground = PictureRepo.getImage("Counter_Vorlage_small.png");
        gc.drawImage(counterBackground, 220.0D, 0.0D, 3*BLOCK_SIZE, BLOCK_SIZE);

        gc.fillText("GEMS: " + gemCounter, 240.0D, 20.0D);
        gc.setFill(Color.YELLOWGREEN);
    }

    private void drawTimePassed (int ticksCounter) {


    }

    public List<LevelButtonSelector> drawLevelOverview(Map<String, Level> levels) {
        this.gc.clearRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);

        double x = 20.0d;
        double y = 80.0d;

        boolean odd = true;
        List<LevelButtonSelector> result = new ArrayList<>();
        for (final String levelName : levels.keySet()) {
            final Level level = levels.get(levelName);
            LevelButtonSelector selector = drawLevelSelection(levelName, level, x, y);
            if (odd) {
                x = 480;
            }
            else {
                x = 20.d;
                y += 80;
            }
            odd = !odd;
            if (selector != null) {
                result.add(selector);
            }
        }
        return result;
    }

    private LevelButtonSelector drawLevelSelection(String levelName, Level level, double startX, double startY) {
        gc.setFill(Color.BLACK);

        gc.strokeText(level.getLevelName(), startX, startY);
        gc.fillText("Edelsteine: " + level.getCollectedGems() + "     " + "Bestzeit: " + level.getTicksPast()
                + "     " + "erreichte Punkte: "+ level.getScoredPoints(), startX , startY + 20);

        if (level.isUnlocked()) {
            gc.setFill(Color.YELLOW);
            double x = startX + 100;
            double y = startY + 30;
            int w = 80;
            int h = 20;
            gc.fillRect(x, y, w, h);

            gc.setFill(Color.BLACK);
            gc.fillText("Play", startX + 125, startY + 45);
            gc.fillText("     ", startX, startY + 50);

            return new LevelButtonSelector(levelName, x, y, w, h);
        }
        return null;
    }

}
