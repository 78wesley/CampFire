package nl.Wesley.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * All rights reserved.
 * CampFire Created by Wesley on 2/3/2017 on 4:19 PM.
 */
class CampFireBuild {

    public static HashMap<Entity, String> log = new HashMap<>();
    private static ConcurrentHashMap<Entity, String> onFire = new ConcurrentHashMap<>();
    private static CampFireLogFile settings = CampFireLogFile.getInstance();

    private static void setArmorStandSettings(ArmorStand armorStand, String name) {
        armorStand.setBasePlate(false);
        armorStand.setArms(false);
        armorStand.setMarker(false);
        armorStand.setOp(false);
        armorStand.setSilent(true);
        armorStand.setGravity(false);
        armorStand.setGlowing(false);
        armorStand.setGliding(false);
        armorStand.setSmall(false);
        armorStand.setCanPickupItems(false);
        armorStand.setCollidable(false);
        armorStand.setCustomNameVisible(false);
        armorStand.setAI(true);
        armorStand.setVisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setCustomName(name);
        armorStand.setFireTicks(0);
    }

    static void addCampFire(Location loc, String name) {
        Location yaw1 = new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ(),0f, loc.getPitch());
        Location yaw2 = new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ(),45f, loc.getPitch());
        Location yaw3 = new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ(),90f, loc.getPitch());
        Location yaw4 = new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ(),135f, loc.getPitch());
        ArmorStand armorStand1 = (ArmorStand) loc.getWorld().spawnEntity(yaw1, EntityType.ARMOR_STAND);
        ArmorStand armorStand2 = (ArmorStand) loc.getWorld().spawnEntity(yaw2, EntityType.ARMOR_STAND);
        ArmorStand armorStand3 = (ArmorStand) loc.getWorld().spawnEntity(yaw3, EntityType.ARMOR_STAND);
        ArmorStand armorStand4 = (ArmorStand) loc.getWorld().spawnEntity(yaw4, EntityType.ARMOR_STAND);
        setArmorStandSettings(armorStand1, name);
        setArmorStandSettings(armorStand2, name);
        setArmorStandSettings(armorStand3, name);
        setArmorStandSettings(armorStand4, name);
        log.put(armorStand1, name);
        log.put(armorStand2, name);
        log.put(armorStand3, name);
        log.put(armorStand4, name);
    }

    static void removeCampFire(String name) {
        for(Entity e : log.keySet()) {
            if (name.equalsIgnoreCase(e.getCustomName())) {
                e.remove();
            }
        }
    }

    static void deSpawnCampFire() {
        for(Entity e : log.keySet()) {
            e.remove();
        }
    }

    static void loadCampFire() {
        if (settings.getData().isConfigurationSection("locations")) {
            for (String key : settings.getData().getConfigurationSection("locations").getKeys(false)) {
                String world = settings.getData().getString("locations." + key + ".world");
                double x = settings.getData().getDouble("locations." + key + ".x");
                double y = settings.getData().getDouble("locations." + key + ".y");
                double z = settings.getData().getDouble("locations." + key + ".z");
                Boolean fire = settings.getData().getBoolean("locations." + key + ".fire");
                addCampFire(new Location(Bukkit.getWorld(world), x, y, z), key);
                if (fire) {
                    setFire(true, key);
                }
                if (!fire){
                    setFire(false, key);
                }
            }
        }
    }

    static void debugCampfires(){
        for (Entity e : log.keySet()) {
            e.setCustomNameVisible(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(settings.getPlugin(), () -> {
                e.setCustomNameVisible(false);
            },200);
        }
    }

    public static Boolean getFire(String args) {
        Boolean fire = settings.getData().getBoolean("locations." + args + ".fire");
        return fire;
    }

    static void setFire(boolean bool, String name) {
        if (bool) {
            for (Entity entity : log.keySet()) {
                if (name.equalsIgnoreCase(entity.getCustomName())) {
                    onFire.put(entity, name);
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : onFire.keySet()) {
                    if (name.equalsIgnoreCase(entity.getCustomName())) {
                        entity.setFireTicks(20);
                    }
                }
            }
        }.runTaskTimer(settings.getPlugin(), 20, 0);
        if (!bool) {
            for (Entity entity : onFire.keySet()) {
                if (name.equalsIgnoreCase(entity.getCustomName())) {
                    onFire.remove(entity, name);
                }
            }
        }
    }
}
