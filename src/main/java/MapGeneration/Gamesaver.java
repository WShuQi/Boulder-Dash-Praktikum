package MapGeneration;

import com.example.g15_bugkiller.Game;
import com.example.g15_bugkiller.Level;
import org.json.*;

import java.io.*;
import java.util.Map;

public class Gamesaver {

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

    public void createJson(Game game, String filePath, String fileName) {
        String fullPath = filePath + File.separator + fileName + ".json";
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

}

