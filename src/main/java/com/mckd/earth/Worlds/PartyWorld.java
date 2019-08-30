package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.event.Listener;

public class PartyWorld implements Listener {
    Earth plugin;
    public PartyWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
