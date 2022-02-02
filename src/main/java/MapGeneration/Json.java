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

    JSONObject json;


    public Json (String filename)throws FileNotFoundException {
        InputStream file = new FileInputStream(filename);
        this.json = new JSONObject(new JSONTokener(file));
    }

    private Coordinate readMapdata() throws JSONException{
        JSONObject mapDataJson = json.getJSONObject("mapdata");
        int width = mapDataJson.getInt("width");
        int height = mapDataJson.getInt("height");
        Coordinate mapsize = new Coordinate(width, height);
        return mapsize;
    }

    private ArrayList<Tile> readTiles(){
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
                            JSONObject valueJson = tokenAndValues.getJSONObject("values");
                            ValuesNames valuesNames = ValuesNames.DIRECTION;
                            int valuewert = valueJson.getInt("direction");
                            values.setSpecificValue(valuesNames,valuewert);
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

    public Input getInput () {
        List<Tile> tiles = this.readTiles();
        List<TilesAt> tilesAts = this.readTilesAts();
        List<ConnectBy> connectBys = this.readConnectby();
        Field defaultField = this.readDefaultField();
        Coordinate mapsize = this.readMapdata();
        Input input = new Input(tiles,tilesAts,connectBys,defaultField,mapsize);
        return input;
    }


    public Level getLevel () throws JSONException{
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
        }
        catch (JSONException e){
            JSONArray ticksJson = json.getJSONArray("time");
            for(int j = 0; j < ticksJson.length(); j++){
                ticks[j] = ticksJson.getInt(j);
            }
        }
        Input mapdata = this.getInput();

        Level level = new Level(levelName,gems,mapdata,ticks);
        return level;
    }
}
