package com.example.g15_bugkiller;

import MapGeneration.MapGeneration;

import java.util.List;

public class LevelLogic {


// Grundablauf pro Tick
// TODO: Sparsity fragen im Tutorium, Kann ein Feld pro Tick mehrmals geändert werden?, Levelübersicht?, Integration von Keypresslistener?, Klasse von Token bestimmen? (Unterschied String/int, Wie sieht Stringarray aus?)



    public static void tick (Level level) {

        level.setTicksPast(level.getTicksPast()+1);

        resetValues(level);

        //loop through map
        executePreRules(level);

        //loop through map
        hauptregelnAnwenden(level);

        //loop through map
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
        //ToDo Levelregeln pre ausführen, Stefan

        List<Regel> preRules = level.getPreRules();

        executePrePostRules(preRules, level);
    }

    public static void hauptregelnAnwenden(Level level){
        //ToDo Levelzustand entsprechend der Hauptregeln verändern, Charis

    }

    public static void executePostRules(Level level){
        // ToDo Levelregeln post ausführen, Stefan
        List<Regel> postRules = level.getPostRules();

        executePrePostRules(postRules, level);
    }

    public static boolean checkIfSituationOccurs(Situation situation, Level level){
        boolean situationOccurs = false;
        int sparsity = 1;//TODO: integrate sparsity in input data
        KeyPressListener keyPressListener = new KeyPressListener(); //TODO: integrate keypress

        if (situation == Situation.ANY || (situation == Situation.RARE && level.getTicksPast() % sparsity == 0) ||
                (situation == Situation.UP && keyPressListener.isUpPressed()) || (situation == Situation.DOWN && keyPressListener.isDownPressed()) ||
                (situation == Situation.RIGHT && keyPressListener.isRightPressed()) || (situation == Situation.LEFT && keyPressListener.isLeftPressed()) ||
                (situation == Situation.METAUP && keyPressListener.isMetaUpPressed()) || (situation == Situation.METADOWN && keyPressListener.isMetaDownPressed()) ||
                (situation == Situation.METARIGHT && keyPressListener.isMetaRightPressed()) || (situation == Situation.METALEFT && keyPressListener.isMetaLeftPressed())  ) {

            situationOccurs = true;
        }

        return situationOccurs;
    }

    public static void executePrePostRules(List<Regel> rules, Level level){

        for(Regel rule : rules){

            Situation situation = rule.getSituation();
            Direction direction = rule.getDirection();

            if(!checkIfSituationOccurs(situation, level)){
                continue;
            }

            switch(direction) {
                case EAST:
                    executePrePostRuleEastward(rule, level);
                    break;
                case WEST:
                    executePrePostRuleWestward(rule, level);
                    break;
                case NORTH:
                    executePrePostRuleNorthward(rule, level);
                    break;
                case SOUTH:
                    executePrePostRuleSouthward(rule, level);
                    break;
            }

        }
    }

    public static void executePrePostRuleEastward(Regel rule, Level level){
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

                    //replaceFields(nextFields, original, result);
                    columnCounter = columnCounter + numberOfRuleComponents;

                } else {
                    columnCounter++;
                }

            }
        }
    }


    public static void executePrePostRuleWestward(Regel rule, Level level){
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

                    //replaceFields(nextFields, original, result);
                    columnCounter = columnCounter - numberOfRuleComponents;

                } else {
                    columnCounter--;
                }

            }
        }
    }


    public static void executePrePostRuleNorthward(Regel rule, Level level){
        List<Regelbaustein> original = rule.getOriginal();
        List<Regelbaustein> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int numberOfColumns = map.length;
        int numberOfRows = map[0].length;
        int numberOfRuleComponents = original.size();

        for(int columnCounter = 0; columnCounter < numberOfRows; columnCounter++){

            int rowCounter = numberOfRows-1;

            while(rowCounter + numberOfRuleComponents < numberOfRows){

                Field[] nextFields = new Field[numberOfRuleComponents];

                for(int fieldCounter = 0; fieldCounter < numberOfRuleComponents; fieldCounter++){
                    nextFields[fieldCounter] = map[rowCounter - fieldCounter][columnCounter];
                }

                if(checkIfNextFieldsAndOriginalsAgree(nextFields, original)){

                    //replaceFields(nextFields, original, result);
                    rowCounter = rowCounter - numberOfRuleComponents;

                } else {
                    rowCounter--;
                }

            }
        }
    }


    public static void executePrePostRuleSouthward(Regel rule, Level level){
        List<Regelbaustein> original = rule.getOriginal();
        List<Regelbaustein> result = rule.getResult();
        Field[][] map = level.getLevelMap();

        int numberOfColumns = map.length;
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

                    //replaceFields(nextFields, original, result);
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

            if(currentOriginalToken.getClass() == String.class){

                if(!currentOriginalToken.equals('*') || !currentOriginalToken.equals(currentGegenstand.getToken()) || !valuesAgree(currentGegenstand.getValues(), currentOriginalValues)){
                    nextFieldsAndOriginalsAgree = false;
                }

            } else if (currentOriginalToken.getClass() == String[].class){
                int lengthOfCurrentOriginalToken = ((String[]) currentOriginalToken).length;

                for(int currentOriginalTokenIterator = 0; currentOriginalTokenIterator < lengthOfCurrentOriginalToken; currentOriginalTokenIterator++){

                    if(!((String[]) currentOriginalToken)[currentOriginalTokenIterator].equals(currentGegenstand.getToken()) || !valuesAgree(currentGegenstand.getValues(), currentOriginalValues)){
                        nextFieldsAndOriginalsAgree = false;
                    }
                }
            }
        }

        return nextFieldsAndOriginalsAgree;
    }

    public static boolean valuesAgree(Values fieldValues, Values ruleComponentValues){

        boolean bothIsZero = fieldValues.moved == 0 && ruleComponentValues.getMoved() == 0 && fieldValues.falling == 0 &&  ruleComponentValues.getFalling() == 0 &&
                                fieldValues.loose == 0 && ruleComponentValues.getLoose() == 0 && fieldValues.slippery == 0 && ruleComponentValues.getSlippery() == 0 &&
                                fieldValues.pushable == 0 && ruleComponentValues.getPushable() == 0 && fieldValues.bam == 0 && ruleComponentValues.getBam() == 0  &&
                                fieldValues.bamrich == 0 && ruleComponentValues.getBamrich() == 0 && fieldValues.A == 0 && ruleComponentValues.getA() == 0 &&
                                fieldValues.B == 0 && ruleComponentValues.getB() == 0 && fieldValues.C == 0 && ruleComponentValues.getC() == 0 && fieldValues.D == 0 && ruleComponentValues.getD() == 0;

        boolean ruleComponentValueIsPositive = ruleComponentValues.getMoved() > 0 && ruleComponentValues.getFalling() > 0 &&
                                                ruleComponentValues.getLoose() > 0 && ruleComponentValues.getSlippery() > 0 &&
                                                ruleComponentValues.getPushable() > 0 && ruleComponentValues.getBam() > 0  &&
                                                ruleComponentValues.getBamrich() > 0 && ruleComponentValues.getA() > 0 &&
                                                ruleComponentValues.getB() > 0 && ruleComponentValues.getC() > 0 && ruleComponentValues.getD() > 0;

        boolean fieldValueIsGreaterOrEqualToRuleComponentValue = fieldValues.moved >= ruleComponentValues.getMoved() && fieldValues.falling >=  ruleComponentValues.getFalling() &&
                                                                    fieldValues.loose >= ruleComponentValues.getLoose() && fieldValues.slippery >=ruleComponentValues.getSlippery() &&
                                                                    fieldValues.pushable >= ruleComponentValues.getPushable() && fieldValues.bam >= ruleComponentValues.getBam()  &&
                                                                    fieldValues.bamrich >= ruleComponentValues.getBamrich() && fieldValues.A >= ruleComponentValues.getA() &&
                                                                    fieldValues.B >= ruleComponentValues.getB() && fieldValues.C >= ruleComponentValues.getC() && fieldValues.D >= ruleComponentValues.getD();

        return bothIsZero || (fieldValueIsGreaterOrEqualToRuleComponentValue && ruleComponentValueIsPositive);
    }

 //   public static void  replaceFields(Field[] nextFields, List<Regelbaustein> original, List<Regelbaustein> results){}


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
