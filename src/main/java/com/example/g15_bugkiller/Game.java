package com.example.g15_bugkiller;

import java.util.*;

public class Game {

    Map<String, Level> levels;
    Set<String> keysOfLockedLevels;
    List<Rule> mainRules;
    int numberOfUnlockedLevels;
    double necessaryRatioOfTotalPoints;
    int totalPoints = 0;
    boolean freePlay= false;

    public Game(Map<String, Level> levels, double necessaryRatioOfTotalPoints) {
        this.levels = levels;
        this.necessaryRatioOfTotalPoints = necessaryRatioOfTotalPoints;
        this.keysOfLockedLevels = cloneStringSet(levels.keySet());

        Random random = new Random();
        String keyOfFirstLeveltoBeUnlocked = new ArrayList<String>(keysOfLockedLevels).get(random.nextInt(keysOfLockedLevels.size()));
        System.out.println(keyOfFirstLeveltoBeUnlocked);
        levels.get(keyOfFirstLeveltoBeUnlocked).setUnlocked(true);
        keysOfLockedLevels.remove(keyOfFirstLeveltoBeUnlocked);
        this.numberOfUnlockedLevels = 1;


      //  levels.values().iterator().next().setUnlocked(true);     //TODO: modify
        if (freePlay){
             freePlay();
        }

    }

    public Map<String, Level> getLevels() {
        return levels;
    }

    //Todo: Überarbeiten
    public void unlockNextLevelAsNecessary(){
        int numberOfLevels = levels.size();

        if(numberOfUnlockedLevels < numberOfLevels) {
            int indexOfNextUnlockedLevel = numberOfUnlockedLevels;
            int necessaryPoints = (int) Math.floor(necessaryRatioOfTotalPoints * numberOfUnlockedLevels * 3);

            if (totalPoints >= necessaryPoints) {
                Random random = new Random();
                String keyOfNextLeveltoBeUnlocked = new ArrayList<String>(keysOfLockedLevels).get(random.nextInt(keysOfLockedLevels.size()));
                levels.get(keyOfNextLeveltoBeUnlocked).setUnlocked(true);
                keysOfLockedLevels.remove(keyOfNextLeveltoBeUnlocked);
                System.out.println(keyOfNextLeveltoBeUnlocked);
                numberOfUnlockedLevels++;
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

    public int getNumberOfUnlockedLevels() {
        return numberOfUnlockedLevels;
    }

    public void freePlay() {
        for (Level level : levels.values()) {
            level.setUnlocked(true);
        }
    }

    public Set<String> cloneStringSet(Set<String> set){
        Set<String> clonedSet = new HashSet<String>();
        for(String element: set){
            clonedSet.add(new String(element));
        }
        return clonedSet;
    }
}
