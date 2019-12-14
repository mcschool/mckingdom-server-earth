package com.mckd.earth.Worlds.SkyWars;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SkyWars implements Listener {

    Earth plugin;
    public SkyWars(Earth plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals("pve")) {
            if (e.getEntity() instanceof Player) {
                Player player = e.getEntity();
                player.sendMessage("You died!");
                player.setHealth(20.0);
                player.setFoodLevel(10);
                player.getInventory().clear();
                player.setGameMode(GameMode.SPECTATOR);
                player.hidePlayer(this.plugin, player);
            }
        }
    }
}