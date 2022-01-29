package com.example.g15_bugkiller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

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


        TextField gemCounterText = new TextField();




    }

    private void drawTimePassed (int ticksCounter) {


    }



}
