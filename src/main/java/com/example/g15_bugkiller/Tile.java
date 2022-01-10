package com.example.g15_bugkiller;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    private String name;
    private List<TileVersion> versions = new ArrayList<TileVersion>();

    public Tile(String name, List<TileVersion> versions) {
        this.name = name;
        this.versions = versions;
    }

    public String getName() {
        return name;
    }

    public List<TileVersion> getVersions() {
        return versions;
    }

    public Coordinate getBiggestXandY(){
        //fixme: hier scheint es ein Problem zu geben
        int biggestX = 0;
        int biggestY = 0;
        //Problem von folgenden Zeilen: Caused by: java.lang.NullPointerException
        for (TileVersion version : versions) {
            List<List<Field>> entries = version.getEntries();
            if (entries.size() > biggestY){
                biggestY = entries.size();
            }
            for (int i = 0; i < entries.size(); i++) {
                if (entries.get(i).size() > biggestX){
                    biggestX = entries.get(i).size();
                }
            }
        }
        return new Coordinate(biggestX, biggestY);
    }
}
