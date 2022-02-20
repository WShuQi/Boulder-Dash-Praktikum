package com.example.g15_bugkiller.LevelEditor;

import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class MapEdit {

    private static int width;
    private static int height;
    private static ArrayList<ArrayList<WritableFieldKachel>> writableKachels = new ArrayList<ArrayList<WritableFieldKachel>>();
    private static GridPane mapEditGridPane;

    public static GridPane getMapEditGridPane(int mapWidth, int mapHeight){
        mapEditGridPane = new GridPane();

        width = mapWidth;
        height = mapHeight;

        //create the empty map
        for (int w = 0; w < height; w++) {
            addRow();
        }

        drawGrid();

        return mapEditGridPane;
    }

    public static void resetMap(){
        writableKachels = new ArrayList<ArrayList<WritableFieldKachel>>();

        //create the empty map
        for (int w = 0; w < height; w++) {
            addRow();
        }

        drawGrid();
    }
    
    private static void addRow(){
        ArrayList<WritableFieldKachel> newRow = new ArrayList<WritableFieldKachel>();
        for (int i = 0; i < width; i++) {
            newRow.add(new WritableFieldKachel());
        }

        writableKachels.add(newRow);
    }

    private static void removeRow(){
        writableKachels.remove(writableKachels.size() - 1);
    }

    private static void addColumn(){
        for (int i = 0; i < height; i++) {
            writableKachels.get(i).add(new WritableFieldKachel());
        }
    }

    private static void removeColumn(){
        for (int i = 0; i < writableKachels.size(); i++) {
            writableKachels.get(i).remove(writableKachels.get(i).size() - 1);
        }
    }

    public static void updateSize(int newWidth, int newHeight){

        if(width > newWidth){
            removeColumn();
        }else if(width < newWidth){
            addColumn();
        }

        if(height > newHeight){
            removeRow();
        }else if(height < newHeight){
            addRow();
        }

        width = newWidth;
        height = newHeight;

        drawGrid();
    }

    private static void drawGrid (){
        mapEditGridPane.getChildren().clear();

        for (int h = 0; h < height; h++) {

            for (int w = 0; w < width; w++) {
                mapEditGridPane.add(writableKachels.get(h).get(w), w, h);
            }
        }
    }
}