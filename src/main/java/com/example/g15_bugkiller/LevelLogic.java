package com.example.g15_bugkiller;

import javafx.animation.KeyFrame;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LevelLogic {

// TODO: Fragen fürs Tutorium:
//  Sparsity
//  Kann ein Feld pro Tick mehrmals geändert werden?,
//  Levelübersicht?,
//  Integration von Keypresslistener?,
//  Klasse von Token bestimmen? (Unterschied String/int, Wie sieht Stringarray aus?)


    // Grundablauf pro Tick
    public static void tick (Level level) {

        level.setTicksPast(level.getTicksPast()+1);

        resetValues(level);
        executePreRules(level);
        hauptregelnAnwenden(level);
        executePostRules(level);
    }

    public static Level resetValues(Level level){     //Zurücksetzen der Zusatzwerte aller Felder entsprechend ihrer Bedeutung
        Field[][] map = level.getLevelMap();
        for(int y=0; map[y].length>y; y++) {
            for (int x = 0; map[x].length > x; x++) {
                map[y][x].getGegenstand().resetValues();
            }
        }
        level.setLevelMap(map);
        return level;
    }

    public static void executePreRules(Level level){
        List<Regel> preRules = level.getPreRules();
        executeRules(preRules, level);
    }

    public static void hauptregelnAnwenden(Level level){
        //ToDo Levelzustand entsprechend der Hauptregeln verändern, Charis

    }

    public static void executePostRules(Level level){
        List<Regel> postRules = level.getPostRules();
        executeRules(postRules, level);
    }

    public static boolean checkIfSituationOccurs(Situation situation, Level level){
        boolean situationOccurs = false;
        int sparsity = 1;//TODO: integrate sparsity in input data

        if (situation == Situation.ANY || (situation == Situation.RARE && level.getTicksPast() % sparsity == 0) ||
                (situation == Situation.UP && KeyPressListener.isUpPressed()) || (situation == Situation.DOWN && KeyPressListener.isDownPressed()) ||
                (situation == Situation.RIGHT && KeyPressListener.isRightPressed()) || (situation == Situation.LEFT && KeyPressListener.isLeftPressed()) ||
                (situation == Situation.METAUP && KeyPressListener.isMetaUpPressed()) || (situation == Situation.METADOWN && KeyPressListener.isMetaDownPressed()) ||
                (situation == Situation.METARIGHT && KeyPressListener.isMetaRightPressed()) || (situation == Situation.METALEFT && KeyPressListener.isMetaLeftPressed())  ) {

            situationOccurs = true;
        }

        return situationOccurs;
    }

    public static void executeRules(List<Regel> rules, Level level){

        for(Regel rule : rules){

            Situation situation = rule.getSituation();
            Direction direction = rule.getDirection();

            if(!checkIfSituationOccurs(situation, level)){
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

        int numberOfColumns = map.length;
        int numberOfRows = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int rowCounter = 0; rowCounter < numberOfRows; rowCounter++){

            int columnCounter = 0;

            while(columnCounter + numberOfRuleComponents < numberOfColumns){

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

        int numberOfColumns = map.length;
        int numberOfRows = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int rowCounter = 0; rowCounter < numberOfRows; rowCounter++){

            int columnCounter = numberOfColumns-1;

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

        int numberOfRows = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int columnCounter = 0; columnCounter < numberOfRows; columnCounter++){

            int rowCounter = numberOfRows-1;

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

        int numberOfRows = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int columnCounter = 0; columnCounter < numberOfRows; columnCounter++){

            int rowCounter = 0;

            while(rowCounter + numberOfRuleComponents < numberOfRows){

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

                if(!currentOriginalToken.equals('*') || !currentOriginalToken.equals(currentGegenstand.getToken()) || !valuesAgree(currentGegenstand.getValues().getValueList(), currentOriginalValues.getValueList())){
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
               currentField.getGegenstand().getValues().getValueList().put(valueName, currentResultComponentValue);
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
