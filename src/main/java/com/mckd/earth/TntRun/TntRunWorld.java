package com.mckd.earth.TntRun;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class TntRunWorld implements Listener {

    Earth plugin;
    String status = "wait";

    public TntRunWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChanged(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("tnt")){
            return;

        }
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);

    }

    @EventHandler
    public void  onPlayerIntract(PlayerInteractEvent e){
        Player player= e.getPlayer();
        if (!player.getWorld().getName().equals("tnt")){
            return;
        }


        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
           Block block = e.getClickedBlock();
           if(block.getType() == Material.TNT){
               this.start();

           }
           if (block.getType() == Material.REDSTONE_BLOCK) {
               //this.fillFloor();
           }
        }
    }

    public void start() {
    World world = Bukkit.getWorld("tnt");
    world.setPVP(false);
    List<Player> players = world.getPlayers();
    int c = 0;
    for (Player p: players){
        c += 3;
        Location location = new Location(world, 0, 0, 0);
        location.add(c, 0,0);
        p.teleport(location);

        }


    }
}

