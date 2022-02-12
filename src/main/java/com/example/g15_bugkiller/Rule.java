package com.example.g15_bugkiller;

import java.util.List;

public class Rule {

    String ruleName;
    Situation situation;
    Direction direction;
    List<RuleComponent> original;
    List<RuleComponent> result;
    boolean collectsStop = false;

    public Rule(Situation situation, Direction direction, List<RuleComponent> original, List<RuleComponent> result){
        this.situation = situation;
        this.direction = direction;
        this.original = original;
        this.result = result;
    }

    public Rule(Situation situation, Direction direction, List<RuleComponent> original, List<RuleComponent> result, boolean collectsStop) {
        this.situation = situation;
        this.direction = direction;
        this.original = original;
        this.result = result;
        this.collectsStop = collectsStop;
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


    public boolean isCollectsStop() {
        return collectsStop;
    }

    public void setCollectsStop(boolean collectsStop) {
        this.collectsStop = collectsStop;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
}
