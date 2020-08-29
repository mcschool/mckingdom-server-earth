package com.mckd.earth.Worlds.water;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
        player.sendMessage("You are at "+player.getWorld().getName());
        if (!player.getWorld().getName().equals(this.worldName)) {
            return;
        }
        player.setFoodLevel(20);
        player.setHealth(20.0);
        player.getInventory().clear();
        Location location = new Location(player.getWorld(), 483.500,95,-781.500);
        player.teleport(location);

        ItemStack itemStack = new ItemStack(Material.SNOW_BALL);
        player.getInventory().addItem(itemStack);

        ItemStack itemstack1 = new ItemStack(Material.BOOK);
        player.getInventory().addItem(itemstack1);

    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
                event.setCancelled(true);
            }
    }








}
