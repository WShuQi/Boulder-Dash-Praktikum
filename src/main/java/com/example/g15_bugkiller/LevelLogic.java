package com.example.g15_bugkiller;

import MapGeneration.MapGeneration;

import java.util.List;

public class LevelLogic {


// Grundablauf pro Tick
  //TODO: Sparsity fragen im Tutorium



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

        if (situation == Situation.ANY){
            situationOccurs = true;
        } else if (situation == Situation.RARE){
            int sparsity = 1; //TODO: integrate sparsity in input data
            if (level.getTicksPast() % sparsity == 0){
                situationOccurs = true;
            }
        }
        //TODO:  implement other situations....

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
    }


    public static void executePrePostRuleNorthward(Regel rule, Level level){
    }


    public static void executePrePostRuleSouthward(Regel rule, Level level){
    }

    public static boolean checkIfNextFieldsAndOriginalsAgree(Field[] nextFields, List<Regelbaustein> original){
        boolean nextFieldsAndOriginalsAgree = true;
        int numberOfOriginals = original.size();

        for(int componentCounter = 0; componentCounter < numberOfOriginals; componentCounter++){

            Gegenstand currentGegenstand = nextFields[componentCounter].getGegenstand();
            Object currentToken = original.get(componentCounter).getToken();
            Values currentValues = original.get(componentCounter).getValues();

            if(currentToken.getClass() == String.class){

                //if(!currentToken.equals('*') || !currentToken.equals(currentGegens)){}
            }


        }

        return nextFieldsAndOriginalsAgree;
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
