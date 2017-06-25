package nl.Wesley.Main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

/**
 * All rights reserved.
 * CampFire Created by Wesley on 2/3/2017 on 4:19 PM.
 */
public class CampFire extends JavaPlugin {

    //TODO Players can change te name so i cant delete the ArmorStand if de CustomName is changed.
    //TODO MAYBE REMOVE AND ADD WITH THE UUID !
    //TODO ^Create a custom id for removing. or player can't change the ArmorStand customName idk how.
    //TODO Send the Player a custom package for only showing him the ArmorStand customName;

    private Logger log = Logger.getLogger("minecraft");
    private CampFireLogFile settings = CampFireLogFile.getInstance();

    @Override
    public void onEnable() {
        log.info("[CampFire] Has been Enabled");
        settings.setupData(this);
        CampFireBuild.loadCampFire();
        getCommand("campfire").setExecutor(new CampFireCommands());
        Bukkit.getPluginManager().registerEvents(new CampFireListeners(),this);
    }

    @Override
    public void onDisable() {
        log.info("[CampFire] Has been Disabled");
        CampFireBuild.deSpawnCampFire();
        settings.saveData();
    }
}
