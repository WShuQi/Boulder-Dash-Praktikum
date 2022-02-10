package com.example.g15_bugkiller;

import java.util.ArrayList;
import java.util.List;

public class Game {

    List<Level> levels;
    int numberOfUnlockedLevels = 1;
    int necessaryRatioOfTotalPoints;
    int totalPoints = 0;

    public Game(List<Level> levels, int necessaryRatioOfTotalPoints) {
        this.levels = levels;
        this.necessaryRatioOfTotalPoints = necessaryRatioOfTotalPoints;
        levels.get(0).setUnlocked(true);
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void unlockNextLevelAsNecessary(){
        int numberOfLevels = levels.size();

        if(numberOfUnlockedLevels < numberOfLevels) {
            int indexOfNextUnlockedLevel = numberOfUnlockedLevels;
            int necessaryPoints = (int) Math.floor(necessaryRatioOfTotalPoints * numberOfLevels * 3);

            if (totalPoints >= necessaryPoints) {
                levels.get(indexOfNextUnlockedLevel).setUnlocked(true);
                numberOfUnlockedLevels++;
            }
        }
    }

    public void updateTotalPoints(){
        int totalPoints = 0;

        for (Level level : levels) {
            totalPoints = totalPoints + level.getScoredPoints();
        }

        this.totalPoints = totalPoints;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
