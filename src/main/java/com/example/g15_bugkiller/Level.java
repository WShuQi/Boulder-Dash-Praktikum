package com.example.g15_bugkiller;

import MapGeneration.Input;
import MapGeneration.MapGeneration;
import MapGeneration.Coordinate;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private String levelName;
    private int sparsity = 1;
    private int[] gems;
    private int[] ticks;
    private Input mapdata; //Input für Kartengeneration
    private List<Rule> preRules;
    private List<Rule> mainRules;
    private List<Rule> postRules;
    private int maxSlime;
    private Field[][] levelMap;
    private int collectedGems;  //aktuell im Spielverlauf gesammelte Gems
    private int ticksPast;  //aktuell im Spielverlauf schon vergangene Ticks
    private int scoredPoints;   //aktuell erreichte Punkte
    private int x = 0;
    private int y = 0;
    private int z = 0;
    private int lives = 3;  //grundsätzlich in jedem Spiel verfügbare Leben
    private int currentLives = lives;  //aktuell noch verfügbare Leben
    private Coordinate originalMePosition;  //Me-Position bei Start des Spiels; bei Verlust eines Lebens wird hierhin gesprungen
    private int stopCounter = 0; //vergangene Ticks bei Einsammeln eines Stoppschilds
    private boolean timeUp = false; //ist ticksPast <= ticks[0] ?
    private boolean passed = false;
    private boolean unlocked = false;
    private boolean exitReached = false;
    private boolean playerDead = false;
    private boolean stopped = false;  //Spiel gestoppt durch Einsammeln eines Stoppschildes?

    private int bestGems = 0; //Bestwert bei Gems
    private int bestTime = (int) Double.POSITIVE_INFINITY; //Bestwert bei Spielzeit
    private int bestScore = 0;     //Bestwert bei erlangten Punkten
    private MapGeneration map;

    private List<Field[][]> replaySaveData = new ArrayList<>();

    private JSONArray preJson;
    private JSONArray postJson;

    public Level() {
    }

    public Level(String levelName, int[] gems, Input mapData, int[] ticks) {
        this.levelName = levelName;
        this.gems = gems;
        this.mapdata = mapData;
        map = new MapGeneration(mapData, 1000);
        this.levelMap = map.generateMap();
        getXYZValuesFromMapData(this.levelMap);
        this.ticks = ticks;
        this.originalMePosition = LevelLogic.computeMePosition(this.levelMap);
    }

    public Level(String levelName, int[] gems, Input mapData, int[] ticks, int lives) {
        this.levelName = levelName;
        this.gems = gems;
        this.mapdata = mapData;
        map = new MapGeneration(mapData, 1000);
        this.levelMap = map.generateMap();
        getXYZValuesFromMapData(this.levelMap);
        this.ticks = ticks;
        this.lives = lives;
        this.currentLives = lives;
        this.originalMePosition = LevelLogic.computeMePosition(this.levelMap);

        System.out.println(levelName);
        System.out.println("gems: " + gems[0] + ", " + gems[1] + ", " + gems[2]);
        System.out.println("ticks: " + ticks[0] + ", " + ticks[1] + ", " + ticks[2]);
        //TerminalMap.drawMap(this.levelMap);
    }

    public void generateNewMap(){
        this.levelMap = cloneLevelMap(map.generateMap());
        this.originalMePosition = LevelLogic.computeMePosition(this.levelMap);
        getXYZValuesFromMapData(this.levelMap);
    }

    public Field[][] cloneLevelMap(Field[][] map){   //dient dazu, eine geklonte Version einer LevelMap zu erhalten, um Probleme mit Call-by-Reference zu umgehen
        int numberOfColumns = map.length;
        int numberOfRows = map[0].length;
        Field[][] clonedMap = new Field[numberOfColumns][numberOfRows];

        for(int columnIterator = 0; columnIterator < numberOfColumns; columnIterator++){
            for(int rowIterator = 0; rowIterator < numberOfRows; rowIterator++){
                boolean checked = map[columnIterator][rowIterator].getChecked();
                Type token = map[columnIterator][rowIterator].getGegenstand().getToken();
                Values values = map[columnIterator][rowIterator].getGegenstand().getValues();
                Values clonedValues = new Values(values.cloneValueList(values.getValueList()));
                clonedMap[columnIterator][rowIterator] = new Field(checked, new Gegenstand(token, clonedValues));
            }
        }

        return clonedMap;
    }

    private void getXYZValuesFromMapData(Field[][] levelMap){
        for (int i = 0; i < levelMap.length; i++) {
            for (int j = 0; j < levelMap[i].length; j++) {
                if(levelMap[i][j].getGegenstand().getValues().getValueList().containsKey(ValuesNames.X)){
                    this.x = levelMap[i][j].getGegenstand().getValues().getValueList().get(ValuesNames.X);
                }
                if(levelMap[i][j].getGegenstand().getValues().getValueList().containsKey(ValuesNames.Y)){
                    this.y = levelMap[i][j].getGegenstand().getValues().getValueList().get(ValuesNames.Y);

                }
                if(levelMap[i][j].getGegenstand().getValues().getValueList().containsKey(ValuesNames.Z)){
                    this.z = levelMap[i][j].getGegenstand().getValues().getValueList().get(ValuesNames.Z);

                }
            }
        }
    }

    public String getLevelName() {
        return levelName;
    }

    public int[] getGems() {
        return gems;
    }

    public Input getMapdata() {
        return mapdata;
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

    public void setMaxSlime(int maxSlime) { this.maxSlime = maxSlime;}

    public void setCollectedGems(int collectedGems) {
        this.collectedGems = collectedGems;
    }

    public void addToCollectedGems(int collectedGems) {
        if(collectedGems == 0){
            this.collectedGems = 0;
        } else {
            this.collectedGems = java.lang.Math.max(this.collectedGems + collectedGems, 0);
        }
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

    public void addToTicksPast(int ticksPast) {
        if(ticksPast == 0){
            this.ticksPast = 0;
        } else {
            this.ticksPast = java.lang.Math.max(this.ticksPast + ticksPast, 0);
        }
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

    public void addToX(int x) {
        if(x == 0){
            this.x = 0;
        } else {
            this.x = java.lang.Math.max(this.x + x, 0);
        }
    }

    public void addToY(int y) {
        if(y == 0){
            this.y = 0;
        } else {
            this.y = java.lang.Math.max(this.y + y, 0);
        }
    }

    public void addToZ(int z) {
        if(z == 0){
            this.z = 0;
        } else {
            this.z = java.lang.Math.max(this.z + z, 0);
        }
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

    public void setBestGems(int currentGems) {
        if (currentGems > this.bestGems) {
            this.bestGems = currentGems;
        }
    }


    public void setBestTime(int currentTime) {
        if (currentTime < this.bestTime) {
            this.bestTime = currentTime;
        }
    }

    public void setBestScore(int currentScore) {
        if (currentScore > this.bestScore) {
            this.bestScore = currentScore;
        }
    }

    public int getBestGems() {
        return bestGems;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getBestTime() {
        return bestTime;
    }

    public Coordinate getMEPosition() {
        Field[][] map = this.getLevelMap();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y].getType() == Type.ME) {
                    return new Coordinate(x, y);
                }
            }
        }
        return getOriginalMePosition();
    }

    public void setPre(JSONArray pre) {
        this.preJson = pre;
    }

    public void setPost(JSONArray post) {
        this.postJson = post;
    }

    public JSONArray getPre() {
        return preJson;
    }

    public JSONArray getPost() {
        return postJson;
    }

}
