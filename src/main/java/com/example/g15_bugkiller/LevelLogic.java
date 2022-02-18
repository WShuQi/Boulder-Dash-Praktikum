package com.example.g15_bugkiller;

import MapGeneration.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LevelLogic {

    // Grundablauf pro Tick
    public static void tick (Level level, KeyPressListener currentKeysPressed) {

        //System.out.println("mapData: " + level.getLevelMap());
        level.setTicksPast(level.getTicksPast() + 1);

        executePreRules(level, currentKeysPressed);
        executeMainRules(level, currentKeysPressed);
        executePostRules(level, currentKeysPressed);

        computeScoredPoints(level);
        slimeCheck(level);
        updateStopCounter(level);
        collectedGemCheck(level);
        playerDeadCheck(level);
        checkIfExitIsReached(level);
        checkIfLevelIsPassed(level);
        checkIfTimeIsUp(level);

        resetValues(level);
    }

    public static void resetLevel(Level level){     //Zurücksetzen der Zusatzwerte aller Felder entsprechend ihrer Bedeutung
        resetValues(level);
        level.setTicksPast(0);
        level.setExitReached(false);
        level.setTimeUp(false);
        level.setCollectedGems(0);
        level.setStopped(false);
        level.setPlayerDead(false);

        if(!level.isPassed()){
            level.setScoredPoints(0);
        }
    }

    public static void resetValues(Level level){
        Field[][] map = level.getLevelMap();
        int rowLength = map.length;
        int columnLength = map[0].length;

        for(int rowIterator = 0; rowIterator < rowLength; rowIterator++) {
            for (int columnIterator = 0; columnIterator < columnLength; columnIterator++) {
                map[rowIterator][columnIterator].getGegenstand().resetValues();
            }
        }
    }


    private static void checkIfTimeIsUp(Level level){
        int maxTicks = level.getTicks()[0];
        int ticksPast = level.getTicksPast();
        level.setTimeUp(ticksPast >= maxTicks);
    }

    private static void computeScoredPoints(Level level){
        int[] gems = level.getGems();
        int[] ticks = level.getTicks();
        int collectedGems = level.getCollectedGems();
        int ticksPast = level.getTicksPast();

        if(ticksPast <= ticks[2] && collectedGems >= gems[2]){
            level.setScoredPoints(3);
        } else if ((ticksPast > ticks[2] && ticksPast <= ticks[1]) || (collectedGems < gems[2] && collectedGems >= gems[1])){
            level.setScoredPoints(2);
        } else if ((ticksPast > ticks[1] && ticksPast <= ticks[0]) || (collectedGems < gems[1] && collectedGems >= gems[0])){
            level.setScoredPoints(1);
        } else {
            level.setScoredPoints(0);
        }
    }

    //TODO: check this in a new mainRule

    private static void checkIfExitIsReached(Level level){

        Field[][] map = level.getLevelMap();

        int numberOfRows = map.length;
        int numberOfColumns = map[0].length;

        for(int rowCounter = 0; rowCounter < numberOfColumns; rowCounter++){
            for(int columnCounter = 0; columnCounter < numberOfRows; columnCounter++){

                if(map[columnCounter][rowCounter].getGegenstand().getValues().getValueList().getOrDefault(ValuesNames.EXITREACHED, 0) == 1){
                    level.setExitReached(true);
                    return;
                }
            }
        }
    }

    private static void checkIfLevelIsPassed(Level level){
        if(level.getScoredPoints() > 0 && !level.isTimeUp() && level.isExitReached() && !level.isPlayerDead()){
            level.setPassed(true);
        }
    }

    private static void executePreRules(Level level, KeyPressListener currentKeysPressed){
        List<Rule> preRules = level.getPreRules();
        executeRules(preRules, level, currentKeysPressed);
    }

    private static void executeMainRules(Level level, KeyPressListener currentKeysPressed){
        List<Rule> mainRules = level.getMainRules();
        executeRules(mainRules, level, currentKeysPressed);
    }

    private static void executePostRules(Level level, KeyPressListener currentKeysPressed){
        List<Rule> postRules = level.getPostRules();
        executeRules(postRules, level, currentKeysPressed);
    }

    private static boolean checkIfSituationOccurs(Situation situation, Level level, KeyPressListener currentKeysPressed){
        boolean situationOccurs = false;

        if (situation == Situation.ANY || (situation == Situation.RARE && level.getTicksPast() % level.getSparsity() == 0) ||
                (situation == Situation.UP && currentKeysPressed.isUpPressed()) || (situation == Situation.DOWN && currentKeysPressed.isDownPressed()) ||
                (situation == Situation.RIGHT && currentKeysPressed.isRightPressed()) || (situation == Situation.LEFT && currentKeysPressed.isLeftPressed()) ||
                (situation == Situation.METAUP && currentKeysPressed.isMetaUpPressed()) || (situation == Situation.METADOWN && currentKeysPressed.isMetaDownPressed()) ||
                (situation == Situation.METARIGHT && currentKeysPressed.isMetaRightPressed()) || (situation == Situation.METALEFT && currentKeysPressed.isMetaLeftPressed()) ||
                (situation == Situation.CHANCE && giveTrueWithThreePercentChance())) {

            situationOccurs = true;
        }

        return situationOccurs;
    }

    private static boolean giveTrueWithThreePercentChance(){
        return (Math.random() <= 0.03);
    }

    private static void executeRules(List<Rule> rules, Level level, KeyPressListener currentKeysPressed){

        if(rules == null){
            return;
        }

        for(Rule rule : rules){

            Situation situation = rule.getSituation();
            Direction direction = rule.getDirection();

            if(!checkIfSituationOccurs(situation, level,  currentKeysPressed)){
                continue;
            }

            switch(direction) {
                case EAST:
                    executeRuleEastward(rule, level);
                    break;
                case WEST:
                    executeRuleWestward(rule, level);
                    break;
                case NORTH:
                    executeRuleNorthward(rule, level);
                    break;
                case SOUTH:
                    executeRuleSouthward(rule, level);
                    break;
            }

            if(rule.getRuleName() != null && (rule.getRuleName().equals("StopButtonRight") | rule.getRuleName().equals("StopButtonLeft") | rule.getRuleName().equals("StopButtonDown") | rule.getRuleName().equals("StopButtonUp"))){
                level.setStopped(true);
                level.setStopCounter(5*5);
            }

        }
    }

    private static void executeRuleEastward(Rule rule, Level level){
        List<RuleComponent> original = rule.getOriginal();
        List<RuleComponent> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int numberOfRows = map.length;
        int numberOfColumns = map[0].length;
        int numberOfRuleComponents = original.size();


        for(int rowCounter = 0; rowCounter < numberOfColumns; rowCounter++){

            int columnCounter = 0;

            while(columnCounter + numberOfRuleComponents < numberOfRows){

                Field[] nextFields = new Field[numberOfRuleComponents];


                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[columnCounter + fieldCounter][rowCounter];
                }

                if(checkIfNextFieldsAndOriginalsAgree(nextFields, original)){

                    replaceFields(nextFields, result);
                    columnCounter = columnCounter + numberOfRuleComponents;

                } else {
                    columnCounter++;
                }

            }
        }
    }

    private static void executeRuleWestward(Rule rule, Level level){
        List<RuleComponent> original = rule.getOriginal();
        List<RuleComponent> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int numberOfRows = map.length;
        int numberOfColumns = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int rowCounter = 0; rowCounter < numberOfColumns; rowCounter++){

            int columnCounter = numberOfRows-1;

            while(columnCounter - numberOfRuleComponents >= 0){

                Field[] nextFields = new Field[numberOfRuleComponents];

                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[columnCounter - fieldCounter][rowCounter];
                }

                if(checkIfNextFieldsAndOriginalsAgree(nextFields, original)){
                    replaceFields(nextFields, result);
                    columnCounter = columnCounter - numberOfRuleComponents;

                } else {
                    columnCounter--;
                }

            }
        }
    }

    private static void executeRuleNorthward(Rule rule, Level level){
        List<RuleComponent> original = rule.getOriginal();
        List<RuleComponent> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int numberOfRows = map.length;
        int numberOfColumns = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int columnCounter = 0; columnCounter < numberOfRows; columnCounter++){

            int rowCounter = numberOfColumns-1;

            while(rowCounter - numberOfRuleComponents >= 0){

                Field[] nextFields = new Field[numberOfRuleComponents];

                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[columnCounter][rowCounter - fieldCounter];
                }

                if(checkIfNextFieldsAndOriginalsAgree(nextFields, original)){
                    replaceFields(nextFields, result);
                    rowCounter = rowCounter - numberOfRuleComponents;

                } else {
                    rowCounter--;
                }

            }
        }
    }


    private static void executeRuleSouthward(Rule rule, Level level){
        List<RuleComponent> original = rule.getOriginal();
        List<RuleComponent> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int numberOfRows = map.length;
        int numberOfColumns = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int columnCounter = 0; columnCounter < numberOfRows; columnCounter++){

            int rowCounter = 0;

            while(rowCounter + numberOfRuleComponents < numberOfColumns){

                Field[] nextFields = new Field[numberOfRuleComponents];

                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[columnCounter][rowCounter + fieldCounter];
                }

                if(checkIfNextFieldsAndOriginalsAgree(nextFields, original)){

                    replaceFields(nextFields, result);
                    rowCounter = rowCounter + numberOfRuleComponents;

                } else {
                    rowCounter++;
                }

            }
        }
    }

    private static boolean checkIfNextFieldsAndOriginalsAgree(Field[] nextFields, List<RuleComponent> original){
        boolean nextFieldsAndOriginalsAgree = true;
        int numberOfOriginals = original.size();

        for(int componentCounter = 0; componentCounter < numberOfOriginals; componentCounter++){

            Gegenstand currentGegenstand = nextFields[componentCounter].getGegenstand();
            Object currentOriginalToken = original.get(componentCounter).getToken();
            Values currentOriginalValues = original.get(componentCounter).getValues();

            if(currentOriginalToken instanceof Type){

                if(!((currentOriginalToken.equals(Type.CATCHALL) || currentOriginalToken.equals(currentGegenstand.getToken())) && valuesAgree(currentGegenstand.getValues().getValueList(), currentOriginalValues.getValueList()))){
                    nextFieldsAndOriginalsAgree = false;
                }

            } else if (currentOriginalToken instanceof ArrayList){

                if(!((ArrayList) currentOriginalToken).contains(currentGegenstand.getToken()) || !valuesAgree(currentGegenstand.getValues().getValueList(), currentOriginalValues.getValueList())){
                    nextFieldsAndOriginalsAgree = false;
                }

            }
        }

        return nextFieldsAndOriginalsAgree;
    }

    private static boolean valuesAgree(HashMap<ValuesNames, Integer> fieldValues, HashMap<ValuesNames, Integer> ruleComponentValues){

        boolean valuesAgree = true;

        for(ValuesNames valueName: ruleComponentValues.keySet()){
            if(fieldValues.get(valueName) != ruleComponentValues.get(valueName)){
                valuesAgree = false;
            }
        }

        return valuesAgree;

        /*
        boolean bothAreZero = true;
        boolean ruleComponentValuesArePositive = true;
        boolean fieldValuesAreGreaterOrEqualToRuleComponentValues = true;

        for(Integer fieldValue: fieldValues.values()){
            if(fieldValue != 0) {
                bothAreZero = false;
                break;
            }
        }

        for(Integer ruleComponentValue: ruleComponentValues.values()){
            if(ruleComponentValue != 0){
                bothAreZero = false;
            }
            if(ruleComponentValue <= 0){
                ruleComponentValuesArePositive = false;
            }
        }

        for(ValuesNames valueName: ruleComponentValues.keySet()){
            if(fieldValues.getOrDefault(valueName, 0) < ruleComponentValues.get(valueName)){
                fieldValuesAreGreaterOrEqualToRuleComponentValues = false;
                break;
            }
        }

        return (bothAreZero || (fieldValuesAreGreaterOrEqualToRuleComponentValues && ruleComponentValuesArePositive));
        */
    }


    private static void replaceFields(Field[] nextFields, List<RuleComponent> result) {
        int resultLength = result.size();

        for(int resultIterator = 0; resultIterator < resultLength; resultIterator++){

            Field currentField = nextFields[resultIterator];
            RuleComponent currentResultComponent = result.get(resultIterator);

            if(currentResultComponent.getToken() instanceof Type){
                Type newToken = (Type) currentResultComponent.getToken();
                currentField.getGegenstand().setToken(newToken);
            } else if (currentResultComponent.getToken() instanceof Integer){
                currentField.getGegenstand().setToken(nextFields[(int) currentResultComponent.getToken()].getGegenstand().getToken());
            }

            replaceValues(currentField, currentResultComponent);
        }
    }

    private static boolean valuesWereReplaced = false;
    private static void replaceValues(Field currentField, RuleComponent currentResultComponent) {
        valuesWereReplaced = true;
        for(ValuesNames valueName: currentResultComponent.getValues().getValueList().keySet()){
            int currentResultComponentValue = currentResultComponent.getValues().getValueList().get(valueName);
            currentField.getGegenstand().getValues().getValueList().put(valueName, currentResultComponentValue);
            /*
            int currentFieldValue = currentField.getGegenstand().getValues().getValueList().getOrDefault(valueName, 0);
            int currentResultComponentValue = currentResultComponent.getValues().getValueList().get(valueName);

            if(currentResultComponentValue == 0){
                currentField.getGegenstand().getValues().getValueList().put(valueName, 0);
            } else {
                int newFieldValue = java.lang.Math.max(currentResultComponentValue + currentFieldValue,0);
                currentField.getGegenstand().getValues().getValueList().put(valueName, newFieldValue);
            }*/
        }
    }

    //Methods for mainRules
    private static RuleComponent spaceToGrow = new RuleComponent(new Type[]{Type.PATH, Type.BLOCKLING, Type.SWAPLING, Type.XLING, Type.MUD}, new Values());
    private static RuleComponent spaceToGrowTo = new RuleComponent(1, new Values());
    private static RuleComponent slimeWithCanGrow = new RuleComponent(Type.SLIME, new Values(new HashMap<ValuesNames, Integer>(){{put(ValuesNames.CANGROW, 1);}}));
    private static RuleComponent slimeWithoutCanGrow = new RuleComponent(Type.SLIME, new Values(new HashMap<ValuesNames, Integer>(){{put(ValuesNames.CANGROW, 0);}}));

    private static Rule setSlimeCanGrowRuleEast = new Rule(Situation.ANY, Direction.EAST, List.of(slimeWithoutCanGrow, spaceToGrow), List.of(slimeWithCanGrow, spaceToGrowTo));
    private static Rule setSlimeCanGrowRuleNorth = new Rule(Situation.ANY, Direction.NORTH, List.of(slimeWithoutCanGrow, spaceToGrow), List.of(slimeWithCanGrow, spaceToGrowTo));
    private static Rule setSlimeCanGrowRuleWest = new Rule(Situation.ANY, Direction.WEST, List.of(slimeWithoutCanGrow, spaceToGrow), List.of(slimeWithCanGrow, spaceToGrowTo));
    private static Rule setSlimeCanGrowRuleSouth = new Rule(Situation.ANY, Direction.SOUTH, List.of(slimeWithoutCanGrow, spaceToGrow), List.of(slimeWithCanGrow, spaceToGrowTo));

    private static Rule spreadSlimeCanGrowRuleEast = new Rule(Situation.ANY, Direction.EAST, List.of(slimeWithCanGrow, slimeWithoutCanGrow), List.of(slimeWithCanGrow, slimeWithCanGrow));
    private static Rule spreadSlimeCanGrowRuleNorth = new Rule(Situation.ANY, Direction.NORTH, List.of(slimeWithCanGrow, slimeWithoutCanGrow), List.of(slimeWithCanGrow, slimeWithCanGrow));
    private static Rule spreadSlimeCanGrowRuleWest = new Rule(Situation.ANY, Direction.WEST, List.of(slimeWithCanGrow, slimeWithoutCanGrow), List.of(slimeWithCanGrow, slimeWithCanGrow));
    private static Rule spreadSlimeCanGrowRuleSouth = new Rule(Situation.ANY, Direction.SOUTH, List.of(slimeWithCanGrow, slimeWithoutCanGrow), List.of(slimeWithCanGrow, slimeWithCanGrow));

    private static void slimeCheck(Level level){
        if(maxSlimeCheck(level)){
            setSlimesToStone(level);
            return;
        }

        //set if slimes can grow
        executeRuleEastward(setSlimeCanGrowRuleEast, level);
        executeRuleNorthward(setSlimeCanGrowRuleNorth, level);
        executeRuleWestward(setSlimeCanGrowRuleWest, level);
        executeRuleSouthward(setSlimeCanGrowRuleSouth, level);

        //spread canGrow to neighbour slimes
        valuesWereReplaced = true;

        while(valuesWereReplaced){
            valuesWereReplaced = false;

            executeRuleEastward(spreadSlimeCanGrowRuleEast, level);
            executeRuleNorthward(spreadSlimeCanGrowRuleNorth, level);
            executeRuleWestward(spreadSlimeCanGrowRuleWest, level);
            executeRuleSouthward(spreadSlimeCanGrowRuleSouth, level);
        }

        setSlimesToGem(level);
    }

    private static boolean maxSlimeCheck(Level level){
        if(level.getMaxSlime() == 0){
            return false;
        }
        Field[][] map = level.getLevelMap();

        int totalSlimes = 0;

        for (int x = 0; x<map.length; x++){
            for (int y = 0; y<map[x].length; y++){
                if(map[x][y].getType() == Type.SLIME){
                    totalSlimes++;
                }
            }
        }
        return totalSlimes > level.getMaxSlime();
    }

    private static void setSlimesToStone(Level level){
        Field[][] map = level.getLevelMap();

        for (int x = 0; x<map.length; x++){
            for (int y = 0; y<map[x].length; y++){
                if(map[x][y].getType() == Type.SLIME){
                    map[x][y].getGegenstand().setToken(Type.STONE);
                }
            }
        }
    }

    private static void setSlimesToGem(Level level){
        Field[][] map = level.getLevelMap();

        for (int x = 0; x<map.length; x++){
            for (int y = 0; y<map[x].length; y++){
                Gegenstand gegenstand = map[x][y].getGegenstand();
                Integer canGrow = gegenstand.getValues().getValueList().get(ValuesNames.CANGROW);
                if(gegenstand.getToken() == Type.SLIME && canGrow != null && canGrow == 0){
                    map[x][y].getGegenstand().setToken(Type.GEM);
                }
            }
        }
    }

    private static void collectedGemCheck(Level level){
        Field[][] map = level.getLevelMap();

        for (int x = 0; x<map.length; x++){
            for (int y = 0; y<map[x].length; y++){
                if (map[x][y].getGegenstand().getValues().getValueList().getOrDefault(ValuesNames.COLLECTED, 0) == 1){
                    level.setCollectedGems(level.getCollectedGems() + 1);
                }
            }
        }
    }

    private static void playerDeadCheck(Level level){
        Field[][] map = level.getLevelMap();
        boolean playerIsDead = true;

        for (int x = 0; x<map.length; x++){
            for (int y = 0; y<map[x].length; y++){
                if (map[x][y].getType() == Type.ME){
                    playerIsDead = false;
                }
            }
        }

        if(playerIsDead){
            level.setCurrentLives(level.getCurrentLives()-1);
            Coordinate originalMePosition = level.getOriginalMePosition();

            if(level.getCurrentLives() == 0){
                level.setPlayerDead(true);
            } else {
                map[originalMePosition.getY()][originalMePosition.getX()].getGegenstand().setToken(Type.ME);
            }

        }
    }

    public static Coordinate computeMePosition(Field[][] map){;
        Coordinate mePosition = new Coordinate();

        int numberOfRows = map.length;
        int numberOfColumns = map[0].length;

        for(int rowCounter = 0; rowCounter < numberOfColumns; rowCounter++) {

            for (int columnCounter = 0; columnCounter < numberOfRows; columnCounter++) {

                if(map[columnCounter][rowCounter].getType() == Type.ME){
                    mePosition.setX(rowCounter);
                    mePosition.setY(columnCounter);
                }
            }

        }

        return mePosition;
    }

    private static void updateStopCounter(Level level){
        int stopCounter = level.getStopCounter();

        if(level.isStopped() && stopCounter > 0){
            stopCounter--;
        }

        if(stopCounter == 0){
            level.setStopped(false);
        }
    }
}


