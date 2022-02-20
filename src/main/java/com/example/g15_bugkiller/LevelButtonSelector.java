package com.example.g15_bugkiller;

public class LevelButtonSelector {

    public static final int DIFF_BUTTONS = 48;
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

    public boolean onPlayButton(double x, double y) {
        return x >= this.x && x <= this.x + this.w && y >= this.y && y <= this.y + this.h;
    }

    public boolean onReplayButton(double x, double y) {
        return x >= this.x + DIFF_BUTTONS + this.w && x <= this.x + DIFF_BUTTONS + this.w + this.w && y >= this.y && y <= this.y + this.h;
    }

    public String getLevelName() {
        return levelName;
    }
}
