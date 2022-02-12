package com.example.g15_bugkiller;

import MapGeneration.Json;
import MapGeneration.MainRulesJson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class LevelReader {

    private String path;
    private MainRulesJson mainRulesJson = new MainRulesJson("src/main/java/com/example/g15_bugkiller/mainRules.json");

    public LevelReader(String path) throws FileNotFoundException {
        this.path = path;
    }

    public Map<String, Level> readLevel() throws FileNotFoundException {
        Map<String, Level> levels = null;
        File file = new File(path);
        File[] levelfiles = file.listFiles();
        for (File f : levelfiles) {
            Json json = new Json(f.getPath());
            System.out.println("current file: ");
            System.out.println(f.getPath());
            Level level = json.getLevel();
            level.setMainRules(mainRulesJson.readMainRules());
            //String levelName = "name";
            String fileName = f.getName();
            String levelName = fileName.substring(0, fileName.lastIndexOf("."));
            System.out.println("levelname: ");
            System.out.println(levelName);
            levels.put(levelName, level);
        }
        return levels;
    }
}













