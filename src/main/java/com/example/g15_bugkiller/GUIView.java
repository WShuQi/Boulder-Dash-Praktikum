package com.example.g15_bugkiller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

        Field[][] fields = level.getLevelMap();
        double startX = 500 - 0.5 * BLOCK_SIZE * fields.length;
        double startY = 500 - 0.5 * BLOCK_SIZE * fields[1].length;

        this.gc.clearRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);


        /**Graphische Grundlage für den Counter
        ImageView counterBackground = new ImageView("Counter_Vorlage.png");

         Image counterBackground = new Image(getClass().getResourceAsStream("Counter_Vorlage.png"));
         counterBackground.getHeight();
         counterBackground.getWidth();

        counterBackground.fitHeightProperty();
        counterBackground.fitWidthProperty();
         **/

        schwarzeLeiste();

        drawGemCounter(level.getCollectedGems());

        drawTimePassed(level.getTicksPast());

        drawLives(level.getCurrentLives(), level.getLives());

        for(int zeile = 0; zeile < fields.length; zeile++){
            for(int spalte = 0; spalte < fields[zeile].length; spalte++) {
                Field field = fields[zeile][spalte];

                double y = BLOCK_SIZE * spalte + startY - START_FIELD_Y;
                double x = BLOCK_SIZE * zeile + startX;

                Image image = PictureRepo.getImage(field.getType().name());
                gc.drawImage(image, x, y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    private void drawGemCounter (int gemCounter)  {

        Image counterBackground;
        counterBackground = PictureRepo.getImage("GemCounter_Background_large");
        gc.drawImage(counterBackground, 440.0D, 0.0D, 120, 40);
        gc.setFill(Color.WHITE);
        gc.fillText("GEMS: " + gemCounter, 473.0D, 25.0D);

    }

    private void drawTimePassed (int ticksCounter) {
       gc.setFill(Color.WHITE);
        gc.fillText("Zeit:  " + ticksCounter, 389, 25.D);

    }

    private void drawLives (int livesLeft, int lives) {
        Image heart = PictureRepo.getImage("HEART");
        int xKoord = 650;
        gc.setFill(Color.WHITE);
        gc.fillText("Leben übrig: ", 570, 25.0D);
        for (int i = 1; i <= livesLeft; i ++) {
            gc.drawImage(heart, xKoord, 4.0D);
            xKoord += 25;
        }
    }
    private void schwarzeLeiste(){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0 , 1000, 40);

    }

    private void graueLeiste(){
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0 , 1000, 40);

    }

    public List<LevelButtonSelector> drawLevelOverview(Map<String, Level> levels) {
        this.gc.clearRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
        graueLeiste();

        int unlockedLevels = 0;
        int gesamtePunkte = 0;
        int gesamteLevel = 0;

        for(Level level: levels.values()){
            if (level.isUnlocked()) {
                unlockedLevels++;
            }
            gesamtePunkte += level.getScoredPoints();
            gesamteLevel++;
        }

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial",14));
        gc.fillText("freigeschaltene Level:  " + unlockedLevels + "  /  " + gesamteLevel +
                "    gesammelte Punkte: " + gesamtePunkte , 345.0d, 25.0d);

        double x = 110.0d;
        double y = 80.0d;

        boolean odd = true;
        List<LevelButtonSelector> result = new ArrayList<>();
        for (final String levelName : levels.keySet()) {
            final Level level = levels.get(levelName);
            LevelButtonSelector selector = drawLevelSelection(levelName, level, x, y);
            if (odd) {
                x = 600;
            }
            else {
                x = 110.0d;
                y += 110;
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
        gc.fillRect(startX , startY-20, 295, 30);

        gc.setFill(Color.WHITE);
        gc.fillText(level.getLevelName(), startX + 10, startY);

        gc.setFill(Color.DARKGREY);
        gc.fillText("Edelsteine: " + level.getCollectedGems() + "    " + "Bestzeit: " + level.getTicksPast()
                + "    " + "erreichte Punkte: "+ level.getScoredPoints(), startX , startY + 30);

        if (level.isUnlocked()||!level.isUnlocked()) {
            gc.setFill(Color.DARKGRAY);
            double x = startX + 110;
            double y = startY + 40;
            int w = 80;
            int h = 20;
            // gc.fillRect(x, y, w, h);
            gc.fillOval(x, y, w, h);
            gc.setFill(Color.WHITE);
            gc.fillText("Play", startX + 138, startY + 54);
            gc.fillText("     ", startX, startY + 50);

            return new LevelButtonSelector(levelName, x, y, w, h);
        }
        return null;
    }

}
