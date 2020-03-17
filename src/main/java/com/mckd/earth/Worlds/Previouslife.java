package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class Previouslife implements Listener {
    public Previouslife(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals(this.worldName));
    }
}



