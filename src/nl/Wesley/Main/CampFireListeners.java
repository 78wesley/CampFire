package nl.Wesley.Main;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * All rights reserved.
 * CampFire Created by Wesley on 2/3/2017 on 4:19 PM.
 */
public class CampFireListeners implements Listener {

    @EventHandler
    public void onArmorStandBreak(EntityDamageEvent event) {
        if (event.getEntity() != null) {
            for (Entity entity : CampFireBuild.log.keySet()) {
                if (event.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
                    if (entity.getCustomName().equals(event.getEntity().getCustomName())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
