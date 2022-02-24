package com.example.g15_bugkiller;

import java.util.*;

public class Game {

    Map<String, Level> levels;
    Set<String> keysOfLockedLevels;
    List<Rule> mainRules;
    int numberOfUnlockedLevels;
    double unlockRatio;
    int totalPoints = 0;
    boolean freePlay = false;

    public Game(Map<String, Level> levels, double necessaryRatioOfTotalPoints, boolean freePlay) {
        this.levels = levels;
        this.unlockRatio = necessaryRatioOfTotalPoints;
        this.keysOfLockedLevels = cloneStringSet(levels.keySet());
        this.freePlay = freePlay;

        Random random = new Random();
        String keyOfFirstLeveltoBeUnlocked = new ArrayList<String>(keysOfLockedLevels).get(random.nextInt(keysOfLockedLevels.size()));
        levels.get(keyOfFirstLeveltoBeUnlocked).setUnlocked(true);
        keysOfLockedLevels.remove(keyOfFirstLeveltoBeUnlocked);
        this.numberOfUnlockedLevels = 1;

        if (freePlay){
             freePlay();
        }

    }

    public Map<String, Level> getLevels() {
        return levels;
    }

    public void unlockNextLevelAsNecessary(){  //
        int numberOfLevels = levels.size();

        if(numberOfUnlockedLevels < numberOfLevels) {
            double currentRatioOfTotalPoints = ((double) totalPoints)/((double) numberOfUnlockedLevels*3);

            while (currentRatioOfTotalPoints > unlockRatio) {
                Random random = new Random();
                System.out.println(keysOfLockedLevels.size());
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
