package com.example.g15_bugkiller;

import java.util.List;

public class LevelLogic {

// Grundablauf pro Tick

    public static  Field[][] tick (Field[][] map) {

        Field[][] updatedMap = map;

        //loop through map
            updatedMap = zurücksetzen(updatedMap);

        //loop through map
            updatedMap = preRegelnAusführen(updatedMap);

        //loop through map
            updatedMap = hauptregelnAnwenden(updatedMap);

        //loop through map
            updatedMap = postRegelnAusführen(updatedMap);

            return updatedMap;
    }

    public static Field[][] zurücksetzen(Field[][] map){
        //ToDo Zurücksetzen der Values aller Felder entsprechend ihrer Bedeutung

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
