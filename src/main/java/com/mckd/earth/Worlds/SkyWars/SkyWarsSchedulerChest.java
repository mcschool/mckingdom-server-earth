package com.mckd.earth.Worlds.SkyWars;

import com.mckd.earth.Earth;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wood;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Material.*;

public class SkyWarsSchedulerChest extends BukkitRunnable {
    private World world;
    private int count;
    private Earth plugin;
    private int enemyCount = 0;


    public SkyWarsSchedulerChest(Earth plugin, World world) {
        this.plugin = plugin;
        this.world = world;
        this.count = 20;

    }


    @Override
    public void run() {
        this.count--;
        if (this.count < 1) {
            Chest chest1 = (Chest) this.world.getBlockAt(476, 7, -874).getState();
            Inventory inv1 = chest1.getInventory();
            inv1.setItem(2, new ItemStack(STONE, 24));
            inv1.setItem(19, new ItemStack(STONE_SWORD));
            inv1.setItem(18, new ItemStack(WOOD_SWORD));
            this.count = 20;
            this.sendMessageToPlayers(this.world,"チェストの中身が追加されました");
        }
    }
    private void sendMessageToPlayers(World world, String msg){
        for( Player player: world.getPlayers() ){
            player.sendMessage(msg);
            player.sendTitle(msg,"", 0,10,0);
        }
    }
}
