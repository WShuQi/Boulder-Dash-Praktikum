package com.example.g15_bugkiller;

import java.util.HashMap;

import static com.example.g15_bugkiller.ValuesNames.*;

public class Values {

    HashMap<ValuesNames, Integer> valueList = new HashMap<ValuesNames, Integer>();

    public Values(HashMap<ValuesNames, Integer> initalValueList){
        valueList.put(MOVED, initalValueList.getOrDefault(MOVED, 0));
        valueList.put(FALLING, initalValueList.getOrDefault(FALLING, 0));
        valueList.put(LOOSE, initalValueList.getOrDefault(LOOSE, 0));
        valueList.put(SLIPPERY, initalValueList.getOrDefault(SLIPPERY, 0));
        valueList.put(PUSHABLE, initalValueList.getOrDefault(PUSHABLE, 0));
        valueList.put(BAM, initalValueList.getOrDefault(BAM, 0));
        valueList.put(BAMRICH, initalValueList.getOrDefault(BAMRICH, 0));
        valueList.put(DIRECTION, initalValueList.getOrDefault(DIRECTION, 0));
        valueList.put(A, initalValueList.getOrDefault(A, 0));
        valueList.put(B, initalValueList.getOrDefault(B, 0));
        valueList.put(C, initalValueList.getOrDefault(C, 0));
        valueList.put(D, initalValueList.getOrDefault(D, 0));
    }

    public void resetValues(){
        valueList.put(MOVED, 0);
        valueList.put(FALLING, 0);
        valueList.put(LOOSE, 0);
        valueList.put(SLIPPERY, 0);
        valueList.put(PUSHABLE, 0);
        valueList.put(BAM, 0);
        valueList.put(BAMRICH, 0);
    }

    public HashMap<ValuesNames, Integer> getValueList() {
        return valueList;
    }

    public void setValueList(HashMap<ValuesNames, Integer> valueList) {
        this.valueList = valueList;
    }
}
