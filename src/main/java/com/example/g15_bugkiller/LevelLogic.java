package com.example.g15_bugkiller;

import java.util.List;

public class LevelLogic {

    Level level;

// Grundablauf pro Tick


    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public static Level tick (Level level) {

        level.setTicksPast(level.getTicksPast()+1);

        Level updatedLevel = level;

        //loop through map
            updatedLevel = resetValues(updatedLevel);

        //loop through map
            updatedLevel = preRegelnAusführen(updatedLevel);

        //loop through map
            updatedLevel = hauptregelnAnwenden(updatedLevel);

        //loop through map
            updatedLevel = postRegelnAusführen(updatedLevel);

            return updatedLevel;
    }

    public static Level resetValues(Level level){     //Zurücksetzen der Zusatzwerte aller Felder entsprechend ihrer Bedeutung
        Field[][] map = level.getMap();
        for(int y=0; map[y].length>y; y++) {
            for (int x = 0; map[x].length > x; x++) {
                map[y][x].getGegenstand().resetValues();
            }
        }
        level.setMap(map);
        return level;
    }

    public static Level preRegelnAusführen(Level level){
        //ToDo Levelregeln pre ausführen

        return level;
    }

    public static Level hauptregelnAnwenden(Level level){
        //ToDo Levelzustand entsprechend der Hauptregeln veränder

        return level;
    }

    public static Level postRegelnAusführen(Level level){
        // ToDo Levelregeln post ausführen

        return level;
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
