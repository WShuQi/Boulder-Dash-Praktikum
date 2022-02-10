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

        System.out.println("Printing Pre Rule Values");
        for (int i = 0; i < preRules.size(); i++) {
            System.out.println("Original Values:");
            for (int j = 0; j < preRules.get(i).getOriginal().size(); j++) {
                System.out.println(preRules.get(i).getOriginal().get(j).getValues().getValueList());
            }

            System.out.println("Result Values:");
            for (int j = 0; j < preRules.get(i).getResult().size(); j++) {
                System.out.println(preRules.get(i).getResult().get(j).getValues().getValueList());
            }
        }
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

        System.out.println("Printing main rules");

        for (int i = 0; i < mainRules.size(); i++) {
            System.out.println("Rule nummer " + i);
            System.out.println("");
            System.out.println("Situation: " + mainRules.get(i).getSituation());
            System.out.println("Direction: " + mainRules.get(i).getDirection());
            System.out.println("");

            for (int j = 0; j < mainRules.get(i).getOriginal().size(); j++) {

                System.out.println("Token " + mainRules.get(i).getOriginal().get(j).getToken());
                if(mainRules.get(i).getResult().get(j).getToken() == Type[].class){
                    System.out.println("is a type array");
                }else if(mainRules.get(i).getResult().get(j).getToken() == Type.class){
                    System.out.println("is a type");
                }else if(mainRules.get(i).getResult().get(j).getToken() == int.class){
                    System.out.println("is a int");
                }else{
                    System.out.println("is NOT a type array or type or int");
                }

                System.out.println("Values:");
                System.out.println(mainRules.get(i).getOriginal().get(j).getValues().getValueList());

            }

            System.out.println("");
            System.out.println("Result Values:");
            for (int j = 0; j < mainRules.get(i).getResult().size(); j++) {
                System.out.println("Token " + mainRules.get(i).getResult().get(j).getToken());

                if(mainRules.get(i).getResult().get(j).getToken() == Type[].class){
                    System.out.println("is a type array");
                }else if(mainRules.get(i).getResult().get(j).getToken() == Type.class){
                    System.out.println("is a type");
                }else if(mainRules.get(i).getResult().get(j).getToken() == int.class){
                    System.out.println("is a int");
                }else{
                    System.out.println("is NOT a type array or type or int");
                }

                System.out.println("Values:");
                System.out.println(mainRules.get(i).getResult().get(j).getValues().getValueList());
            }
        }
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
