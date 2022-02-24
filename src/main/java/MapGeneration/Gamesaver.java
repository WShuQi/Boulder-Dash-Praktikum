package MapGeneration;
import com.example.g15_bugkiller.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import org.json.*;

import java.io.*;
import java.util.*;

public class Gamesaver {


    private String path;
    private String jsonStr;
    //Egal Fortschritte von Game, oder ein Benutzer-definiertes Level, endlich nur als diese String speichern.

    public Gamesaver(String path) {
        this.path = path;
    }

    public void getGameData(Game game) {
        JSONObject gamejobj = new JSONObject();
        int numberOfUnlockedLevels = game.getNumberOfUnlockedLevels();
        gamejobj.put("numberOfUnlockedLevels", numberOfUnlockedLevels);

        int totalpoints = game.getTotalPoints();
        gamejobj.put("totalpoints", totalpoints);

        JSONObject levelsjobj = new JSONObject();
        Map<String, Level> levels = game.getLevels();
        for (Level level : levels.values()) {
            JSONObject leveljobj = new JSONObject();
            int gems = level.getBestGems();
            leveljobj.put("gems", gems);
            int besttime = level.getBestTime();
            leveljobj.put("besttime", besttime);
            int scoredPoints = level.getBestScore();
            leveljobj.put("scoredPoints", scoredPoints);
            boolean unlocked = level.isUnlocked();
            leveljobj.put("unlocked", unlocked);
            String lName = level.getLevelName();
            levelsjobj.put(lName, leveljobj);
        }
        gamejobj.put("levels", levelsjobj);
        this.jsonStr = gamejobj.toString();
    }

    public void getLevelData(Level level) {
        JSONObject leveljobj = new JSONObject();

        int[] gems = level.getGems();
        System.out.println(gems);
        JSONArray gemsjarr = new JSONArray(gems);
        leveljobj.put("gems", gemsjarr);

        String levelName = level.getLevelName();
        leveljobj.put("name", levelName);

        int[] ticks = level.getTicks();
        JSONArray ticksjarr = new JSONArray(ticks);
        leveljobj.put("ticks", ticksjarr);

        JSONArray preRules = level.getPre();
        leveljobj.put("pre", preRules);

        JSONArray postRules = level.getPost();
        leveljobj.put("post", postRules);

        int lives = level.getLives();
        leveljobj.put("lives", lives);

        int maxSlime = level.getMaxSlime();
        leveljobj.put("maxslime", maxSlime);

        //Mapdata einlesen, davon width,height,tiles,tilesAt,connectbys,default
        Input mapdata = level.getMapdata();
        JSONObject mapdatajobj = new JSONObject();
        int width = mapdata.getMapSize().getX();
        mapdatajobj.put("width", width);
        int height = mapdata.getMapSize().getY();
        mapdatajobj.put("height", height);

        //Tiles stufe für Stufe einlesen
        List<Tile> tiles = mapdata.getTiles();
        JSONObject tilesjobj = new JSONObject();
        for (Tile tile : tiles) {
            String name = tile.getName();
            List<TileVersion> tileVersions = tile.getVersions();
            JSONArray tileversionsjarr = new JSONArray();
            for (TileVersion tileVersion : tileVersions) {
                JSONArray entriesjarr = new JSONArray();
                List<List<Field>> entries = tileVersion.getEntries();
                for (List<Field> fields : entries) {
                    JSONArray fieldsjarr = new JSONArray();

                    for (Field field : fields) {
                        Gegenstand gegenstand = field.getGegenstand();
                        String token = gegenstand.getToken().toString();
                        HashMap<ValuesNames, Integer> values = gegenstand.getValues().getValueList();
                        HashMap<String, Integer> newValues = new HashMap<>();

                        //Nur geänderte Values werden gespeichert
                        for (ValuesNames valuesNames : values.keySet()) {
                            if (values.get(valuesNames) > 0) {
                                newValues.put(valuesNames.toString(), values.get(valuesNames));
                            }
                        }

                        JSONObject valuesjobj = new JSONObject(newValues);

                        //Wenn keine Werte geändert wird, token einfach als String speichern(anstatt JsonObj)
                        if (newValues.isEmpty()) {
                            fieldsjarr.put(token);
                        } else {
                            JSONObject fieldjobj = new JSONObject();
                            fieldjobj.put("token", token);
                            fieldjobj.put("values", valuesjobj);
                            fieldsjarr.put(fieldjobj);
                        }

                    }

                    entriesjarr.put(fieldsjarr);
                }
                tileversionsjarr.put(entriesjarr);
            }
            tilesjobj.put(name, tileversionsjarr);
        }
        mapdatajobj.put("tiles", tilesjobj);

        List<TilesAt> tilesAts = mapdata.getTilesAts();
        JSONArray tilesAtsjarr = new JSONArray();
        for (TilesAt tilesAt : tilesAts) {
            JSONObject tilesAtjobj = new JSONObject();
            tilesAtjobj.put("x", tilesAt.getCoordinate().getX());
            tilesAtjobj.put("y", tilesAt.getCoordinate().getY());
            tilesAtjobj.put("kind", tilesAt.getKindName());
            tilesAtjobj.put("default", tilesAt.getDefaultValue());
            tilesAtsjarr.put(tilesAtjobj);
        }
        mapdatajobj.put("tilesat", tilesAtsjarr);

        List<ConnectBy> connectBIES = mapdata.getConnectBys();
        JSONArray connectBiesjarr = new JSONArray();
        for (ConnectBy connectBy : connectBIES) {
            JSONObject connectbyjobj = new JSONObject();
            JSONArray fromjarr = new JSONArray();
            fromjarr.put(connectBy.getFrom().getX());
            fromjarr.put(connectBy.getFrom().getY());
            connectbyjobj.put("from", fromjarr);

            JSONArray tojarr = new JSONArray();
            tojarr.put(connectBy.getTo().getX());
            tojarr.put(connectBy.getTo().getY());
            connectbyjobj.put("to", tojarr);

            JSONArray usesjarr = new JSONArray();
            for (Field field : connectBy.getUses()) {
                String use = field.getType().toString();
                usesjarr.put(use);
            }
            connectbyjobj.put("uses", usesjarr);
            connectBiesjarr.put(connectbyjobj);
        }
        mapdatajobj.put("commectby", connectBiesjarr);

        mapdatajobj.put("default", mapdata.getDefaultField().getType().toString());

        leveljobj.put("mapdata", mapdatajobj);

        this.jsonStr = leveljobj.toString();
    }


    public void createJson(String fileName) {
        String fullPath = this.path + File.separator + fileName + ".json";
        try {
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(this.jsonStr);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Save failed:(");
            e.printStackTrace();
        }
        System.out.println("Fortschritte werden erfolgreich gespeichert!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Fortschritte werden erfolgreich gespeichert!");
        alert.showAndWait();
    }

    public void readGameData(Game game) {
        try {
            List<String> choices = new ArrayList<>();
            File file = new File(this.path);
            File[] nameFlies = file.listFiles();
            for (File f : nameFlies) {
                String fname = f.getName();
                System.out.println(fname);
                String playername = fname.substring(0, fname.lastIndexOf("."));
                System.out.println(playername);
                choices.add(playername);
            }
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setHeaderText("Für wen wird die Fortschritte geladen? ");
            dialog.setContentText("Bitte Name wählen: ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String filepath = "src/main/java/com/example/g15_bugkiller/SavedGames/" + result.get() + ".json";
                Json json = new Json(filepath);
                JSONObject gameDataJobj = json.getJson();
                game.setNumberOfUnlockedLevels(gameDataJobj.getInt("numberOfUnlockedLevels"));
                game.setTotalPoints(gameDataJobj.getInt("totalpoints"));
                Map<String, Level> levels = game.getLevels();
                JSONObject levelsobj = gameDataJobj.getJSONObject("levels");
                for (String name : levelsobj.keySet()) {
                    try {
                        levels.get(name).setBestGems(levelsobj.getJSONObject(name).getInt("gems"));
                        levels.get(name).setBestTime(levelsobj.getJSONObject(name).getInt("besttime"));
                        levels.get(name).setBestScore(levelsobj.getJSONObject(name).getInt("scoredPoints"));
                        levels.get(name).setUnlocked(levelsobj.getJSONObject(name).getBoolean("unlocked"));
                    } catch (NullPointerException e) {
                        System.out.println("No data of the level " + name);
                        continue;
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Fortschritte werden erfolgreich geladen!");
                alert.showAndWait();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

