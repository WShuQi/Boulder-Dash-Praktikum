package com.example.g15_bugkiller;

import java.util.List;

public class Rule {

    Situation situation;
    Direction direction;
    List<RuleComponent> original;
    List<RuleComponent> result;
    boolean executedWhenStopped = true;
    boolean collectsStop = false;

    public Rule(Situation situation, Direction direction, List<RuleComponent> original, List<RuleComponent> result){
        this.situation = situation;
        this.direction = direction;
        this.original = original;
        this.result = result;
    }

    public Rule(Situation situation, Direction direction, List<RuleComponent> original, List<RuleComponent> result, boolean executedWhenStopped, boolean collectsStop) {
        this.situation = situation;
        this.direction = direction;
        this.original = original;
        this.result = result;
        this.executedWhenStopped = executedWhenStopped;
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

    public boolean isExecutedWhenStopped() {
        return executedWhenStopped;
    }

    public void setExecutedWhenStopped(boolean executedWhenStopped) {
        this.executedWhenStopped = executedWhenStopped;
    }

    public boolean isCollectsStop() {
        return collectsStop;
    }

    public void setCollectsStop(boolean collectsStop) {
        this.collectsStop = collectsStop;
    }
}
