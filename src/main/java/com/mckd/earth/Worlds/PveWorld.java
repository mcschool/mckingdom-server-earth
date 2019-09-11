package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PveWorld implements Listener {

    public PveWorld(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        if (!e.getPlayer().getWorld().getName().equals("pve")) return;
        Player p = e.getPlayer();
        p.setGameMode((GameMode.SURVIVAL));
        p.setFoodLevel(20);
        p.setHealth(20.0);
        p.getWorld().setPVP(false);
        p.getInventory().clear();
    }
}
