package com.example.g15_bugkiller;

import MapGeneration.Input;
import MapGeneration.MapGeneration;

import java.util.List;

public class Level {

    String levelName;
    int sparsity = 1;
    int[] gems;
    int[] ticks;
    List<Rule> preRules;
    List<Rule> mainRules;
    List<Rule> postRules;
    int maxSlime;
    Field[][] levelMap;
    int collectedGems;
    int ticksPast;  //schon vergangene Ticks
    int scoredPoints;
    int x;
    int y;
    int z;
    boolean timeUp = false; //ist ticksPast <= ticks[0] ?
    boolean passed = false;

    public boolean isPlayerDead() {
        return playerDead;
    }

    public void setPlayerDead(boolean playerDead) {
        this.playerDead = playerDead;
    }

    boolean playerDead = false;

    public Level(){}

    public Level(String levelName, int[] gems, Input mapData, int[] ticks) {
        this.levelName = levelName;
        this.gems = gems;
        MapGeneration map = new MapGeneration(mapData, 1000);
        this.levelMap = map.generateMap() ;
        this.ticks = ticks;

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
    }

    public void setPostRules(List<Rule> postRules) {
        this.postRules = postRules;
    }

    public void setMaxSlime(int maxSlime) {
        this.maxSlime = maxSlime;
    }

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
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
