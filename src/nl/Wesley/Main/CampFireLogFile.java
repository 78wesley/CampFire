package nl.Wesley.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URL;

/**
 * All rights reserved.
 * CampFire Created by Wesley on 2/3/2017 on 4:19 PM.
 */
public class CampFireLogFile {

    // http://bukkit.gamepedia.com/Configuration_API_Reference#Basic_Topics

    private static CampFireLogFile instance = new CampFireLogFile();
    private File locationFile;
    private FileConfiguration locationFileData;
    private Plugin plug;
    public static CampFireLogFile getInstance() {
        return instance;
    }

    public void setupData(Plugin plugin){
        plug = plugin;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        if (locationFile == null) {
            locationFile = new File(plugin.getDataFolder(), "Location.yml");
        }
        locationFileData = YamlConfiguration.loadConfiguration(locationFile);
        // Looks for defaults in the jar
        Reader defConfigStream = new InputStreamReader(plugin.getResource("Location.yml"));
        if (defConfigStream == null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            locationFileData.setDefaults(defConfig);
            saveData();
        }
    }

    public void setData(String path, Object value) {
        locationFileData.set(path, value);
    }

    public Object getData(String path) {
        return locationFileData.get(path);
    }

    public boolean containsData(String path) {
        return locationFileData.contains(path);
    }

    public void saveData() {
        try {
            locationFileData.save(locationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadData() {
        locationFileData = YamlConfiguration.loadConfiguration(locationFile);
    }

    public FileConfiguration getData() {
        return locationFileData;
    }

    public Plugin getPlugin(){
        return plug;
    }
}