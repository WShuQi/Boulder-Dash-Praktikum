package MapGeneration;

import MapGeneration.*;
import com.example.g15_bugkiller.Field;

import java.util.ArrayList;
import java.util.List;

public class Input {

    private List<Tile> tiles = new ArrayList<Tile>();
    private List<TilesAt> tilesAts = new ArrayList<TilesAt>();
    private List<ConnectBy> connectBys = new ArrayList<ConnectBy>();
    private static Field defaultField;
    private Coordinate coordinateOffset; // Zur Umrechnung von negativen Indizes
    private Coordinate mapSize;  //speichert die Größe der tatsächlich gespeicherten endlichen Karte, auf der Kacheln liegen

    public Input(List<Tile> tiles, List<TilesAt> tilesAts, List<ConnectBy> connectBys, Field defaultField, Coordinate mapSize) {
        this.tiles = tiles;
        this.tilesAts = tilesAts;
        this.connectBys = connectBys;
        this.defaultField = defaultField;
        this.coordinateOffset = getCoordinateOffset();
        this.mapSize = mapSize;
        applyCoordinateOffsetToConnectBy();
        applyCoordinateOffsetToTilesAt();
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<TilesAt> getTilesAts() {
        return tilesAts;
    }

    public List<ConnectBy> getConnectBys() {
        return connectBys;
    }

    public Field getDefaultField() {
        return defaultField;
    }

    public Coordinate getMapSize() {
        return mapSize;
    }


    public Tile getTileWithName (String kindName) {
        Tile tileWithName = new Tile("notFound", new ArrayList<TileVersion>());
        for (Tile tile : tiles) {
            if (kindName.equals(tile.getName())) {
                tileWithName = tile;
            }
        }
        if (tileWithName.getName() == "notFound"){
            System.out.println("Tile mit Name " + kindName + " nicht vorhanden");
        }
        return tileWithName;
    }

    // solves Umrechnung von negativen Indizes
    private Coordinate getCoordinateOffset(){
        List<TilesAt> tilesAts = getTilesAts();
        int offsetX = Integer.MAX_VALUE;
        int offsetY = Integer.MAX_VALUE;

        for (int p = 0; p<tilesAts.size(); p++){

                Coordinate xy = tilesAts.get(p).getCoordinate();
            if (xy.getX() < offsetX){
                offsetX = xy.getX();
            }
            if (xy.getY() < offsetY){
                offsetY = xy.getY();
            }
        }
        offsetX *= -1;
        offsetY *= -1;
        return new Coordinate (offsetX, offsetY);
    }

    private void applyCoordinateOffsetToConnectBy(){
        for (int i=0; i<connectBys.size(); i++){
            Coordinate fromOffset = new Coordinate(
                    connectBys.get(i).getFrom().getX() + coordinateOffset.getX(),
                    connectBys.get(i).getFrom().getY() + coordinateOffset.getY());
            Coordinate toOffset = new Coordinate(
                    connectBys.get(i).getTo().getX() + coordinateOffset.getX(),
                    connectBys.get(i).getTo().getY() + coordinateOffset.getY());

            connectBys.get(i).setFrom(fromOffset);
            connectBys.get(i).setTo(toOffset);
        }
    }

    private void applyCoordinateOffsetToTilesAt(){
        for (int i=0; i< tilesAts.size(); i++){
            Coordinate tilesAtOffset = new Coordinate(
                    tilesAts.get(i).getCoordinate().getX() + coordinateOffset.getX(),
                    tilesAts.get(i).getCoordinate().getY() + coordinateOffset.getY());
            tilesAts.get(i).setCoordinate(tilesAtOffset);
        }
    }

    public void printOutInput(){
        System.out.println("Printing Input:");
        System.out.println(" ");
        System.out.println("tiles Size: " + tiles.size());
        System.out.println("tilesAts Size:" + tilesAts.size());
        System.out.println("connectBy Size: " + connectBys.size());
        System.out.println(" ");

        System.out.println("Tiles:");
        for (int d=0; d<tiles.size(); d++){
            System.out.println("  tile Name: " + tiles.get(d).getName());
            System.out.println("    tile Version Size: " + tiles.get(d).getVersions().size());
            System.out.println("    tile biggestXandY: " + tiles.get(d).getBiggestXandY().getX() + " " + tiles.get(d).getBiggestXandY().getY());
        }

        System.out.println(" ");
        System.out.println("TilesAts:");
        for (int i=0; i<tilesAts.size(); i++) {
            System.out.println("  tilesAt Index: " + i);
            System.out.println("    tilesAts X Y: " + tilesAts.get(i).getCoordinate().getX() + " " + tilesAts.get(i).getCoordinate().getY());
            System.out.println("    tilesAt KindName: " + tilesAts.get(i).getKindName());
            System.out.println("    default: " + tilesAts.get(i).getDefaultValue());
        }

        System.out.println(" ");
        System.out.println("ConnectBy:");
        for (int h = 0; h< connectBys.size(); h++){
            System.out.println("  connectBy Index: " + h);
            System.out.println("    connectBy From: " + connectBys.get(h).getFrom().getX() + " " + connectBys.get(h).getFrom().getY());
            System.out.println("    connectBy To: " + connectBys.get(h).getTo().getX() + " " + connectBys.get(h).getTo().getY());
            System.out.println("    connectBy Uses Size: " + connectBys.get(h).getUses().size());
        }
    }
}

