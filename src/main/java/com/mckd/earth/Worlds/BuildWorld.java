package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuildWorld implements Listener {

    private String worldName = "build";

    public BuildWorld(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        // このプログラムが動いているワールド名が "build" じゃなかったらプログラム終了
        if (!e.getPlayer().getWorld().getName().equals(this.worldName)) return;
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if (!event.getPlayer().getWorld().getName().equals(this.worldName)) return;
        player.getInventory().clear();
        ItemStack itemStack = new ItemStack(Material.BED);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemStack.setItemMeta(itemMeta);
        player.getInventory().addItem(itemStack);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(this.worldName)) return;
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if (event.getMaterial() == Material.BED){
                if (player.getUniqueId().toString().equals("2cef9f00-eac6-3f9b-aace-1543f2ef4fda")){
                    player.setGameMode(GameMode.CREATIVE);
                    return;
                }
                if (player.getUniqueId().toString().equals("ebef3a23-b9d4-3723-ad94-64efa165182b")){
                    player.setGameMode(GameMode.CREATIVE);
                    return;
                }
            }
        }
    }
}
