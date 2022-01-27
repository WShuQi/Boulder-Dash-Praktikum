package com.example.g15_bugkiller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static com.example.g15_bugkiller.GUIApplication.SCREEN_HEIGHT;
import static com.example.g15_bugkiller.GUIApplication.SCREEN_WIDTH;

public class GUIView {

    public final int BLOCK_SIZE = 32;

    public final int START_FIELD_Y = 30;

    public int gemCounter;

    public int ticksCounter;

    private GraphicsContext gc;

    public GUIView(GraphicsContext gc) {
        this.gc = gc;
    }

    public void drawLevel(Level level) {
        this.gc.clearRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);

        Field[][] fields = level.getLevelMap();

        /**Graphische Grundlage für den Counter
        //Image counterBackground = new Image("Counter_Vorlage.png");
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

        gc.fillText("GEMS: " + gemCounter, 240.0D, 20.0D);

    }

    private void drawTimePassed (int ticksCounter) {


    }



}
