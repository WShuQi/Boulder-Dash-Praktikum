package com.example.g15_bugkiller;

public class Gegenstand {

    Type token;
    Values values;

    public Gegenstand (Type token, Values values){
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
}
