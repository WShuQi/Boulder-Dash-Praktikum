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


public class Json {

    private JSONObject json;


    public Json(String filename) throws FileNotFoundException {
        InputStream file = new FileInputStream(filename);
        this.json = new JSONObject(new JSONTokener(file));
    }

    private boolean isType(String text) {
        boolean boo = false;
        if (text.toUpperCase().equals("ME") || text.toUpperCase().equals("MUD") || text.toUpperCase().equals("STONE") || text.toUpperCase().equals("BRICKS") || text.toUpperCase().equals("PATH") || text.toUpperCase().equals("EXIT") || text.toUpperCase().equals("WALL") || text.toUpperCase().equals("EXPLOSION") || text.toUpperCase().equals("FIRE") || text.toUpperCase().equals("POT") || text.toUpperCase().equals("SIEVE") || text.toUpperCase().equals("SAND") || text.toUpperCase().equals("SLIME") || text.toUpperCase().equals("SWAPLING") || text.toUpperCase().equals("BLOCKLING") || text.toUpperCase().equals("XLING") || text.toUpperCase().equals("GHOSTLING") || text.toUpperCase().equals("NORTHTHING") || text.toUpperCase().equals("CATCHALL") | text.toUpperCase().equals("LOCH") || text.toUpperCase().equals("WESTTHING") || text.toUpperCase().equals("SOUTHTHING") || text.toUpperCase().equals("EASTTHING") || text.toUpperCase().equals("GEM") || text.toUpperCase().equals("STOPBUTTON")) {
            boo = true;
        }
        return boo;
    }

    private Object readToken(JSONObject jObjToken) throws JSONException {
        Object token = new Object();
        try {
            JSONArray tokenArr = jObjToken.getJSONArray("token");
            //System.out.println("This token contains a list.");
            List<Object> tokenList = new ArrayList<>();
            for (int g = 0; g < tokenArr.length(); g++) {
                Object thisToken = new Object();
                if (tokenArr.get(g) instanceof Integer) {
                    //System.out.println("This Token is Int.");
                    thisToken = tokenArr.getInt(g);
                    //System.out.println(thisToken);
                } else {
                    String thisTokenStr = tokenArr.getString(g);
                    if (this.isType(thisTokenStr)) {
                        //  System.out.println("This token is Type");
                        thisToken = Type.valueOf(thisTokenStr.toUpperCase());
                        // System.out.println(thisToken);
                    } else if (thisTokenStr.equals("*")) {
                        // System.out.println("This token is *");
                        thisToken = Type.CATCHALL;
                        // System.out.println(thisToken);
                    } else if (thisTokenStr.matches("[0-9]")) {
                        // System.out.println("This token is a string of number");
                        thisToken = Integer.parseInt(thisTokenStr);
                        // System.out.println(thisToken);
                    } else {
                        System.out.println(thisTokenStr);
                        System.out.println("This token is not Type or * or \"integer\"");
                    }
                }
                tokenList.add(thisToken);
            }
            System.out.println(tokenList);
            token = tokenList;
        } catch (JSONException e) {
            if (jObjToken.get("token") instanceof Integer) {
                token = jObjToken.getInt("token");
                //System.out.println("Token is a int");
                //System.out.println(token);
            } else {
                String tokenStr = jObjToken.getString("token");
                //System.out.println("Token is a String...");
                //System.out.println(token);
                if (this.isType(tokenStr)) {
                    token = Type.valueOf(tokenStr.toUpperCase());
                    // System.out.println("Token is a Type");
                    // System.out.println(token);
                } else if (tokenStr.equals("*")) {
                    token = Type.CATCHALL;
                    // System.out.println("Token is *");
                    // System.out.println(token);
                } else if (tokenStr.matches("[0-9]")) {
                    //System.out.println("Token is a string of number");
                    token = Integer.parseInt(tokenStr);
                    //System.out.println(token);
                } else {
                    System.out.println(tokenStr);
                    System.out.println("Token is not Type or * or \"integer\"");
                }
            }
        }
        return token;
    }

    private Values readValues(JSONObject jobjValues) throws JSONException {
        Values values = new Values();
        JSONObject valuesJson = jobjValues.getJSONObject("values");
        for (String name : valuesJson.keySet()) {
            ValuesNames valuesNames = ValuesNames.valueOf(name.toUpperCase());
            int valuewert = valuesJson.getInt(name);
            values.setSpecificValue(valuesNames, valuewert);
        }
        return values;
    }

    private List<RuleComponent> readOriginalOrResult(JSONObject jobj, String keyword) {
        List<RuleComponent> ruleComponentList = new ArrayList<>();
        JSONArray keywordListJson = jobj.getJSONArray(keyword);
        for (int j = 0; j < keywordListJson.length(); j++) {
            JSONObject keywordJson = keywordListJson.getJSONObject(j);
            Object token = this.readToken(keywordJson);
            RuleComponent ruleComponent;
            if (keywordJson.has("values")) {
                Values values = this.readValues(keywordJson);
                ruleComponent = new RuleComponent(token, values);
            } else {
                ruleComponent = new RuleComponent(token);
            }
            ruleComponentList.add(ruleComponent);
        }
        return ruleComponentList;
    }

    private Situation readSituation(JSONObject jsonObject) {
        Situation situation = Situation.valueOf(jsonObject.getString("situation").toUpperCase());
        return situation;
    }

    private String readRuleName(JSONObject jsonObject) {
        if (jsonObject.has("name")) {
            return jsonObject.getString("name");
        }
        return null;
    }

    private Direction readDirection(JSONObject jsonObject) {
        Direction direction = Direction.valueOf(jsonObject.getString("direction").toUpperCase());
        return direction;
    }

    private Coordinate readMapdata() throws JSONException {
        JSONObject mapDataJson = json.getJSONObject("mapdata");
        int width = mapDataJson.getInt("width");
        int height = mapDataJson.getInt("height");
        Coordinate mapsize = new Coordinate(width, height);
        return mapsize;
    }

    private ArrayList<Tile> readTiles() {
        JSONObject mapDataJson = json.getJSONObject("mapdata");
        JSONObject tilesListJson = mapDataJson.getJSONObject("tiles");
        //Parsen Variante von Tiles und in Liste speichern
        ArrayList<Tile> tiles =new ArrayList<>();
        String tileName;

        for (String name : tilesListJson.keySet()) {
            tileName = name;
            JSONArray tileJson = tilesListJson.getJSONArray(name);

            //Tileversion parsen und in Liste speichern
            List<TileVersion> versions = new ArrayList<>();

            for(int i = 0; i < tileJson.length(); i++){
                JSONArray versionJson = tileJson.getJSONArray(i);
                ArrayList<List<Field>> entries = new ArrayList<>();

                for(int j = 0; j < versionJson.length(); j++){
                    ArrayList<Field> fieldsList = new ArrayList<>();
                    JSONArray fieldListJson = versionJson.getJSONArray(j);

                    for (int k = 0; k < fieldListJson.length(); k++){
                        Type token;
                        Values values = new Values();
                        try{
                            if(fieldListJson.getString(k).equals("-")){
                                token = Type.LOCH;
                            }
                            else{
                                token = Type.valueOf(fieldListJson.getString(k).toUpperCase());
                            }
                        }
                        catch (JSONException e){
                            JSONObject tokenAndValues = fieldListJson.getJSONObject(k);
                            token = Type.valueOf(tokenAndValues.getString("token").toUpperCase(Locale.ROOT));
                            values = new Values();
                            JSONObject valuesJson = tokenAndValues.getJSONObject("values");
                            for(String valueName : valuesJson.keySet()) {
                                try {
                                    ValuesNames valuesNames = ValuesNames.valueOf(valueName.toUpperCase());
                                    int valuewert = valuesJson.getInt(valueName);
                                    values.setSpecificValue(valuesNames, valuewert);
                                } catch (IllegalArgumentException exc) {
                                    System.out.println(valueName);
                                }
                            }
                        }
                        Gegenstand gegenstand = new Gegenstand(token,values);
                        Field field = new Field(gegenstand);
                        fieldsList.add(field);
                    }entries.add(fieldsList);
                }
                TileVersion version = new TileVersion(entries);
                versions.add(version);
            }
            Tile tile = new Tile(tileName,versions);
            tiles.add(tile);
        }
        return tiles;
    }

    private ArrayList<TilesAt> readTilesAts() throws JSONException{

        String kindName;
        int defaultValue;

        //Parsen TilesAt und in einer Liste speichern
        JSONObject mapDataJson = json.getJSONObject("mapdata");
        JSONArray tilesAtJson = mapDataJson.getJSONArray("tilesat");
        ArrayList<TilesAt> tilesAts = new ArrayList<>();

        for(int i = 0; i < tilesAtJson.length(); i++){
            JSONObject obj = tilesAtJson.getJSONObject(i);
            int x = obj.getInt("x");
            int y = obj.getInt("y");
            kindName = obj.getString("kind");
            defaultValue = obj.getInt("default");
            Coordinate coordinate = new Coordinate(x,y);

            TilesAt tilesAt = new TilesAt(coordinate,kindName,defaultValue);
            tilesAts.add(tilesAt);
        }
        return tilesAts;
    }

    private List<ConnectBy> readConnectby() throws JSONException{
        //falls connectby nicht existiert?  --> returns an empty list
        //Bitte bei Formulierung von Logik nicht vergessen zu prüfen, ob connectby existiert

        JSONObject mapDataJson = json.getJSONObject("mapdata");
        List<ConnectBy> connectbys = new ArrayList<>();
        if (mapDataJson.has("connectby")) {
            JSONArray connectbyJson = mapDataJson.getJSONArray("connectby");
            for(int i = 0; i < connectbyJson.length(); i++){

                JSONObject obj = connectbyJson.getJSONObject(i);

                JSONArray fromJson = obj.getJSONArray("from");
                int fromx = fromJson.getInt(0);
                int fromy = fromJson.getInt(1);
                Coordinate from = new Coordinate(fromx,fromy);

                JSONArray toJson = obj.getJSONArray("to");
                int tox = toJson.getInt(0);
                int toy = toJson.getInt(1);
                Coordinate to = new Coordinate(tox,toy);

                JSONArray usesJson = obj.getJSONArray("uses");
                List<Field> uses = new ArrayList<>();
                for (int j = 0; j< usesJson.length(); j++){
                    Type token = Type.valueOf(usesJson.getString(j).toUpperCase(Locale.ROOT));
                    Values values = new Values();
                    Gegenstand gegenstand = new Gegenstand(token,values);
                    Field field = new Field(gegenstand);
                    uses.add(field);
                }
                ConnectBy connectBy = new ConnectBy(from,to,uses);
                connectbys.add(connectBy);
            }
        }
        return connectbys;
    }

    private Field readDefaultField() throws JSONException{
        JSONObject mapDataJson = json.getJSONObject("mapdata");
        Type token = Type.valueOf(mapDataJson.getString("default").toUpperCase());
        Values values = new Values();
        Gegenstand gegenstand = new Gegenstand(token,values);
        Field field = new Field(gegenstand);
        return field;
    }

    private Input getInput () {
        List<Tile> tiles = this.readTiles();
        List<TilesAt> tilesAts = this.readTilesAts();
        List<ConnectBy> connectBys = this.readConnectby();
        Field defaultField = this.readDefaultField();
        Coordinate mapsize = this.readMapdata();
        Input input = new Input(tiles,tilesAts,connectBys,defaultField,mapsize);
        return input;
    }

    private List<Rule> getPreRules(){
        List<Rule> preRules = new ArrayList<>();
        JSONArray prelistJson = json.getJSONArray("pre");
        for(int i = 0; i < prelistJson.length(); i++) {
            JSONObject preJson = prelistJson.getJSONObject(i);
            Situation situation = this.readSituation(preJson);
            Direction direction = this.readDirection(preJson);

            List<RuleComponent> original = this.readOriginalOrResult(preJson, "original");
            List<RuleComponent> result = this.readOriginalOrResult(preJson, "result");
            /*
            List<RuleComponent> original = new ArrayList<>();
            JSONArray originalListJson = preJson.getJSONArray("original");
            for(int j = 0; j < originalListJson.length(); j++){
                JSONObject originalJson = originalListJson.getJSONObject(j);
                Type token = Type.valueOf(originalJson.getString("token").toUpperCase());
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
            JSONArray resultListJson = preJson.getJSONArray("result");
            for(int j = 0; j < resultListJson.length(); j++){
                JSONObject resultJson = resultListJson.getJSONObject(j);
                Type token = Type.valueOf(resultJson.getString("token").toUpperCase());
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
            }*/

            Rule prerule = new Rule(situation,direction,original,result);
            prerule.setRuleName(readRuleName(preJson));
            preRules.add(prerule);
        }
        return preRules;
    }

    private List<Rule> getPostRules(){
        List<Rule> postRules = new ArrayList<>();
        JSONArray postlistJson = json.getJSONArray("post");
        for(int i = 0; i < postlistJson.length(); i++) {
            JSONObject postJson = postlistJson.getJSONObject(i);
            Situation situation = this.readSituation(postJson);
            Direction direction = this.readDirection(postJson);

            /*
            List<RuleComponent> original = new ArrayList<>();
            JSONArray originalListJson = postJson.getJSONArray("original");
            for(int j = 0; j < originalListJson.length(); j++){
                JSONObject originalJson = originalListJson.getJSONObject(j);
                Type token = Type.valueOf(originalJson.getString("token").toUpperCase());
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
            JSONArray resultListJson = postJson.getJSONArray("result");
            for(int j = 0; j < resultListJson.length(); j++){
                JSONObject resultJson = resultListJson.getJSONObject(j);
                Type token = Type.valueOf(resultJson.getString("token").toUpperCase());
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
            }*/
            List<RuleComponent> original = this.readOriginalOrResult(postJson, "original");
            List<RuleComponent> result = this.readOriginalOrResult(postJson, "result");

            Rule postrule = new Rule(situation, direction, original, result);
            postrule.setRuleName(readRuleName(postJson));
            postRules.add(postrule);
        }
        return postRules;
    }

    public Level getLevel() throws JSONException{
        String levelName = json.getString("name");

        int[] gems = new int[3];
        JSONArray gemsJson = json.getJSONArray("gems");
        for(int i = 0; i < gemsJson.length(); i++){
            gems[i] = gemsJson.getInt(i);
        }

        int[] ticks = new int[3];
        try{
            JSONArray ticksJson = json.getJSONArray("ticks");
            for(int j = 0; j < ticksJson.length(); j++){
            ticks[j] = ticksJson.getInt(j);
            }
        } catch (JSONException e) {
            JSONArray ticksJson = json.getJSONArray("time");
            for (int j = 0; j < ticksJson.length(); j++) {
                ticks[j] = ticksJson.getInt(j);
            }
        }
        Input mapdata = this.getInput();

        Level level = new Level(levelName, gems, mapdata, ticks);

        if (json.has("maxslime")) {
            int maxslime = json.getInt("maxslime");
            level.setMaxSlime(maxslime);
        }

        if (json.has("pre")) {
            level.setPreRules(this.getPreRules());
        }
        if (json.has("post")) {
            level.setPostRules(this.getPostRules());
        }
        return level;
    }

    public List<Rule> getMainRules() throws JSONException {
        List<Rule> mainrules = new ArrayList<>();
        JSONArray meinRulesArr = json.getJSONArray("MainRules");
        for (int i = 0; i < meinRulesArr.length(); i++) {
            JSONObject mainruleJson = meinRulesArr.getJSONObject(i);
            Rule mainRule = new Rule(this.readSituation(mainruleJson), this.readDirection(mainruleJson), this.readOriginalOrResult(mainruleJson, "original"), this.readOriginalOrResult(mainruleJson, "result"));
            mainRule.setRuleName(readRuleName(mainruleJson));
            mainrules.add(mainRule);
        }
        return mainrules;
    }
}
