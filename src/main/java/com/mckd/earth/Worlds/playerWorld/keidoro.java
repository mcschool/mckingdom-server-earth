package com.mckd.earth.Worlds.playerWorld;

import com.mckd.earth.Earth;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class keidoro implements Listener {

    Earth plugin;
    String worldName = "keidoro";

    public keidoro(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
    }
}