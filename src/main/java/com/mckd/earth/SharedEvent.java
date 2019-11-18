package com.mckd.earth;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class SharedEvent implements Listener {

    public SharedEvent(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        System.out.println("=== PlayerChangedWorldEvent ====================");
        System.out.println(e.getPlayer().getWorld().getName());
    }

}
