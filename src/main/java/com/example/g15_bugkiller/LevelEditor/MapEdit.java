package com.example.g15_bugkiller.LevelEditor;

import MapGeneration.*;
import com.example.g15_bugkiller.Field;
import com.example.g15_bugkiller.Gegenstand;
import com.example.g15_bugkiller.Type;
import com.example.g15_bugkiller.Values;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

public class MapEdit {

    private static int width;
    private static int height;
    private static ArrayList<ArrayList<WritableFieldKachel>> writableKachels;
    private static GridPane mapEditGridPane;

    public static GridPane getMapEditGridPane(int mapWidth, int mapHeight){
        mapEditGridPane = new GridPane();
        writableKachels = new ArrayList<ArrayList<WritableFieldKachel>>();

        width = mapWidth;
        height = mapHeight;

        //create the empty map
        for (int w = 0; w < height; w++) {
            addRow();
        }

        drawGrid();

        return mapEditGridPane;
    }

    public static Input getInput(){
        TileVersion tileVersion = new TileVersion(turnKachelsToFields());
        List<TileVersion> tileVersionList = List.of(tileVersion);

        Tile tile = new Tile("main", tileVersionList);
        List<Tile> tileList = List.of(tile);

        TilesAt tilesAt = new TilesAt(new Coordinate(0, 0), "main", 0);
        List<TilesAt> tilesAtList = List.of(tilesAt);

        List<ConnectBy> emptyConnectByList = List.of();

        Coordinate mapSize = new Coordinate(width, height);

        Field defaultField = new Field(new Gegenstand(Type.PATH, new Values()));

        Input input = new Input(tileList, tilesAtList, emptyConnectByList, defaultField, mapSize);

        return input;
    }

    private static List<List<Field>> turnKachelsToFields(){
        List<List<Field>> fieldArray = new ArrayList<>();

        for (int h = 0; h < height; h++) {
            List<Field> fieldRow = new ArrayList<>();

            for (int w = 0; w < width; w++) {
                fieldRow.add(writableKachels.get(h).get(w).getField());
            }

            fieldArray.add(fieldRow);
        }

        return fieldArray;

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