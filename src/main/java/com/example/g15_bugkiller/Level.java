package com.example.g15_bugkiller;

import MapGeneration.Input;
import MapGeneration.MapGeneration;
import MapGeneration.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private String levelName;
    private int sparsity = 1;
    private int[] gems;
    private int[] ticks;
    private List<Rule> preRules;
    private List<Rule> mainRules;
    private List<Rule> postRules;
    private int maxSlime;
    private Field[][] levelMap;
    private int collectedGems;
    private int ticksPast;  //schon vergangene Ticks
    private int scoredPoints;
    private int x;
    private int y;
    private int z;
    private int lives = 3;
    private int currentLives = lives;
    private Coordinate originalMePosition;
    private int stopCounter = 0;
    private boolean timeUp = false; //ist ticksPast <= ticks[0] ?
    private boolean passed = false;
    private boolean unlocked = false;
    private boolean exitReached = false;
    private boolean playerDead = false;
    private boolean stopped = false;

    private List<Field[][]> replaySaveData = new ArrayList<>();

    public Level(){}

    public Level(String levelName, int[] gems, Input mapData, int[] ticks) {
        this.levelName = levelName;
        this.gems = gems;
        MapGeneration map = new MapGeneration(mapData, 1000);
        this.levelMap = map.generateMap() ;
        this.ticks = ticks;
        this.originalMePosition = LevelLogic.computeMePosition(this.levelMap);

        System.out.println(levelName);
        System.out.println("gems: " + gems[0] + ", " + gems[1] + ", " + gems[2]);
        System.out.println("ticks: " + ticks[0] + ", " + ticks[1] + ", " + ticks[2]);
        //TerminalMap.drawMap(this.levelMap);
    }

    public Level(String levelName, int[] gems, Input mapData, int[] ticks, int lives) {
        this.levelName = levelName;
        this.gems = gems;
        MapGeneration map = new MapGeneration(mapData, 1000);
        this.levelMap = map.generateMap() ;
        this.ticks = ticks;
        this.lives = lives;
        this.currentLives = lives;
        this.originalMePosition = LevelLogic.computeMePosition(this.levelMap);

        System.out.println(levelName);
        System.out.println("gems: " + gems[0] + ", " + gems[1] + ", " + gems[2]);
        System.out.println("ticks: " + ticks[0] + ", " + ticks[1] + ", " + ticks[2]);
        //TerminalMap.drawMap(this.levelMap);
    }

    public String getLevelName() {
        return levelName;
    }

    public int[] getGems() {
        return gems;
    }

    public List<Rule> getPreRules() {
        return preRules;
    }

    public List<Rule> getPostRules() {
        return postRules;
    }

    public int getMaxSlime() {
        return maxSlime;
    }

    public Field[][] getLevelMap() {
        return levelMap;
    }

    public int getCollectedGems() {
        return collectedGems;
    }

    public int getScoredPoints() {
        return scoredPoints;
    }

    public int[] getTicks() {
        return ticks;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setPreRules(List<Rule> preRules) {

        this.preRules = preRules;

        /*System.out.println("Printing Pre Rule Values");
        for (int i = 0; i < preRules.size(); i++) {
            System.out.println("Original Values:");
            for (int j = 0; j < preRules.get(i).getOriginal().size(); j++) {
                System.out.println(preRules.get(i).getOriginal().get(j).getValues().getValueList());
            }

            System.out.println("Result Values:");
            for (int j = 0; j < preRules.get(i).getResult().size(); j++) {
                System.out.println(preRules.get(i).getResult().get(j).getValues().getValueList());
            }
        }*/
    }


    public void setPostRules(List<Rule> postRules) {
        this.postRules = postRules;
    }

    public void setMaxSlime(int maxSlime) { this.maxSlime = maxSlime;}

    public void setCollectedGems(int collectedGems) {
        this.collectedGems = collectedGems;
    }

    public void setScoredPoints(int scoredPoints) {
        this.scoredPoints = scoredPoints;
    }

    public void setLevelMap(Field[][] levelMap) {
        this.levelMap = levelMap;
    }

    public int getTicksPast() {
        return ticksPast;
    }

    public void setTicksPast(int ticksPast) {
        this.ticksPast = ticksPast;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setSparsity(int sparsity) {
        this.sparsity = sparsity;
    }

    public int getSparsity() {
        return sparsity;
    }

    public boolean isTimeUp() {
        return timeUp;
    }

    public void setTimeUp(boolean timeUp) {
        this.timeUp = timeUp;
    }

    public List<Rule> getMainRules() {
        return mainRules;
    }

    public void setMainRules(List<Rule> mainRules) {
        this.mainRules = mainRules;


        /*
        System.out.println("Printing main rules");

        for (int i = 0; i < mainRules.size(); i++) {
            System.out.println("Rule nummer " + i);
            //System.out.println("");
            //System.out.println("Situation: " + mainRules.get(i).getSituation());
            //System.out.println("Direction: " + mainRules.get(i).getDirection());

            System.out.println("Original Tokens:");
            for (int j = 0; j < mainRules.get(i).getOriginal().size(); j++) {

                System.out.println("Rule " + i + " Token  " + j + " is class " + mainRules.get(i).getOriginal().get(j).getToken().getClass());
                if (mainRules.get(i).getResult().get(j).getToken() instanceof Object) {
                    System.out.println("is a type array");
                    //System.out.println("type array has length " + ((ArrayList<?>) mainRules.get(i).getResult().get(j).getToken()).size());
                } else if (mainRules.get(i).getResult().get(j).getToken() instanceof Type) {
                    System.out.println("is a type");
                } else if (mainRules.get(i).getResult().get(j).getToken() instanceof Integer) {
                    System.out.println("is a int");
                } else {
                    System.out.println("is NOT a type array or type or int");
                }

                //System.out.println("Values:");
                //System.out.println(mainRules.get(i).getOriginal().get(j).getValues().getValueList());

            }

            System.out.println("");
            System.out.println("Result Tokens:");
            for (int j = 0; j < mainRules.get(i).getResult().size(); j++) {
                System.out.println("Rule " + i + " Token " + j+ " ist class " + mainRules.get(i).getResult().get(j).getToken().getClass());

                if (mainRules.get(i).getResult().get(j).getToken() instanceof ArrayList) {
                    System.out.println("is a type array");
                } else if (mainRules.get(i).getResult().get(j).getToken() instanceof Type) {
                    System.out.println("is a type");
                } else if (mainRules.get(i).getResult().get(j).getToken() instanceof Integer) {
                    System.out.println("is a int");
                } else {
                    System.out.println("is NOT a type array or type or int");
                }

                System.out.println("Values:");
                System.out.println(mainRules.get(i).getResult().get(j).getValues().getValueList());

            }
        }
        */
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isExitReached() {
        return exitReached;
    }

    public void setExitReached(boolean exitReached) {
        this.exitReached = exitReached;
    }


    public boolean isPlayerDead() {
        return playerDead;
    }

    public void setPlayerDead(boolean playerDead) {
        this.playerDead = playerDead;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public Coordinate getOriginalMePosition() {
        return originalMePosition;
    }

    public void setOriginalMePosition(Coordinate originalMePosition) {
        this.originalMePosition = originalMePosition;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public int getStopCounter() {
        return stopCounter;
    }

    public void setStopCounter(int stopCounter) {
        this.stopCounter = stopCounter;
    }

    public List<Field[][]> getReplaySaveData() {
        return replaySaveData;
    }

    public void setReplaySaveData(List<Field[][]> replaySaveData) {
        this.replaySaveData = replaySaveData;
    }
}
