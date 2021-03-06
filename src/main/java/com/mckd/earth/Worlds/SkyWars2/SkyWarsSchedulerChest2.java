package com.mckd.earth.Worlds.SkyWars2;

import com.mckd.earth.Earth;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Material.*;

public class SkyWarsSchedulerChest2 extends BukkitRunnable {
    private World world;
    private int count;
    private Earth plugin;
    private int enemyCount = 0;


    public SkyWarsSchedulerChest2(Earth plugin, World world) {
        this.plugin = plugin;
        this.world = world;
        this.count = 80;

    }


    @Override
    public void run() {
       this.count--;
        if (this.count < 1) {
            this.RefillChest(new Location(world, -170, 13, -2), 0);
            this.RefillChest(new Location(world, -238, 12, -2), 0);

            this.RefillChest(new Location(world, -171, 8, -2), 1);
            this.RefillChest(new Location(world, -237, 7, -2), 1);

            this.RefillChest(new Location(world, -173, 8, 0), 2);
            this.RefillChest(new Location(world, -235, 7, -4), 2);

            this.RefillChest(new Location(world, -200, 12, 4), 3);
            this.RefillChest(new Location(world, -211, 12, -6), 3);

            this.RefillChest(new Location(world, -202, 6, 1), 4);
            this.RefillChest(new Location(world, -208, 6, -5), 4);
            this.count = 80;
            this.sendMessageToPlayers(this.world,"チェストの中身が追加されました");
            this.cancel();
        }
    }
    private void sendMessageToPlayers(World world, String msg){
        for( Player player: world.getPlayers() ){
            player.sendMessage(msg);
            player.sendTitle(msg,"", 0,10,0);
        }
    }


    public void RefillChest(Location location, int type) {
        Chest chest = (Chest) world.getBlockAt(location).getState();
        Inventory inv = chest.getInventory();
        if (type == 0) {
            inv.setItem(2,new ItemStack(COOKED_BEEF,4));
            inv.setItem(11,new ItemStack(WOOD,32));
        }
        if (type == 1) {
            inv.setItem(11,new ItemStack(CHAINMAIL_BOOTS));
            inv.setItem(26,new ItemStack(CHAINMAIL_LEGGINGS));
            inv.setItem(7,new ItemStack(WOOD,32));
        }
        if (type == 2) {
            inv.setItem(15,new ItemStack(SNOW_BALL,16));
            inv.setItem(15,new ItemStack(IRON_HELMET));
        }
        if (type == 3) {
            inv.setItem(16,new ItemStack(IRON_HELMET));
            inv.setItem(5,new ItemStack(IRON_CHESTPLATE));
        }
        if (type == 4) {
            inv.setItem(16,new ItemStack(IRON_SWORD));
            inv.setItem(5,new ItemStack(GOLDEN_APPLE));
        }

    }
}
