package MapGeneration;
import com.example.g15_bugkiller.*;
import org.json.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Gamesaver {

    private String path;

    public Gamesaver(String path) {
        this.path = path;
    }

    private String getGameData(Game game) {
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
        return gamejobj.toString();
    }

    private String getLevelData(Level level) {
        JSONObject leveljobj = new JSONObject();

        int[] gems = level.getGems();
        JSONArray gemsjarr = new JSONArray();
        for (int i : gems) {
            gemsjarr.put(gems[i]);
        }
        leveljobj.put("gems", gemsjarr);

        String levelName = level.getLevelName();
        leveljobj.put("name", levelName);

        int[] ticks = level.getTicks();
        JSONArray ticksjarr = new JSONArray();
        for (int i : ticks) {
            ticksjarr.put(ticks[i]);
        }
        leveljobj.put("ticks", ticksjarr);

        JSONArray preRules = level.getPre();
        leveljobj.put("pre", preRules);

        JSONArray postRules = level.getPost();
        leveljobj.put("post", postRules);

        int lives = level.getLives();
        leveljobj.put("lives", lives);

        int maxSlime = level.getMaxSlime();
        leveljobj.put("maxslime", maxSlime);

        Input mapdata = level.getMapdata();
        JSONObject mapdatajobj = new JSONObject();
        int width = mapdata.getMapSize().getX();
        mapdatajobj.put("width", width);
        int height = mapdata.getMapSize().getY();
        mapdatajobj.put("height", height);

        List<Tile> tiles = mapdata.getTiles();
        for (Tile tile : tiles) {
            String name = tile.getName();
            List<TileVersion> tileVersions = tile.getVersions();
        }

        return leveljobj.toString();
    }


    public void createJson(Game game, String fileName) {
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
            writer.write(this.getGameData(game));
            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("Save failed:(");
            e.printStackTrace();
        }
        System.out.println("Fortschritte werden erfolgreich gespeichert!");
    }

    public void readGameData(Game game) throws FileNotFoundException {
        Json json = new Json(path);
        JSONObject gameDataJobj = json.getJson();
        game.setNumberOfUnlockedLevels(gameDataJobj.getInt("numberOfUnlockedLevels"));
        game.setTotalPoints(gameDataJobj.getInt("totalpoints"));
        Map<String, Level> levels = game.getLevels();
        JSONObject levelsobj = gameDataJobj.getJSONObject("levels");
        for (String name : levelsobj.keySet()) {
            levels.get(name).setBestGems(levelsobj.getJSONObject(name).getInt("gems"));
            levels.get(name).setBestTime(levelsobj.getJSONObject(name).getInt("besttime"));
            levels.get(name).setScoredPoints(levelsobj.getJSONObject(name).getInt("scoredPoints"));
            levels.get(name).setUnlocked(levelsobj.getJSONObject(name).getBoolean("unlocked"));
        }
    }

}

