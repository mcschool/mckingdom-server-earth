package com.mckd.earth.Worlds.playerWorld;

import com.mckd.earth.Earth;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArcherBattle implements Listener {

    Earth plugin;
    String worldName = "ArcherBattle";

    public ArcherBattle(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


}


