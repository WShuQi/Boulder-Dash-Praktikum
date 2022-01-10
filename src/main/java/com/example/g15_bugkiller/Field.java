package com.example.g15_bugkiller;

public class Field {

    private String type;
    private boolean checked;
    private boolean inKachel;

    public Field(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean getChecked() { return checked;}

    public void setChecked() { this.checked = true; }

    public boolean getInKachel() { return inKachel;}

    public void setInKachel() { this.inKachel = true;}
}
