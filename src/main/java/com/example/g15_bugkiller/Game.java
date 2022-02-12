package com.example.g15_bugkiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {

    Map<String, Level> levels;
    List<Rule> mainRules;
    int numberOfUnlockedLevels = 1;
    double necessaryRatioOfTotalPoints;
    int totalPoints = 0;

    public Game(Map<String, Level> levels, double necessaryRatioOfTotalPoints) {
        this.levels = levels;
        this.necessaryRatioOfTotalPoints = necessaryRatioOfTotalPoints;
        levels.get(0).setUnlocked(true);     //TODO: modify
    }

    public Map<String, Level> getLevels() {
        return levels;
    }

    //Todo:Habe ein HashMap geplant, so kann man laut Namen die Reihenfolge von Leveln bestimmen. Bitte noch anpassen.
    public void unlockNextLevelAsNecessary(){
        int numberOfLevels = levels.size();

        if(numberOfUnlockedLevels < numberOfLevels) {
            int indexOfNextUnlockedLevel = numberOfUnlockedLevels;
            int necessaryPoints = (int) Math.floor(necessaryRatioOfTotalPoints * numberOfUnlockedLevels * 3);

            if (totalPoints >= necessaryPoints) {
                levels.get(indexOfNextUnlockedLevel).setUnlocked(true);
                numberOfUnlockedLevels++;
            }
        }
    }

    public void updateTotalPoints() {
        int totalPoints = 0;

        for (Level level : levels.values()) {

            if(level.isPassed()) {
                totalPoints = totalPoints + level.getScoredPoints();
            }
        }

        this.totalPoints = totalPoints;
    }

    public void setLevel(String name, Level level) {
        this.levels.put(name, level);
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
