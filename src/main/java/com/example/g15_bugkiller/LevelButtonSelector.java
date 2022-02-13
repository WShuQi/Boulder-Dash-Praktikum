package com.example.g15_bugkiller;

public class LevelButtonSelector {

    private final String levelName;

    private final double x, y;
    private final double w, h;

    LevelButtonSelector(String levelName, double x, double y, double w, double h) {
        this.levelName = levelName;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean contains(double x, double y) {
        return x >= this.x && x <= this.x + this.w && y >= this.y && y <= this.y + this.h;
    }

    public String getLevelName() {
        return levelName;
    }
}
