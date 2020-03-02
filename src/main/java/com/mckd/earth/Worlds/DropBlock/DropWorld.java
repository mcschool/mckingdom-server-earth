package com.mckd.earth.Worlds.DropBlock;

import com.mckd.earth.Earth;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.util.Vector;

import java.util.Random;

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
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.getPlayer().getWorld().getName().equals(this.worldname)) {
            Random random = new Random();
            int minx =100;
            int maxx = 100;
            int minz = 100;
            int maxz = 100;

            int x = random.nextInt(maxx - (minx) + 1) + (minx);
            int z = random.nextInt(maxz - (minz) + 1) + (minz);
            int y = player.getWorld().getHighestBlockAt(x, z).getY();
            Location loc = new Location(player.getWorld(), x,y,z);

            player.teleport(loc);
        }
    }
}

