package com.mckd.earth.Worlds.DropBlock;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.util.Vector;

public class DropWorld implements Listener {
    private Earth plugin;
    String worldname = "fall";

    public DropWorld(Earth plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(this.worldname)) return;
        player.setGameMode(GameMode.ADVENTURE);
        player.setPlayerWeather(WeatherType.CLEAR);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getWorld().setPVP(false);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event){
        for (Block blocks : event.blockList()) {
            event.setCancelled(true);
            float x = (float) (-1.0D + 2.5 * Math.random());
            float y = (float) (-2.0D + 3.5 * Math.random());
            float z = (float) (-1.0D + 2.5 * Math.random());

            FallingBlock fallingBlock = blocks.getWorld().spawnFallingBlock(blocks.getLocation(), blocks.getType(), blocks.getData());
            fallingBlock.setDropItem(false);
            fallingBlock.setVelocity(new Vector(x, y, z));

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void EntityChangeBlock(EntityChangeBlockEvent event){
        if (event.getEntityType() == EntityType.FALLING_BLOCK) {
            event.setCancelled(true);
        }
    }

}

