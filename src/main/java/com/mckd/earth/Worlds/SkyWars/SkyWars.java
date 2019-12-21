package com.mckd.earth.Worlds.SkyWars;

import com.mckd.earth.Earth;
import com.mckd.earth.Worlds.Pve.PveRespawnScheduler;
import com.mckd.earth.Worlds.Pve.PveScheduler;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static org.bukkit.Material.*;

public class SkyWars implements Listener {

    Earth plugin;

    public SkyWars(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals("SkyWars")) {
            if (e.getEntity() instanceof Player) {
                Player player = e.getEntity();
                player.sendMessage("You died!");
                player.setHealth(20.0);
                player.setFoodLevel(10);
                player.getInventory().clear();
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }



    @EventHandler
    public void onPlayerDeathEvent2(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (e.getEntity().getWorld().getName().equals("SkyWars")) {
            List<Player> players = e.getEntity().getWorld().getPlayers();
            int count = 0;
            for( Player player1 : players ){
                if (player1.getGameMode() == GameMode.SURVIVAL) {
                    count++;
                    player = player1;
                }
            }
            if (count == 1) {
                if (e.getEntity() instanceof Player) {
                    player.sendMessage("You Win !!");
                    player.setHealth(20.0);
                    player.setFoodLevel(10);
                    player.hidePlayer(this.plugin, player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            //player.performCommand("mvtp world");
                        }
                    }.runTaskLater(this.plugin, 100);
                }
            }
        }
    }




    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("SkyWars")) {
            player.sendMessage("SkyWars");
            player.setGameMode(GameMode.ADVENTURE);
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.getWorld().setPVP(true);
            player.getInventory().clear();
            List<Player> players = player.getWorld().getPlayers();
            // ワールドにいる人数が1人だ以上だった場合スケジューラースタート
            if (players.size() == 1) {
                new SkyWarsScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);
            }
            //ワールドに入った時にチェストを置く
            if (players.size() == 1) {
                World world = player.getWorld();
                //1人目のガラス
                world.getBlockAt(481, 9, -860).setType(GLASS);
                world.getBlockAt(481, 10, -860).setType(GLASS);
                world.getBlockAt(480, 9, -859).setType(GLASS);
                world.getBlockAt(480, 10, -859).setType(GLASS);
                world.getBlockAt(481, 9, -858).setType(GLASS);
                world.getBlockAt(481, 10, -858).setType(GLASS);
                world.getBlockAt(482, 9, -859).setType(GLASS);
                world.getBlockAt(482, 10, -859).setType(GLASS);
                world.getBlockAt(481, 8, -859).setType(GLASS);
                world.getBlockAt(481, 11, -859).setType(GLASS);

                
                //ワールドに入った時にチェストを置く
                if (players.size() == 1) {
                    World world2 = player.getWorld();
                    world2.getBlockAt(476, 7, -874).setType(CHEST);
                    world2.getBlockAt(476, 7, -876).setType(CHEST);
                    world2.getBlockAt(476, 7, -878).setType(CHEST);
                    Chest chest1 = (Chest) world2.getBlockAt(476, 7, -874).getState();
                    Inventory inv1 = chest1.getInventory();
                    inv1.setItem(1, new ItemStack(STONE, 24));
                    inv1.setItem(18, new ItemStack(STONE_SWORD));
                    Chest chest2 = (Chest) world2.getBlockAt(476, 7, -876).getState();
                    Inventory inv2 = chest2.getInventory();
                    inv2.setItem(5, new ItemStack(WOOD, 32));
                    inv2.setItem(20, new ItemStack(EGG, 16));
                }
                //ワールドに入った時にプレイヤーをテレポートさせる
                if (players.size() == 1) {
                    Location location = new Location(player.getWorld(), 481.476, 9, -858.495);
                    player.teleport(location);
                }
                if (players.size() == 2) {
                    Location location = new Location(player.getWorld(), 475.510, 9, -858.513);
                    player.teleport(location);
                }
            }
        }

    }
}