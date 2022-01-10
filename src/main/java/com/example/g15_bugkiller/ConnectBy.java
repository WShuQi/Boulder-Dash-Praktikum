package com.example.g15_bugkiller;

import java.util.ArrayList;
import java.util.List;

public class ConnectBy {

    private Coordinate from;
    private Coordinate to;
    private List<Field> uses = new ArrayList<Field>();

    public ConnectBy(Coordinate from, Coordinate to, List<Field> uses) {
        this.from = from;
        this.to = to;
        this.uses = uses;
    }

    public Coordinate getFrom() {
        return from;
    }

    public void setFrom(Coordinate from) {
        this.from = from;
    }

    public Coordinate getTo() {
        return to;
    }

    public void setTo(Coordinate to) {
        this.to = to;
    }

    public List<Field> getUses() {
        return uses;
    }
}
