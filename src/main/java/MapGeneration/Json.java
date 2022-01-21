package MapGeneration;


import com.example.g15_bugkiller.Field;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;

import java.util.List;


public class Json {

    String filename;
    JSONObject json;

    public Json (String filename) {this.filename = filename;}

    /**
     * Set the filename.
     * Read file to a (root) json-object "json", which represents the whole file.
     * @throws FileNotFoundException
     * @param filename must be a valid json file name with path
     */

    private void setFilename (String filename) throws FileNotFoundException {
        this.filename = filename;
        //Einlesen von Datei
        InputStream file = new FileInputStream(this.filename);
        this.json = new JSONObject(new JSONTokener(file));
    }

    private ArrayList<Tile> readTiles() throws JSONException{
        //Parsen Variante von Tiles und in Liste speichern
        ArrayList<Tile> tiles =new ArrayList<>();
        String tileName;

        JSONObject tilesListJson = json.getJSONObject("tiles");
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
                        Field field = new Field(fieldListJson.getString(k));
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

    //todo
    private void readMapdata() throws JSONException{
        JSONObject mapDataJson = json.getJSONObject("mapdata");

    }

    private ArrayList<TilesAt> readTilesAts() throws JSONException{

        String kindName;
        int defaultValue;

        //Parsen TilesAt und in einer Liste speichern
        JSONArray tilesAtJson = json.getJSONArray("tilesat");
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

        List<ConnectBy> connectbys = new ArrayList<>();
        if (json.has("connectby")) {
            JSONArray connectbyJson = json.getJSONArray("connectby");
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
                    Field field = new Field(usesJson.getString(j));
                    uses.add(field);
                }
                ConnectBy connectBy = new ConnectBy(from,to,uses);
                connectbys.add(connectBy);
            }
        }
        return connectbys;
    }

    private Field readDefaultField() throws JSONException{
        //Parsen default Tile ("Wiese")
        String defaultField = json.getString("default");
        Field field = new Field(defaultField);
        return field;
    }

    public Input getInput () throws FileNotFoundException {
        this.setFilename(filename);
        List<Tile> tiles = this.readTiles();
        List<TilesAt> tilesAts = this.readTilesAts();
        List<ConnectBy> connectBys = this.readConnectby();
        Field defaultField = this.readDefaultField();
        Coordinate mapsize =;
        Input input = new Input(tiles,tilesAts,connectBys,defaultField,mapsize);
        return input;
    }
}
