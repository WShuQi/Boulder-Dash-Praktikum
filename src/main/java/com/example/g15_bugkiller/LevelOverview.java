package com.example.g15_bugkiller;

import java.util.ArrayList;
import java.util.List;

public class LevelOverview {

    List<Level> levels = new ArrayList<Level>();

    public LevelOverview(List<Level> levels) {
        this.levels = levels;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }
}
