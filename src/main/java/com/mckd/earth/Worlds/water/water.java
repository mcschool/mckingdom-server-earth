package com.mckd.earth.Worlds.water;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class water implements Listener {
    Earth plugin;
    String worldName = "water";

    public water(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("You are at " + player.getWorld().getName());
        if (!player.getWorld().getName().equals(this.worldName)) {
            return;
        }
        player.setFoodLevel(20);
        player.setHealth(20.0);
        player.getInventory().clear();
        Location location = new Location(player.getWorld(), 483.500, 95, -781.500);
        player.teleport(location);

        ItemStack itemStack = new ItemStack(Material.SNOW_BALL);
        player.getInventory().addItem(itemStack);

        ItemStack itemstack1 = new ItemStack(Material.BOOK);
        player.getInventory().addItem(itemstack1);

    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void signClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!p.getWorld().getName().equals("water")) {
            return;
        }

        Block b = e.getClickedBlock();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && b.getType() == Material.SIGN_POST) {

            Sign sign;
            sign = (Sign) b.getState();
            //ScoreboardManager sbm = Bukkit.ScoreboardManager();
            //Scoreboard sb = sbm.getMainScoreboard();
            //Objective obj = sb.getObjective("point");
            String line = sign.getLine(1);
            String line2 = sign.getLine(2);
            if (line.equals("GameStart")) {
                Location location = new Location(p.getWorld(), 452, 23, -776);
                p.teleport(location);
                // this.start(p);
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e){
        Player player = e.getEntity();

        if(!player.getWorld().getName().equals(worldName)){
            return;
        }
        
    }
}

