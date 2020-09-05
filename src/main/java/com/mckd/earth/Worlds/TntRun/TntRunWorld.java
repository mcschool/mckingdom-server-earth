package com.mckd.earth.Worlds.TntRun;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TntRunWorld implements Listener {

    Earth plugin;
    String status = "wait";
    String worldname = "tntrun";

    public TntRunWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChanged(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("tntrun")) {
            return;
        }
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        World world = player.getWorld();
        Location location = new Location(world,555,4,434);
        player.teleport(location);
    }

    @EventHandler
    public void signClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.getWorld().getName().equals("tntrun")) {
            return;
        }
        Block b = e.getClickedBlock();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && b.getType() == Material.SIGN_POST) {
            Sign sign;
            sign = (Sign) b.getState();
            String line = sign.getLine(1);
            if (line.equals("GameStart")) {
                Location location = new Location(p.getWorld(), 393.9, 33, 374.5);
                p.teleport(location);
            }
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (event.getPlayer().getWorld().getName().equals(this.worldname)){
            return;
        }
        Location location = event.getPlayer().getLocation().clone().subtract(0,-1,0);
        Block block = location.getBlock();
        if (block.getType() == Material.TNT){
            player.sendMessage(block.getType().name());
            world.getBlockAt(location).setType(Material.AIR);
        }
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        if (event.getPlayer().getWorld().getName().equals(this.worldname)) {
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                event.getPlayer().sendMessage("壊せないよ");
                event.setCancelled(true);
            }
        }
    }
}



