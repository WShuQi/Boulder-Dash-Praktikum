package com.example.g15_bugkiller;

import java.util.*;

public class Game {

    Map<String, Level> levels;
    Set<String> keysOfLockedLevels;
    List<Rule> mainRules;
    int numberOfUnlockedLevels;
    double unlockRatio;  //"benchmark" für Freischalten neuer Level, siehe Game.unlockNextLevelAsNecessary()
    int totalPoints = 0; //erzielte Punkte über alle Level hinweg
    boolean freePlay = false;  //alternativer Modus: alle Level sind freigeschaltet

    public Game(Map<String, Level> levels, double necessaryRatioOfTotalPoints, boolean freePlay) {
        this.levels = levels;
        this.unlockRatio = necessaryRatioOfTotalPoints;
        this.keysOfLockedLevels = cloneStringSet(levels.keySet());
        this.freePlay = freePlay;

        Random random = new Random();  //erstes freigeschaltetes Level wird zufällig gewählt
        String keyOfFirstLevelToBeUnlocked = new ArrayList<String>(keysOfLockedLevels).get(random.nextInt(keysOfLockedLevels.size()));
        levels.get(keyOfFirstLevelToBeUnlocked).setUnlocked(true);
        keysOfLockedLevels.remove(keyOfFirstLevelToBeUnlocked);
        this.numberOfUnlockedLevels = 1;

        if (freePlay){
             freePlay();
        }

    }

    public Map<String, Level> getLevels() {
        return levels;
    }

/*
    Regel zum Freischalten neuer Level:
    currentRatioOfTotalPoints: Anteil an erzielten Punkten an möglichen Punkten in derzeit freigeschalteten Leveln
    unlockRatio: "benchmark"
    Schalte zufällig ein neues Level frei, falls currentRatioOfTotalPoints > unlockRatio
    Wiederhole solange, bis wieder currentRatioOfTotalPoints <= unlockRatio gilt
    */
    public void unlockNextLevelAsNecessary(){
        int numberOfLevels = levels.size();

        if(numberOfUnlockedLevels < numberOfLevels) {
            double currentRatioOfTotalPoints = ((double) totalPoints)/((double) numberOfUnlockedLevels*3);

            while (currentRatioOfTotalPoints > unlockRatio) {
                Random random = new Random();
                String keyOfNextLevelToBeUnlocked = new ArrayList<String>(keysOfLockedLevels).get(random.nextInt(keysOfLockedLevels.size()));
                levels.get(keyOfNextLevelToBeUnlocked).setUnlocked(true);
                keysOfLockedLevels.remove(keyOfNextLevelToBeUnlocked);
                numberOfUnlockedLevels++;
                currentRatioOfTotalPoints = ((double) totalPoints)/((double) numberOfUnlockedLevels*3);
            }
        }
    }

    public void updateTotalPoints() {
        int totalPoints = 0;

        for (Level level : levels.values()) {

            if(level.isPassed()) {
                totalPoints = totalPoints + level.getBestScore();
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

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getNumberOfUnlockedLevels() {
        return numberOfUnlockedLevels;
    }

    public void setNumberOfUnlockedLevels(int numberOfUnlockedLevels) {
        this.numberOfUnlockedLevels = numberOfUnlockedLevels;
    }

    public void freePlay() {
        for (Level level : levels.values()) {
            level.setUnlocked(true);
        }
        numberOfUnlockedLevels = levels.size();
        keysOfLockedLevels.removeAll(keysOfLockedLevels);
    }

    public Set<String> cloneStringSet(Set<String> set) {
        Set<String> clonedSet = new HashSet<String>();
        for (String element : set) {
            clonedSet.add(new String(element));
        }
        return clonedSet;
    }
}
