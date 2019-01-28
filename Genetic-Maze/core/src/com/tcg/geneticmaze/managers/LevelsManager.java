package com.tcg.geneticmaze.managers;

import com.badlogic.gdx.Files;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jose de Jesus Rodriguez Rivas
 * @version 1/22/19
 */
public class LevelsManager {

    private static Map<String, LevelMap> levelMaps;
    private static boolean loaded = false;

    public static void init(Files files) {
        if(!loaded) {
            levelMaps = new HashMap<String, LevelMap>();
            JSONObject levelsFile = new JSONObject(files.internal("values/levels.json").readString("utf-8"));
            JSONArray levels = levelsFile.getJSONArray("levels");

            for (int i = 0; i < levels.length(); i++) {
                JSONObject level = levels.getJSONObject(i);
                String key = level.getString("key");
                String name = level.getString("name");
                String path = level.getString("path");
                levelMaps.put(key, new LevelMap(key, name, path));
            }

            loaded = true;
        }
    }

    public static LevelMap getMap(String key) {
        if(!loaded) {
            throw new IllegalStateException("Levels not yet loaded");
        }
        return levelMaps.get(key);
    }

    public static class LevelMap {
        private final String key;
        private final String name;
        private final String path;

        LevelMap(String key, String name, String path) {
            this.key = key;
            this.name = name;
            this.path = path;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }
    }

}
