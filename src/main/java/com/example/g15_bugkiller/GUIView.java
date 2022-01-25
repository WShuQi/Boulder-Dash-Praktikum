package com.example.g15_bugkiller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import static com.example.g15_bugkiller.HelloApplication.SCREEN_HEIGHT;
import static com.example.g15_bugkiller.HelloApplication.SCREEN_WIDTH;

public class GUIView {

    public final int BLOCK_SIZE = 30;

    private GraphicsContext gc;

    public GUIView(GraphicsContext gc) {
        this.gc = gc;
    }

    public void drawLevel(Level level) {
        this.gc.clearRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);

        Field[][] fields = level.getLevelMap();


        for(int zeile = 0; zeile < fields.length; zeile++){
            for(int spalte = 0; spalte < fields[zeile].length; spalte++) {
                Field field = fields[zeile][spalte];
                int y = BLOCK_SIZE * spalte;
                int x = BLOCK_SIZE * zeile;

                switch (field.getType()) {
                    case WALL:
                        drawRectangle(Color.DARKGRAY, x, y);
                        break;
                    case MUD:
                        drawRectangle(Color.SADDLEBROWN, x, y);
                        break;
                    case BRICKS:
                        drawRectangle(Color.BLUEVIOLET, x, y);
                        break;
                    case STONE:
                        drawCircle(Color.GRAY, x, y);
                        break;
                    case ME:
                        drawRectangle(Color.GREEN, x, y);
                        break;
                    case GEM:
                        drawSmallCircle(Color.PINK, x, y);
                        break;
                    case PATH:
                        drawRectangle(Color.BLACK, x, y);
                        break;
                    case EXIT:
                        drawRectangle(Color.YELLOW, x, y);
                        break;
                    default:
                        drawRectangle(Color.WHITE, x, y);
                }

            }
        }

    }

    private void drawRectangle(final Paint color, final int x, final int y) {
        gc.setFill(color);
        gc.fillPolygon(new double[] {x, x + BLOCK_SIZE, x + BLOCK_SIZE, x},
                new double[] {y, y, y + BLOCK_SIZE, y + BLOCK_SIZE}, 4);
    }

    private void drawCircle(final Paint color, final int x, final int y) {
        gc.setFill(color);
        gc.fillOval(x, y, BLOCK_SIZE, BLOCK_SIZE );
    }
    private void drawSmallCircle(final Paint color, final int x, final int y) {
        gc.setFill(color);
        gc.fillOval(x + 2.5, y + 2.5, BLOCK_SIZE - 5, BLOCK_SIZE - 5);
    }
    private void drawTriangle(final Paint color, final int x, final int y) {
        gc.setFill(color);
        gc.fillPolygon(new double[] {x, x + BLOCK_SIZE, x + BLOCK_SIZE, x},
                new double[] {y, y, y + BLOCK_SIZE, y + BLOCK_SIZE}, 3);
    }

}
