package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.event.Listener;

public class PveWorld implements Listener {

    public PveWorld(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
