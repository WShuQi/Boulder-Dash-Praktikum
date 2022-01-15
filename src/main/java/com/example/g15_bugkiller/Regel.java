package com.example.g15_bugkiller;

import java.util.List;

public class Regel {

    Situation situation;
    Direction direction;
    List<Regelbaustein> original;
    List<Regelbaustein> result;


    public Regel (Situation situation, Direction direction, List<Regelbaustein> original, List<Regelbaustein> result){
        this.situation = situation;
        this.direction = direction;
        this.original = original;
        this.result = result;
    }

    public Situation getSituation(){
        return situation;
    }

    public Direction getDirection(){
        return direction;
    }

    public List<Regelbaustein> getOriginal(){
        return original;
    }

    public List<Regelbaustein> getResult(){
        return result;
    }
}
