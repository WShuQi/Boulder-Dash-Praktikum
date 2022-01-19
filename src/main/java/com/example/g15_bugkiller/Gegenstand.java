package com.example.g15_bugkiller;

import java.util.HashMap;

import static com.example.g15_bugkiller.ValuesNames.*;


public class Gegenstand {

    Type token;
    Values values;


    public Gegenstand(Type token, Values values) {
        this.token = token;
        this.values = values;
    }


    public Type getToken() {
        return token;
    }


    public Values getValues() {
        return values;
    }

    public void setToken(Type token) {
        this.token = token;
    }

    public void setValues(Values values) {
        this.values = values;
    }

    //setter that sets both variables at once

    public void setTokenAndValues(Type token, Values values) {
        this.token = token;
        this.values = values;
    }


/*
    void resetValues(){
        values.resetValues();
        switch (token){
            case GEM:
                values.setLoose(1);
                values.setSlippery(1);
                break;
            case STONE:
                values.setLoose(1);
                values.setSlippery(1);
                values.setPushable(1);
                break;
            case BRICKS:
                values.setSlippery(1);
                break;
        }
    }

     */
    void resetValues(){
        values.getValueList().put(MOVED, 0);
        values.getValueList().put(FALLING, 0);
        values.getValueList().put(LOOSE, 0);
        values.getValueList().put(SLIPPERY, 0);
        values.getValueList().put(PUSHABLE, 0);
        values.getValueList().put(BAM, 0);
        values.getValueList().put(BAMRICH, 0);

        switch (token) {
            case GEM:
                values.getValueList().put(LOOSE, 1);
                values.getValueList().put(SLIPPERY, 1);
                break;
            case STONE:
                values.getValueList().put(LOOSE, 1);
                values.getValueList().put(SLIPPERY, 1);
                values.getValueList().put(PUSHABLE, 1);
                break;
            case BRICKS:
                values.getValueList().put(SLIPPERY, 1);
                break;
        }
    }
}


