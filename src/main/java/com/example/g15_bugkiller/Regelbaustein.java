package com.example.g15_bugkiller;

public class Regelbaustein {

    Object token;       // could be Type (incl CATCHALL), Type[], int
    Values values;

    public Regelbaustein (Object token, Values values){
        this.token = token;
        this.values = values;
    }

    public Regelbaustein (Object token){
        this.token = token;
        this.values = new Values();
    }

    public Object getToken() {
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
