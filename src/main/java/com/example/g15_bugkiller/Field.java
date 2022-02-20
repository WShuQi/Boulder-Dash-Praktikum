package com.example.g15_bugkiller;

public class Field implements Cloneable {

    private boolean checked;
    Gegenstand gegenstand;

    public Field(Gegenstand gegenstand) { this.gegenstand = gegenstand;}

    public Field(boolean checked, Gegenstand gegenstand) {
        this.checked = checked;
        this.gegenstand = gegenstand;
    }

    public Type getType() {
        return gegenstand.getToken();
    }

    public boolean getChecked() { return checked;}

    public void setChecked() { this.checked = true;}

    public Gegenstand getGegenstand(){
        return gegenstand;
    }

    public Field clone() {
        return new Field(checked, getGegenstand().clone());
    }
}
