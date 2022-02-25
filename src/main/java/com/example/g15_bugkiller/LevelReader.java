package com.example.g15_bugkiller;

import MapGeneration.Json;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class LevelReader {

    private String path;
    private final Json mainRulesJson = new Json("src/main/java/com/example/g15_bugkiller/mainRules.json");

    public LevelReader(String path) throws FileNotFoundException {
        this.path = path;
    }

    public Map<String, Level> readLevel() throws FileNotFoundException {
        Map<String, Level> levels = new HashMap<>();
        File file = new File(path);
        File[] levelfiles = file.listFiles();
        for (File f : levelfiles) {
            Json json = new Json(f.getPath());

            Level level = json.getLevel();

            level.setMainRules(mainRulesJson.getMainRules());

            String levelName = level.getLevelName();
            levels.put(levelName, level);
        }
        return levels;
    }
}













