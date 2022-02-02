package com.example.g15_bugkiller;
import java.util.HashMap;


public class TerminalMap {

    private static HashMap<Type, String> fieldTypes = new HashMap<Type, String>(){
        {
            put(Type.ME, "☺");

            put(Type.MUD, "▒");
            put(Type.STONE, "●");
            put(Type.GEM, "◊");
            put(Type.WALL, "█");
            put(Type.BRICKS, "□");
            put(Type.PATH, "┼");
            put(Type.EXIT, "E");

            put(Type.SLIME, "S");
            put(Type.SWAPLING, "sw");
            put(Type.BLOCKLING, "B");
            put(Type.XLING, "X");
            put(Type.GHOSTLING, "G");

            put(Type.EXPLOSION, "!");
            put(Type.FIRE, "☼");
            put(Type.POT, "Ս");
            put(Type.SIEVE, "֍");
            put(Type.SAND, "҈");
            put(Type.LOCH, " ");
            put(Type.NORTHTHING, "↑");
            put(Type.EASTTHING, "→");
            put(Type.SOUTHTHING, "↓");
            put(Type.WESTTHING, "←");
        }
    };

    public static void drawMap(Field[][] map){

        for(int i=0; i<map[0].length;i++){
            String printRow = "";
           for(int k=0; k<map.length ;k++){
               if(map[k][i] == null){
                   printRow += " ! ";
               }

               else if(map[k][i].getChecked()){
                   printRow += " @ ";
               }

               /*
               else if(map[k][i].getInKachel()){
                   printRow += " @ ";
               }
               */
               else {
                   printRow += " " + fieldTypes.get(map[k][i].getType()) + " ";
               }
           }
            System.out.println(printRow);
        }
    }
}

