package com.example.g15_bugkiller;
import java.util.HashMap;


public class TerminalMap {

    private static HashMap<String, String> fieldTypes = new HashMap<String, String>(){
        {
            put("ostwest", "─");
            put("nordsued", "│");

            put("kurve_nordost", "└");
            put("kurve_suedwest", "┐");
            put("kurve_nordwest", "┘");
            put("kurve_suedost", "┌");

            put("tkreuz_nord", "┴");
            put("tkreuz_sued", "┬");
            put("tkreuz_ost", "├");
            put("tkreuz_west", "┤");

            put("obersee_wiese", "▄");
            put("obersee_ostwest", "╥");
            put("untersee_suedost", "▀╔");
            put("seekurve_suedost", "╔");

            put("kreuzung2", "╬");
            put("kreuzung", "┼");
            put("kurvenkreuzung", "♯");
            put("seekreuzung", "¤");

            put("wiese", "▒");
        }
    };

    public static void drawMap(Field[][] map){
        System.out.println("Drawing Terminal Map");
        //System.out.println("Map-Height: " + map[0].length);
        //System.out.println("Map-Width : " + map.length);
        System.out.println("");


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

