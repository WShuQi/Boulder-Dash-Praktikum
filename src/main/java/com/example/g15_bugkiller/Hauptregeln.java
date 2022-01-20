package com.example.g15_bugkiller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.g15_bugkiller.ValuesNames.*;

public class Hauptregeln {

    // Gegner bewegen sich
    // Verschiedene Gegenstände fallen nach unten
    // Herunterfallende Gegenstande können Gegner sowie die Spielfigur erschlagen
    // Schleim breitet sich aus
    // Edelsteine können eingesammelt werden
    // Hat man genügend Edelsteine gesammelt, so kann man ein Level durch einen Ausgang verlassen

/*
    List<Values> values = new List<Values>;
    public static Regel falling = new Regel(
            Situation.ANY,
            Direction.SOUTH,
            List.of( //Original
                    new Regelbaustein(Type.STONE,
                            new Values(new HashMap<ValuesNames, Integer>() {
                                {
                                    put(FALLING, 1);
                                    put(LOOSE, 1);
                                }
                            })),
                    new Regelbaustein(Type.PATH)),
            List.of( //Result
                    new Regelbaustein(Type.PATH),
                    new Regelbaustein(Type.STONE,
                            new Values(new HashMap<ValuesNames, Integer>() {
                                {
                                    put(FALLING, 1);
                                    //put(LOOSE, 1);
                                    put(MOVED, 1)
                                }
                            }))

            )








            );



*/

    public boolean giveTrueWithThreePercentProbability(){
        return (Math.random() <= 0.03);
    }
}