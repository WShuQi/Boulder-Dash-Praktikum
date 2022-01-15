package com.example.g15_bugkiller;

import MapGeneration.Input;
import MapGeneration.MapGeneration;

import java.util.List;

public class Level {

    String levelName;
    int[] gems;
    int[] ticks;
    List<Regel> preRules;
    List<Regel> postRules;
    int maxSlime;
    Field[][] map;
    int collectedGems;
    int ticksPast;  //schon vergangene Ticks
    int scoredPoints;
    int time;  //maximale Zeit in Ticks
    int x;
    int y;
    int z;

    public Level(){}

    public Level(String levelName, int[] gems, int time, Input mapData, int[] ticks) {
        this.levelName = levelName;
        this.gems = gems;
        MapGeneration map = new MapGeneration(mapData, 1000);
        this.map = map.generateMap() ;
        this.time = time;

        System.out.println(levelName);
        System.out.println("gems: " + gems[0] + ", " + gems[1] + ", " + gems[2]);
        System.out.println("time: " + ticks[0] + ", " + ticks[1] + ", " + ticks[2]);
        TerminalMap.drawMap(this.map);
    }

    public String getLevelName() {
        return levelName;
    }

    public int[] getGems() {
        return gems;
    }

    public int getTime() { return time; }

    public List<Regel> getPreRules() {
        return preRules;
    }

    public List<Regel> getPostRules() {
        return postRules;
    }

    public int getMaxSlime() {
        return maxSlime;
    }

    public Field[][] getMap() {
        return map;
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

    public void setPreRules(List<Regel> preRules) {
        this.preRules = preRules;
    }

    public void setPostRules(List<Regel> postRules) {
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

    public void setMap(Field[][] map) {
        this.map = map;
    }

    public void setTime(int time) {
        this.time = time;
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
}
