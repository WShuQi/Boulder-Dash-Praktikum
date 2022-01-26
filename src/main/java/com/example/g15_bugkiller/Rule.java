package com.example.g15_bugkiller;

import java.util.List;

public class Rule {

    Situation situation;
    Direction direction;
    List<RuleComponent> original;
    List<RuleComponent> result;


    public Rule(Situation situation, Direction direction, List<RuleComponent> original, List<RuleComponent> result){
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

    public List<RuleComponent> getOriginal(){
        return original;
    }

    public List<RuleComponent> getResult(){
        return result;
    }
}
