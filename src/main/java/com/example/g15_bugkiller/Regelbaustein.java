package com.example.g15_bugkiller;

public class Regelbaustein {

    Object token;       // could be String, String[], int
    Values values;


    public Regelbaustein (Object token, Values values){
        this.token = token;
        this.values = values;
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
