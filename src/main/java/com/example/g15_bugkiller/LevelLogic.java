package com.example.g15_bugkiller;

import java.util.List;

public class LevelLogic {

// Grundablauf pro Tick

    public static Level tick (Level level) {

        level.setTicksPast(level.getTicksPast()+1);

        Level updatedLevel = new Level();

        Field[][] updatedMap = level.getMap();

        //loop through map
            updatedMap = resetValues(updatedMap);

        //loop through map
            updatedMap = preRegelnAusführen(updatedMap);

        //loop through map
            updatedMap = hauptregelnAnwenden(updatedMap);

        //loop through map
            updatedMap = postRegelnAusführen(updatedMap);

            updatedLevel.setMap(updatedMap);

            return updatedLevel;
    }

    public static Field[][] resetValues(Field[][] map){     //Zurücksetzen der Zusatzwerte aller Felder entsprechend ihrer Bedeutung
        for(int y=0; map[y].length>y; y++) {
            for (int x = 0; map[x].length > x; x++) {
                map[y][x].getGegenstand().resetValues();
            }
        }
        return map;
    }

    public static Field[][] preRegelnAusführen(Field[][] map){
        //ToDo Levelregeln pre ausführen

        return map;
    }

    public static Field[][] hauptregelnAnwenden(Field[][] map){
        //ToDo Levelzustand entsprechend der Hauptregeln veränder

        return map;
    }

    public static Field[][] postRegelnAusführen(Field[][] map){
        // ToDo Levelregeln post ausführen

        return map;
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
