package com.example.g15_bugkiller;

import java.util.HashMap;

import static com.example.g15_bugkiller.ValuesNames.*;

public class Values {

    HashMap<ValuesNames, Integer> valueList = new HashMap<ValuesNames, Integer>();

    public Values(){
        valueList = new HashMap<ValuesNames, Integer>();
    }


    public Values(HashMap<ValuesNames, Integer> initalValueList){
        valueList = initalValueList;
    }

    public void resetValues(){

        valueList.put(MOVED, 0);
        valueList.put(FALLING, 0);
        valueList.put(LOOSE, 0);
        valueList.put(SLIPPERY, 0);
        valueList.put(PUSHABLE, 0);
        valueList.put(BAM, 0);
        valueList.put(BAMRICH, 0);
        valueList.put(TOGEM, 0);
        valueList.put(TOEXPLOSION, 0);
        valueList.put(HASSPACE, 0);
        valueList.put(SLIPDIRECTION, 0);
        valueList.put(CANGROW, 0);
        valueList.put(EXITREACHED, 0);
        valueList.put(STOP, 0);
        valueList.put(STOPBUTTONPRESSED, 0);
        valueList.put(A, 0);
        valueList.put(B, 0);
        valueList.put(C, 0);
        valueList.put(D, 0);
    }

    public HashMap<ValuesNames, Integer> getValueList() {
        return valueList;
    }

    public void setValueList(HashMap<ValuesNames, Integer> valueList) {
        this.valueList = valueList;
    }

    public void setSpecificValue(ValuesNames valuesNames, int valuewert) {
        valueList.put(valuesNames, valuewert);
    }


}

