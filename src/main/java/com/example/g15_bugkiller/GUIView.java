package com.example.g15_bugkiller;

import MapGeneration.Coordinate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUIView {

    final double width;
    final double height;

    public final int BLOCK_SIZE = 32;
    public final int BLOCK_SIZE_MINI = 8;
    public final int START_FIELD_Y = 30;

    private GraphicsContext gc;

    public int vorherigeErsteSpalte;
    public int vorherigeErsteZeile;

    public GUIView(GraphicsContext gc, double width, double height) {
        this.gc = gc;
        this.width = width;
        this.height = height;
    }

    public void resetLevelView() {
        vorherigeErsteSpalte = 0;
        vorherigeErsteZeile = 0;
    }

    public void drawLevel(Level level) {

        Field[][] fields = level.getLevelMap();

        double startXMini = 500 - 0.5 * BLOCK_SIZE_MINI * fields.length;
        double startYMini = 125;

        double startX = Math.max(4, 500 - 0.5 * BLOCK_SIZE * fields.length);

        double startYVerschiebung = startYMini+ fields[1].length * BLOCK_SIZE_MINI + 5;
        double startYMittig = 500 - 0.5 * BLOCK_SIZE * fields[1].length;

        double startY = Math.max(startYVerschiebung,startYMittig);
        this.gc.clearRect(0,0, width, height);

        blackTopStripe();

        drawGemCounter(level.getCollectedGems());

        drawTimePassed(level.getTicksPast());

        drawLives(level.getCurrentLives(), level.getLives());

        drawNecessaryAchievements(level.getCollectedGems(), level.getGems(),  level.getTicks(), level.getTicksPast());

        int maxSpalten = fields.length;
        int maxZeilen = fields[0].length;

        int startSpalte = this.vorherigeErsteSpalte;
        int startZeile = this.vorherigeErsteZeile;

        final int maxZeilenToDisplay = Math.min((int) ((height - startY) / BLOCK_SIZE), maxZeilen);
        final int maxSpaltenToDisplay = Math.min((int) (width) / BLOCK_SIZE, maxSpalten);

        int endeZeile = Math.min(startZeile + maxZeilenToDisplay, maxZeilen);
        int endeSpalte = Math.min(startSpalte + maxSpaltenToDisplay, maxSpalten);

        Coordinate mePosition = level.getMEPosition();

        //Scrollen des Levels vertikal
        if (mePosition.getY() >= endeZeile - 3) {
            endeZeile = mePosition.getY() + 4;
            if (endeZeile > maxZeilen) {
                endeZeile = maxZeilen;
            }
            startZeile = Math.max(endeZeile - maxZeilenToDisplay, 0);
        }
        else if (mePosition.getY() < startZeile + 3) {
            startZeile = mePosition.getY() - 3;
            if (startZeile < 0) {
                startZeile = 0;
            }
            endeZeile = Math.min(startZeile + maxZeilenToDisplay, maxZeilen);
        }
        // Scrollen des Levels horizontal
        if (mePosition.getX() >= endeSpalte - 3) {
            endeSpalte = mePosition.getX() + 4;
            if (endeSpalte > maxSpalten) {
                endeSpalte = maxSpalten;
            }
            startSpalte = Math.max(endeSpalte - maxSpaltenToDisplay, 0);
        }
        else if (mePosition.getX() < startSpalte + 3) {
            startSpalte = mePosition.getX() - 3;
            if (startSpalte < 0) {
                startSpalte = 0;
            }
            endeSpalte = Math.min(startSpalte + maxSpaltenToDisplay, maxSpalten);
        }

        //zeichnen des Levels
        for (int spalte = startSpalte; spalte < endeSpalte; spalte++){
            for(int zeile = startZeile; zeile < endeZeile; zeile++) {
                Field field = fields[spalte][zeile];

                double y = BLOCK_SIZE * (zeile - startZeile) + startY - START_FIELD_Y;
                double x = BLOCK_SIZE * (spalte - startSpalte) + startX;

                Image image = PictureRepo.getImage(field.getType().name());
                gc.drawImage(image, x, y, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        this.vorherigeErsteSpalte = startSpalte;
        this.vorherigeErsteZeile = startZeile;

        //Zeichnen der Minimap
        for (int spalte = 0; spalte < fields.length; spalte++){
            for(int zeile = 0; zeile < fields[spalte].length; zeile++) {
                Field field = fields[spalte][zeile];

                double y = BLOCK_SIZE_MINI * zeile + startYMini- START_FIELD_Y;
                double x = BLOCK_SIZE_MINI * spalte + startXMini;

                Image image = PictureRepo.getImage(field.getType().name());
                gc.drawImage(image, x, y, BLOCK_SIZE_MINI, BLOCK_SIZE_MINI);
            }
        }

        drawButtonsGame();
    }

    private void drawButtonsGame() {
        drawButtonCyan("Neustart", 50, 80);
        drawButtonCyan(" Zur??ck", 850, 80);
    }

    private void drawButtonsOverview() {
        drawButtonBlack("Fortschritte speichern", 50, 10);
        drawButtonBlack("   Fortschritte laden", 800, 10);
        drawButtonBlack("Neues Level erstellen", 425, 55);
    }

    private void drawButtonBlack(String text, double x, double y) {
        int w = 150;
        int h = 22;
        gc.setFill(Color.ORANGE);
        gc.fillRect(x+1, y+1, w + 2, h + 2);
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, w, h);
        gc.setFill(Color.WHITE);
        gc.fillText(text, x + 10, y + h - 5);
    }

    private void drawButtonCyan(String text, double x, double y) {
        int w = 80;
        int h = 25;
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(x+1, y+1, w+2, h+2);
        gc.setFill(Color.DARKCYAN);
        gc.fillRect(x, y, w, h);
        gc.setFill(Color.WHITE);
        gc.fillText(text, x + 12, y + h - 8);
    }

    private void drawGemCounter (int gemCounter)  {
        // Hintergrund f??r Sterne, fr??her HG f??r Gems, deshalb Gem Counter
        Image counterBackground;
        counterBackground = PictureRepo.getImage("GemCounter_Background_large");
        gc.drawImage(counterBackground, 385.0D, -18.0D, 230, 100);
        /**gc.setFill(Color.WHITE);
        gc.fillText("GEMS: " + gemCounter, 467.0D, 25.0D);
         **/

    }

    private void drawTimePassed (int ticksCounter) {
        gc.setFill(Color.WHITE);
        gc.fillText("" + ticksCounter, 350, 40.D);

    }


    //Lebensanzeige (nur f??r 3 oder weniger sinnvoll, durch x Verschiebung)
    private void drawLives (int livesLeft, int lives) {
        Image heart = PictureRepo.getImage("HEART");
        int xKoordLives = 850;
        /**gc.setFill(Color.WHITE);
        gc.fillText("Leben ??brig: ", 590, 25.0D);
         **/
        for (int i = 1; i <= livesLeft; i ++) {
            gc.drawImage(heart, xKoordLives, 5D, 55,55);
            xKoordLives += 40;
        }
    }
    private void drawNecessaryAchievements (int getCollectedGems, int[] gems, int[] ticks, int getTicksPast) {

        //Anzeige verschiedener Achievements im Level
        Image gem = PictureRepo.getImage("GEM");
        Image check = PictureRepo.getImage("PASSED");
        Image noCheck = PictureRepo.getImage("NOTPASSED");
        Image clockTimer = PictureRepo.getImage("CLOCK_TIMER");
        Image star = PictureRepo.getImage("STAR");
        int xKoordClock = 680;
        int xKoordGems = 645;

        //GEM Anzeige
        gc.setFill(Color.WHITE);
        if (getCollectedGems < gems[0]) {
            gc.fillText(" ", 760, 40.0D);
            gc.fillText(getCollectedGems + "/" + gems[0], xKoordGems, 40.0D);
        }
        else if (getCollectedGems <= gems[1]) {
            gc.fillText(" ", 760, 40.0D);
            gc.fillText(getCollectedGems + "/" + gems[1], xKoordGems, 40.0D);
        }
        else if (getCollectedGems <= gems[2]) {
            gc.fillText(" ", 760, 40.0D);
            gc.fillText(getCollectedGems + "/" + gems[2], xKoordGems, 40.0D);
        }
        else if (getCollectedGems > gems[2]) {
            gc.fillText(" ", 760, 40.0D);
            gc.fillText("" +getCollectedGems, xKoordGems +15, 40.0D);
        }

        gc.drawImage(gem, xKoordClock, 18.0D);

        //Level bestanden Anzeige
        gc.fillText("Level bestanden: ", 20, 40.0D);

        if (getCollectedGems >= gems[0]) {
            gc.drawImage(check, 130, 18.0D);
        }
        else {
            gc.drawImage(noCheck, 130, 18.0D);
        }

        //Zeit- und Sternanzeige
        gc.drawImage(clockTimer, 300, 15D);

        if (ticks[2] > getTicksPast) {
            gc.drawImage(star, 445, 15.0D);
            gc.drawImage(star, 485, 15.0D);
            gc.drawImage(star, 525, 15.0D);
        }
        else if (ticks[1] > getTicksPast && ticks[2] <= getTicksPast){
            gc.drawImage(star, 465, 15.0D);
            gc.drawImage(star, 505, 15.0D);
        }
        else if (ticks[0] >= getTicksPast && ticks[1] <= getTicksPast) {
            gc.fillText("Zeit ??brig: " + ticks[0], 850, 90.D);
            gc.drawImage(star, 485, 15.0D);
        }
    }

    private void blackTopStripe(){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0 , 1000, 65);
    }

    private void cyanTopStripe(){
        gc.setFill(Color.DARKCYAN);
        gc.fillRect(0, 0 , 1000, 40);
    }

    public List<LevelButtonSelector> drawLevelOverview(Map<String, Level> levels, double startY) {
        this.gc.clearRect(0,0, width, height);
        //StartY vom Scrollen abh??ngig
        double x = 110.0d;
        double y = startY;

        boolean odd = true;
        //Aufrufen der Methode zum Anzeigen der Levels und Platzierung
        List<LevelButtonSelector> result = new ArrayList<>();
        for (final String levelName : levels.keySet()) {
            final Level level = levels.get(levelName);
            LevelButtonSelector selector = drawLevelSelection(levelName, level, x, y);
            if (odd) {
                x = 540;
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

        cyanTopStripe();

        int unlockedLevels = 0;
        int gesamtePunkte = 0;
        int gesamteLevel = 0;

        //Berechnung der Gesamtpunktzahl und freigeschalteten Levels

        for(Level level: levels.values()){
            if (level.isUnlocked()) {
                unlockedLevels++;
            }
            if (level.isPassed() || level.getBestScore() > 0) {
                gesamtePunkte += level.getBestScore();
            }
            gesamteLevel++;
        }

        //Anzeige der gesamten Punkte und wie viele Levels freigeschaltet sind
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial",14));
        gc.fillText("Freigeschaltene Level:  " + unlockedLevels + "  /  " + gesamteLevel +
                "    Gesamte Punkte: " + gesamtePunkte , 345.0d, 25.0d);

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 40 , 1000, 50);
        drawButtonsOverview();

        return result;
    }

    private LevelButtonSelector drawLevelSelection(String levelName, Level level, double startX, double startY) {

        //Levelanzeige
        gc.setFill(Color.DARKCYAN);
        gc.fillRect(startX+1, startY-19, 346, 31);

        gc.setFill(Color.BLACK);
        gc.fillRect(startX , startY-20, 345, 30);

        gc.setFill(Color.WHITE);
        gc.fillText(level.getLevelName(), startX + 10, startY);

        gc.setFill(Color.BLACK);

        // Anzeige der Leveldaten bzw. Achievements
        if(level.isPassed() || level.getBestScore() > 0) {
            gc.fillText("Edelsteine: " + level.getBestGems() + "    " + "Bestzeit: " + level.getBestTime()
                    + "    " + "erreichte Punkte: " + level.getBestScore(), startX, startY + 30);
        } else {
            gc.fillText("Edelsteine: " + "-" + "    " + "Bestzeit: " + "-"
                    + "    " + "erreichte Punkte: " + "-", startX, startY + 30);
        }
        //Anzeige des Replay Buttons nach Bestehen des Levels
        if (level.isUnlocked()) {
            return createLevelButton(levelName, startX, startY,
                    level.getReplaySaveData() != null && level.getReplaySaveData().size() > 0);
        }
        return null;
    }

    private LevelButtonSelector createLevelButton(String levelName, double startX, double startY, boolean drawReplay) {

        double x = startX + 40;
        double y = startY + 40;
        int w = 80;
        int h = 25;

        //Play Button
        gc.setFill(Color.LIGHTGRAY);
        gc.fillOval(x+1, y+1, w+2, h+2);

        gc.setFill(Color.DARKCYAN);
        gc.fillOval(x, y, w, h);

        gc.setFill(Color.BLACK);
        gc.strokeOval(x,y,w+0.5D,h+0.5D);

        gc.setFill(Color.WHITE);
        gc.fillText("Play", x + 28, y + 17);

        //Replay Button
        if (drawReplay) {
            double rx = x + w + LevelButtonSelector.DIFF_BUTTONS;

            gc.setFill(Color.LIGHTGRAY);
            gc.fillOval(rx+1, y+1, w+2, h+2);

            gc.setFill(Color.DARKCYAN);
            gc.fillOval(rx, y, w, h);

            gc.setFill(Color.BLACK);
            gc.strokeOval(rx,y,w+0.5D,h+0.5D);

            gc.setFill(Color.WHITE);
            gc.fillText("Replay", rx + 19, y + 17);
        }

        return new LevelButtonSelector(levelName, x, y, w, h);
    }

    //Game Over Schriftzug
    public void drawGameOver() {
        gc.setFill(Color.RED);
        gc.setTextAlign(TextAlignment.CENTER);
        Font font = gc.getFont();
        double orgSize = font.getSize();
        gc.setFont(new Font(font.getName(), 50));
        gc.fillText("Game Over", 500, 500);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(new Font(font.getName(), orgSize));
    }
}
