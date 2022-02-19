package com.example.g15_bugkiller;

import MapGeneration.Coordinate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUIView {

    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 1000;

    public final int BLOCK_SIZE = 32;
    public final int BLOCK_SIZE_MINI = 8;
    public final int START_FIELD_Y = 30;

    private GraphicsContext gc;

    public int lastX;
    public int lastY;

    public GUIView(GraphicsContext gc) {
        this.gc = gc;
    }

    public void resetLevelView() {
        lastX = 0;
        lastY = 0;
    }

    public void drawLevel(Level level) {

        Field[][] fields = level.getLevelMap();

        double startXMini = 500 - 0.5 * BLOCK_SIZE_MINI * fields.length;
        double startYMini = 75;

        double startX = Math.max(4, 500 - 0.5 * BLOCK_SIZE * fields.length);

        double startYVerschiebung = startYMini+ fields[1].length * BLOCK_SIZE_MINI + 5;
        double startYMittig = 500 - 0.5 * BLOCK_SIZE * fields[1].length;

        double startY = Math.max(startYVerschiebung,startYMittig);
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

        int maxSpalten = fields.length;
        int maxZeilen = fields[0].length;

        int startSpalte = 0;
        int startZeile = 0;

        final int maxZeilenToDisplay = Math.min((int) ((GUIView.SCREEN_HEIGHT - startY - 140) / BLOCK_SIZE), maxZeilen);
        final int maxSpaltenToDisplay = Math.min((GUIView.SCREEN_WIDTH) / BLOCK_SIZE, maxSpalten);

        int endeZeile = maxZeilenToDisplay;
        int endeSpalte = maxSpaltenToDisplay;

        Coordinate mePosition = level.getMEPosition();

        if (mePosition.getY() >= endeZeile - 3) {
            endeZeile = mePosition.getY() + 4;
            if (endeZeile > maxZeilen) {
                endeZeile = maxZeilen;
            }
            startZeile = Math.max(endeZeile - maxZeilenToDisplay, 0);
        }

        if (mePosition.getX() >= endeSpalte - 3) {
            endeSpalte = mePosition.getX() + 4;
            if (endeSpalte > maxSpalten) {
                endeSpalte = maxSpalten;
            }
            startSpalte = Math.max(endeSpalte - maxSpaltenToDisplay, 0);
        }

        for (int spalte = startSpalte; spalte < endeSpalte; spalte++){
            for(int zeile = startZeile; zeile < endeZeile; zeile++) {
                Field field = fields[spalte][zeile];

                double y = BLOCK_SIZE * (zeile - startZeile) + startY - START_FIELD_Y;
                double x = BLOCK_SIZE * (spalte - startSpalte) + startX;

                Image image = PictureRepo.getImage(field.getType().name());
                gc.drawImage(image, x, y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        for (int spalte = 0; spalte < fields.length; spalte++){
            for(int zeile = 0; zeile < fields[spalte].length; zeile++) {
                Field field = fields[spalte][zeile];

                double y = BLOCK_SIZE_MINI * zeile + startYMini- START_FIELD_Y;
                double x = BLOCK_SIZE_MINI * spalte + startXMini;

                Image image = PictureRepo.getImage(field.getType().name());
                gc.drawImage(image, x, y, BLOCK_SIZE_MINI, BLOCK_SIZE_MINI);
            }
        }
    }

    private void drawGemCounter (int gemCounter)  {

        Image counterBackground;
        counterBackground = PictureRepo.getImage("GemCounter_Background_large");
        gc.drawImage(counterBackground, 440.0D, 0.0D, 120, 40);
        gc.setFill(Color.WHITE);
        gc.fillText("GEMS: " + gemCounter, 467.0D, 25.0D);

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
        gc.fillText("Edelsteine: " + level.getBestGems() + "    " + "Bestzeit: " + level.getBestTime()
                + "    " + "erreichte Punkte: "+ level.getBestScore(), startX , startY + 30);

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
