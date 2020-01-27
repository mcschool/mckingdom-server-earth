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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static org.bukkit.Material.*;

public class SkyWars implements Listener {

    Earth plugin;
    String worldName = "SkyWars";
    Boolean startFlag = false;

    public SkyWars(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerDeathEvent2(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals(this.worldName)) {
            Player player = e.getEntity();
            List<Player> players = player.getWorld().getPlayers();
            if (players.size() >= 2) {
                if (e.getEntity() instanceof Player) {
                    player.sendMessage("You died!");
                    player.setHealth(20.0);
                    player.setFoodLevel(10);
                    player.getInventory().clear();
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }

            int count = 0;
            for (Player player1 : players) {
                if (player1.getGameMode() == GameMode.SURVIVAL) {
                    count++;
                }
            }
            if (count == 1) {
                for (Player player1 : players) {
                    if (player1.getGameMode() == GameMode.SURVIVAL) {
                        player1.sendMessage("You Win !!");
                        player1.setHealth(20.0);
                        player1.setFoodLevel(10);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player1.performCommand("mvtp world");
                                player1.performCommand("WorldRestorer load SkyWars");
                            }
                        }.runTaskLater(this.plugin, 100);
                    } else {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player1.performCommand("mvtp world");
                            }
                        }.runTaskLater(this.plugin, 80);
                    }
                }
            }
        }
    }


    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("You are at "+player.getWorld().getName());
        if (player.getWorld().getName().equals(this.worldName)) {
            player.performCommand("mv modify set animals false");
            player.performCommand("mv modify set monsters false");
            player.setGameMode(GameMode.ADVENTURE);
            player.setPlayerWeather(WeatherType.CLEAR);
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.getWorld().setPVP(true);
            player.getInventory().clear();
            List<Player> players = player.getWorld().getPlayers();
            // ワールドにいる人数が1人だ以上だった場合スケジューラースタート
            if (players.size() == 2) {
                new SkyWarsScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);
                new SkyWarsSchedulerChest(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);
                startFlag = true;
            }
            if(players.size() == 1){
                World world = player.getWorld();
                //1人目のガラス
                world.getBlockAt(473, 15, -874).setType(Material.GLASS);
                world.getBlockAt(473, 16, -874).setType(Material.GLASS);
                world.getBlockAt(474, 15, -875).setType(Material.GLASS);
                world.getBlockAt(474, 16, -875).setType(Material.GLASS);
                world.getBlockAt(472, 15, -875).setType(Material.GLASS);
                world.getBlockAt(472, 16, -875).setType(Material.GLASS);
                world.getBlockAt(473, 15, -876).setType(Material.GLASS);
                world.getBlockAt(473, 16, -876).setType(Material.GLASS);
                world.getBlockAt(473, 14, -875).setType(Material.GLASS);
                world.getBlockAt(473, 17, -875).setType(Material.GLASS);

                this.spawnChest(new Location(world, 471, 11, -875), 0);
                this.spawnChest(new Location(world, 425, 11, -875), 0);

                this.spawnChest(new Location(world, 469, 11, -879), 1);
                this.spawnChest(new Location(world, 431, 11, -879), 1);

                this.spawnChest(new Location(world, 472, 6, -872), 2);
                this.spawnChest(new Location(world, 420, 7, -871), 2);

                this.spawnChest(new Location(world, 447, 16, -901), 3);
                this.spawnChest(new Location(world, 447, 13, -855), 3);

                this.spawnChest(new Location(world, 440, 16, -900), 4);
                this.spawnChest(new Location(world, 455, 14, -857), 4);




                //ワールドに入った時にプレイヤーをテレポートさせる
                Location location = new Location(player.getWorld(), 473.494, 15, -874.500);
                player.teleport(location);


            }

            if (players.size() == 2) {
                //new SkyWarsScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);
                World world2 = player.getWorld();
                //2人目のガラス
                world2.getBlockAt(427, 15, -874).setType(Material.GLASS);
                world2.getBlockAt(427, 16, -874).setType(Material.GLASS);
                world2.getBlockAt(428, 15, -875).setType(Material.GLASS);
                world2.getBlockAt(428, 16, -875).setType(Material.GLASS);
                world2.getBlockAt(426, 15, -875).setType(Material.GLASS);
                world2.getBlockAt(426, 16, -875).setType(Material.GLASS);
                world2.getBlockAt(427, 15, -876).setType(Material.GLASS);
                world2.getBlockAt(427, 16, -876).setType(Material.GLASS);
                world2.getBlockAt(427, 14, -875).setType(Material.GLASS);
                world2.getBlockAt(427, 17, -875).setType(Material.GLASS);

                Location location = new Location(player.getWorld(), 427.494, 15, -874.491);
                player.teleport(location);
            }
            if(players.size() >= 3){
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }


    public void spawnChest(Location location, int type){
        World world = Bukkit.getWorld(this.worldName);
        world.getBlockAt(location).setType(Material.CHEST);
        Chest chest = (Chest)world.getBlockAt(location).getState();
        Inventory inv = chest.getInventory();
        inv.clear();
        if (type == 0) {
            inv.setItem(1, new ItemStack(STONE, 24));
            inv.setItem(18, new ItemStack(WOOD_SWORD));
            inv.setItem(21,new ItemStack(COOKED_CHICKEN,6));
        }
        if (type == 1) {
            inv.setItem(5, new ItemStack(WOOD, 32));
            inv.setItem(20, new ItemStack(EGG, 16));
        }
        if (type == 2) {
            inv.setItem(9,new ItemStack(LEATHER_CHESTPLATE));
            inv.setItem(25,new ItemStack(LEATHER_HELMET));
        }
        if (type == 3) {
            inv.setItem(11,new ItemStack(CHAINMAIL_BOOTS));
            inv.setItem(26,new ItemStack(CHAINMAIL_LEGGINGS));
            inv.setItem(17,new ItemStack(COOKED_BEEF,4));
        }
        if (type == 4) {
            inv.setItem(3,new ItemStack(STONE_SWORD));
            inv.setItem(19,new ItemStack(BOW));
            inv.setItem(21,new ItemStack(ARROW,32));
        }
    }



    public static boolean inAbyss(Player player) {
        Location location = player.getLocation();
        if (location.getY() < -25) {
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void checkAbyss(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals(this.worldName)) {
            return;
        }
        if (inAbyss(player)){
            player.damage(player.getHealth());
        }
    }



    /*@EventHandler
    public void checkPlayers(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals(this.worldName)) {
            return;
        }
        if (!startFlag){
            return;
        }
        List<Player> players = player.getWorld().getPlayers();

        int count = 0;
        for (Player player1 : players) {
            if (player1.getGameMode() == GameMode.SURVIVAL) {
                count++;
            }
        }
        if (count == 1) {
            for (Player player1 : players) {
                if (player1.getGameMode() == GameMode.SURVIVAL) {
                    player1.sendMessage("You Win !!");
                    player1.setHealth(20.0);
                    player1.setFoodLevel(10);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player1.performCommand("mvtp world");
                            player1.performCommand("WorldRestorer load skywars");
                        }
                    }.runTaskLater(this.plugin, 100);
                } else {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player1.performCommand("mvtp world");
                        }
                    }.runTaskLater(this.plugin, 80);
                }
            }
        }
    }*/
}

