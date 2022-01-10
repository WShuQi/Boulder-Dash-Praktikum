package com.example.g15_bugkiller;

import java.util.ArrayList;
import java.util.List;

public class TileVersion {

    private List<List<Field>> entries = new ArrayList<List<Field>>();

    public TileVersion(List<List<Field>> entries) {
        this.entries = entries;
    }

    public List<List<Field>> getEntries() {
        return entries;
    }


    public int getBiggestRowInTileVersion() {
        int biggest = 0;
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).size() > biggest) {
                biggest = entries.get(i).size();
            }
        }
        return biggest;
    }

}