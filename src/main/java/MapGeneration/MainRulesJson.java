package MapGeneration;

import com.example.g15_bugkiller.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

public class MainRulesJson {

    JSONObject mainRulesListJson;

    public MainRulesJson(String filename) throws  FileNotFoundException{
        InputStream file = new FileInputStream(filename);
        this.mainRulesListJson = new JSONObject(new JSONTokener(file));
    }

    private boolean isType(String text){
        boolean boo = false;
        if (text.toUpperCase().equals("ME") || text.toUpperCase().equals("MUD") || text.toUpperCase().equals("STONE") || text.toUpperCase().equals("BRICKS") || text.toUpperCase().equals("PATH") || text.toUpperCase().equals("EXIT") || text.toUpperCase().equals("WALL") || text.toUpperCase().equals("EXPLOSION") || text.toUpperCase().equals("FIRE") || text.toUpperCase().equals("POT") || text.toUpperCase().equals("SIEVE") || text.toUpperCase().equals("SAND") || text.toUpperCase().equals("SLIME") || text.toUpperCase().equals("SWAPLING") || text.toUpperCase().equals("BLOCKLING") || text.toUpperCase().equals("XLING") || text.toUpperCase().equals("GHOSTLING") || text.toUpperCase().equals("NORTHTHING") || text.toUpperCase().equals("CATCHALL") | text.toUpperCase().equals("LOCH") || text.toUpperCase().equals("WESTTHING") || text.toUpperCase().equals("SOUTHTHING") || text.toUpperCase().equals("EASTTHING")) {
            boo = true;
        }
        return boo;
    }

    public List<Rule> readMainRules() throws JSONException{
        List<Rule> mainRulesList = new ArrayList<>();
        JSONArray mainRulesListArr = mainRulesListJson.getJSONArray("MainRules");
        for(int i = 0; i < mainRulesListArr.length(); i++){
            JSONObject mainRuleJson = mainRulesListArr.getJSONObject(i);
            Situation situation = Situation.valueOf(mainRuleJson.getString("situation").toUpperCase());
            Direction direction = Direction.valueOf(mainRuleJson.getString("direction").toUpperCase());

            List<RuleComponent> original = new ArrayList<>();
            JSONArray originalListJson = mainRuleJson.getJSONArray("original");
            for(int j = 0; j < originalListJson.length(); j++){
                JSONObject originalJson = originalListJson.getJSONObject(j);
                Object token;
                if(originalJson.get("token") instanceof JSONArray) {
                    JSONArray tokenArr = originalJson.getJSONArray("token");
                    List<Type> tokenList = new ArrayList<>();
                    for (int g = 0; g < tokenArr.length(); g++) {
                        tokenList.add(Type.valueOf(tokenArr.getString(g).toUpperCase()));
                    }
                    token = tokenList;

                }
                else {
                    String tokenStr = originalJson.getString("token");
                    if (this.isType(tokenStr)) {
                        token = Type.valueOf(tokenStr.toUpperCase());
                    } else if (tokenStr.equals("*")) {
                        token = Type.CATCHALL;
                    } else if (tokenStr.matches("[0-9]")) {
                        token = Integer.parseInt(tokenStr);
                    } else {
                        token = Type.valueOf(tokenStr.toUpperCase());
                    }
                }

                Values values = new Values();
                if(originalJson.has("values")) {
                    JSONObject valuesJson = originalJson.getJSONObject("values");
                    for(String name : valuesJson.keySet()) {
                        ValuesNames valuesNames = ValuesNames.valueOf(name.toUpperCase());
                        int valuewert = valuesJson.getInt(name);
                        values.setSpecificValue(valuesNames, valuewert);
                    }
                }
                RuleComponent ruleComponent = new RuleComponent(token,values);
                original.add(ruleComponent);
            }

            List<RuleComponent> result = new ArrayList<>();
            JSONArray resultListJson = mainRuleJson.getJSONArray("result");
            for(int j = 0; j < resultListJson.length(); j++){
                JSONObject resultJson = resultListJson.getJSONObject(j);
                Object token;

                if(resultJson.get("token") instanceof JSONArray) {
                    JSONArray tokenArr = resultJson.getJSONArray("token");
                    List<Object> tokenList = new ArrayList<>();
                    for (int g = 0; g < tokenArr.length(); g++) {
                        if(tokenArr.get(g) instanceof Integer){
                            token = tokenArr.getInt(g);
                            tokenList.add(token);
                        }else{
                            String tokenStr = tokenArr.getString(g);
                            if (this.isType(tokenStr)) {
                                token = Type.valueOf(tokenStr.toUpperCase());
                            } else if (tokenStr.equals("*")) {
                                token = Type.CATCHALL;
                            } else if (tokenStr.matches("[0-9]")) {
                                token = Integer.parseInt(tokenStr);
                            } else {
                                token = Type.valueOf(tokenStr.toUpperCase());
                            }
                            tokenList.add(token);
                        }
                    }
                    token = tokenList;

                } else if (resultJson.get("token") == int.class) {
                    token = resultJson.getInt("token");
                } else {
                    String tokenStr = resultJson.getString("token");
                    if (this.isType(tokenStr)) {
                        token = Type.valueOf(tokenStr.toUpperCase());
                    } else if (tokenStr.equals("*")) {
                        token = Type.CATCHALL;
                    } else if (tokenStr.matches("[0-9]")) {
                        token = Integer.parseInt(tokenStr);
                    } else {
                        token = Type.valueOf(tokenStr.toUpperCase());
                    }
                }

                Values values = new Values();
                if(resultJson.has("values")){
                    JSONObject valuesJson = resultJson.getJSONObject("values");
                    for(String name : valuesJson.keySet()) {
                        ValuesNames valuesNames = ValuesNames.valueOf(name.toUpperCase());
                        int valuewert = valuesJson.getInt(name);
                        values.setSpecificValue(valuesNames, valuewert);
                    }
                }
                RuleComponent ruleComponent = new RuleComponent(token,values);
                result.add(ruleComponent);
            }
            Rule mainRule = new Rule(situation,direction,original,result);
            mainRulesList.add(mainRule);
        }
        return mainRulesList;
    }
}
