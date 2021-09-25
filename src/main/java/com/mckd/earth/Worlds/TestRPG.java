package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class TestRPG  implements Listener {
    private Earth plugin;
    public String worldname = "sky";
    public Location StartLocation;


    public TestRPG(Earth plugin){
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.StartLocation = new Location(Bukkit.getWorld(this.worldname),0,0,0);
    }


    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (player.getWorld().getName().equals(this.worldname)) {
            if (player.hasPlayedBefore() == false){
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(this.StartLocation);
                player.setHealth(20.0);
                player.setFoodLevel(20);
                player.sendMessage("false");
            }
            else{
                player.sendMessage("true");
            }
        }
    }
}
