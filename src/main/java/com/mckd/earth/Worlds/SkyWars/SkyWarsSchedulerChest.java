package com.mckd.earth.Worlds.SkyWars;

import com.mckd.earth.Earth;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
        this.count = 70;

    }


    @Override
    public void run() {
        /*this.count--;
        if (this.count < 1) {
            this.RefillChest(new Location(world,471, 11, -875),0);
            this.count = 70;
            this.sendMessageToPlayers(this.world,"チェストの中身が追加されました");
        }*/
    }
    private void sendMessageToPlayers(World world, String msg){
        for( Player player: world.getPlayers() ){
            player.sendMessage(msg);
            player.sendTitle(msg,"", 0,10,0);
        }
    }


    public void RefillChest(Location location, int type) {
        World world = Bukkit.getWorld("SkyWars");
        Chest chest = (Chest) world.getBlockAt(location).getState();
        Inventory inv = chest.getInventory();
        if (type == 0) {
            inv.setItem(7,new ItemStack(DIAMOND));
        }
    }
}
