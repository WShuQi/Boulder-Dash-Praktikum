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
        valueList.put(COLLECTED, 0);
        valueList.put(ToGEM, 0);
        valueList.put(ToEXPLOSION, 0);
        valueList.put(HasSPACE, 0);
        valueList.put(CanGROW, 0);
    }

    public HashMap<ValuesNames, Integer> getValueList() {
        return valueList;
    }

    public void setValueList(HashMap<ValuesNames, Integer> valueList) {
        this.valueList = valueList;
    }

    public void setSpecificValue(ValuesNames valuesNames, int valuewert) {
        valueList.replace(valuesNames,valuewert);
    }

}

