package com.example.g15_bugkiller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LevelLogic {

// TODO: Fragen fürs Tutorium:
//  Sparsity  -> wsl auf der obersten Ebene gespeichert
//  Kann ein Feld pro Tick mehrmals geändert werden? -> Ja!
//  Levelübersicht? -> Sinnvoll, Gui bauen
//  Integration von Keypresslistener?, -> am besten in Timeline, übergeben als Parameter
//  Klasse von Token bestimmen? (Unterschied String/int, Wie sieht Stringarray aus?) -> siehe Seite 15 oben


    // Grundablauf pro Tick
    public static void tick (Level level, KeyPressListener currentKeysPressed) {

        level.setTicksPast(level.getTicksPast()+1);

        resetValues(level);
        executePreRules(level, currentKeysPressed);
        hauptregelnAnwenden(level, currentKeysPressed);
        executePostRules(level, currentKeysPressed);
        computeScoredPoints(level);
        checkIfTimeIsUp(level);
    }

    public static Level resetValues(Level level){     //Zurücksetzen der Zusatzwerte aller Felder entsprechend ihrer Bedeutung
        Field[][] map = level.getLevelMap();
        int rowLength = map.length;
        int columnLength = map[0].length;

        for(int rowIterator=0; rowIterator < rowLength; rowIterator++) {
            for (int columnIterator = 0; columnIterator < columnLength; columnIterator++) {
                map[rowIterator][columnIterator].getGegenstand().resetValues();
            }
        }
        level.setLevelMap(map);
        return level;
    }


    public static void checkIfTimeIsUp(Level level){
        int maxTicks = level.getTicks()[0];
        int ticksPast = level.getTicksPast();
        level.setTimeUp(ticksPast <= maxTicks);
    }

    public static void computeScoredPoints(Level level){
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

    public static void executePreRules(Level level, KeyPressListener currentKeysPressed){
        List<Regel> preRules = level.getPreRules();
        executeRules(preRules, level, currentKeysPressed);
    }

    public static void hauptregelnAnwenden(Level level, KeyPressListener currentKeysPressed){
        //ToDo Levelzustand entsprechend der Hauptregeln verändern, Charis

    }

    public static void executePostRules(Level level, KeyPressListener currentKeysPressed){
        List<Regel> postRules = level.getPostRules();
        executeRules(postRules, level, currentKeysPressed);
    }

    public static boolean checkIfSituationOccurs(Situation situation, Level level, KeyPressListener currentKeysPressed){
        boolean situationOccurs = false;
//ToDo neue Situation: Situation.CHANCE
        if (situation == Situation.ANY || (situation == Situation.RARE && level.getTicksPast() % level.getSparsity() == 0) ||
                (situation == Situation.UP && currentKeysPressed.isUpPressed()) || (situation == Situation.DOWN && currentKeysPressed.isDownPressed()) ||
                (situation == Situation.RIGHT && currentKeysPressed.isRightPressed()) || (situation == Situation.LEFT && currentKeysPressed.isLeftPressed()) ||
                (situation == Situation.METAUP && currentKeysPressed.isMetaUpPressed()) || (situation == Situation.METADOWN && currentKeysPressed.isMetaDownPressed()) ||
                (situation == Situation.METARIGHT && currentKeysPressed.isMetaRightPressed()) || (situation == Situation.METALEFT && currentKeysPressed.isMetaLeftPressed())  ) {

            situationOccurs = true;
        }

        return situationOccurs;
    }

    public static void executeRules(List<Regel> rules, Level level, KeyPressListener currentKeysPressed){

        for(Regel rule : rules){

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

        }
    }

    public static void executeRuleEastward(Regel rule, Level level){
        List<Regelbaustein> original = rule.getOriginal();
        List<Regelbaustein> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int rowLength = map.length;
        int columnLength = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int rowCounter = 0; rowCounter < rowLength; rowCounter++){

            int columnCounter = 0;

            while(columnCounter + numberOfRuleComponents < columnLength){

                Field[] nextFields = new Field[numberOfRuleComponents];

                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[rowCounter][columnCounter + fieldCounter];
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

    public static void executeRuleWestward(Regel rule, Level level){
        List<Regelbaustein> original = rule.getOriginal();
        List<Regelbaustein> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int rowLength = map.length;
        int columnLength = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int rowCounter = 0; rowCounter < rowLength; rowCounter++){

            int columnCounter = columnLength-1;

            while(columnCounter - numberOfRuleComponents >= 0){

                Field[] nextFields = new Field[numberOfRuleComponents];

                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[rowCounter][columnCounter - fieldCounter];
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

    public static void executeRuleNorthward(Regel rule, Level level){
        List<Regelbaustein> original = rule.getOriginal();
        List<Regelbaustein> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int rowLength = map.length;
        int columnLength = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int columnCounter = 0; columnCounter < columnLength; columnCounter++){

            int rowCounter = rowLength-1;

            while(rowCounter - numberOfRuleComponents >= 0){

                Field[] nextFields = new Field[numberOfRuleComponents];

                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[rowCounter - fieldCounter][columnCounter];
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


    public static void executeRuleSouthward(Regel rule, Level level){
        List<Regelbaustein> original = rule.getOriginal();
        List<Regelbaustein> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int rowLength = map.length;
        int columnLength = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int columnCounter = 0; columnCounter < columnLength; columnCounter++){

            int rowCounter = 0;

            while(rowCounter + numberOfRuleComponents < rowLength){

                Field[] nextFields = new Field[numberOfRuleComponents];

                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[rowCounter + fieldCounter][columnCounter];
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

    public static boolean checkIfNextFieldsAndOriginalsAgree(Field[] nextFields, List<Regelbaustein> original){
        boolean nextFieldsAndOriginalsAgree = true;
        int numberOfOriginals = original.size();

        for(int componentCounter = 0; componentCounter < numberOfOriginals; componentCounter++){

            Gegenstand currentGegenstand = nextFields[componentCounter].getGegenstand();
            Object currentOriginalToken = original.get(componentCounter).getToken();
            Values currentOriginalValues = original.get(componentCounter).getValues();

            if(currentOriginalToken.getClass() == Type.class){

                if(!currentOriginalToken.equals(Type.CATCHALL) || !currentOriginalToken.equals(currentGegenstand.getToken()) || !valuesAgree(currentGegenstand.getValues().getValueList(), currentOriginalValues.getValueList())){
                    nextFieldsAndOriginalsAgree = false;
                }

            } else if (currentOriginalToken.getClass() == Type[].class){

                if(!Arrays.asList((Type[]) currentOriginalToken).contains(currentGegenstand.getToken()) || !valuesAgree(currentGegenstand.getValues().getValueList(), currentOriginalValues.getValueList())){
                    nextFieldsAndOriginalsAgree = false;
                }

            }
        }

        return nextFieldsAndOriginalsAgree;
    }

    public static boolean valuesAgree(HashMap<ValuesNames, Integer> fieldValues, HashMap<ValuesNames, Integer> ruleComponentValues){

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

        for(ValuesNames valueName: ValuesNames.values()){
            if(fieldValues.get(valueName) < ruleComponentValues.get(valueName)){
                fieldValuesAreGreaterOrEqualToRuleComponentValues = false;
                break;
            }
        }

        return (bothAreZero || (fieldValuesAreGreaterOrEqualToRuleComponentValues && ruleComponentValuesArePositive));
    }


    public static void replaceFields(Field[] nextFields, List<Regelbaustein> result) {
        int resultLength = result.size();

        for(int resultIterator = 0; resultIterator < resultLength; resultIterator++){
            Field currentField = nextFields[resultIterator];
            Regelbaustein currentResultComponent = result.get(resultIterator);

            if(currentResultComponent.getToken() == Type.class){
                Type newToken = (Type) currentResultComponent.getToken();
                currentField.getGegenstand().setToken(newToken);
            } else if (currentResultComponent.getToken() == int.class){
                currentField.getGegenstand().setToken(nextFields[(int) currentResultComponent.getToken()].getGegenstand().getToken());
            }

            replaceValues(currentField, currentResultComponent);
        }
    }

    public static void replaceValues(Field currentField, Regelbaustein currentResultComponent) {

        for(ValuesNames valueName: ValuesNames.values()){
            int currentFieldValue = currentField.getGegenstand().getValues().getValueList().get(valueName);
            int currentResultComponentValue = currentResultComponent.getValues().getValueList().get(valueName);

            if(currentResultComponentValue == 0){
                currentField.getGegenstand().getValues().getValueList().put(valueName, 0);
            } else {
                int newFieldValue = java.lang.Math.max(currentResultComponentValue + currentFieldValue,0);
                currentField.getGegenstand().getValues().getValueList().put(valueName, newFieldValue);
            }
        }
    }



// Methoden für Hauptregeln

    // Die Spielfigur kann sich bewegen
    // Gegner bewegen sich
    // Verschiedene Gegenstände fallen nach unten
    // Herunterfallende Gegenstande können Gegner sowie die Spielfigur erschlagen
    // Schleim breitet sich aus
    // Edelsteine können eingesammelt werden
    // Hat man genügend Edelsteine gesammelt, so kann man ein Level durch einen Ausgang verlassen




        /*
        public static void printTypes (Field[][] map) {

            for(int y=0; map[y].length>y; y++){
                for (int x=0; map[x].length>x; x++){

                        System.out.println(map[y][x].getType());
                }
            }
        }
        */
}
