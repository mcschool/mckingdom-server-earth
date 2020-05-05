package com.mckd.earth.Worlds.MonsterHuntWorld;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MonsterHuntWorld implements Listener {
    Earth plugin;
    Player player;
    String worldname = "monhun";

    public MonsterHuntWorld(Earth plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (!player.getWorld().getName().equals(this.worldname)) return;
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getWorld().setPVP(false);
        player.getInventory().clear();
        //プレイヤーをテレポートさせる
        Location location = new Location(world,424,4,1079);
        player.teleport(location);
        //スケジューラーを開始させる
        new MonsterHuntScheduler(this.plugin,10).runTaskTimer(this.plugin,0,20);
    }

    public ItemStack getStrongSword(){
        ItemStack item =  new ItemStack(Material.IRON_SWORD);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS,6,true);
        itemMeta.addEnchant(Enchantment.DURABILITY,3,true);
        item.setItemMeta(itemMeta);
        player.getInventory().setItem(1,item);
        item.setDurability((short) 100);
        return item;

    }
}
