package com.mckd.earth.Worlds.SkyWars2;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static org.bukkit.Material.*;

public class SkyWars2 implements Listener {

    Earth plugin;
    String worldName = "SkyWars2";
    Boolean startFlag = false;

    public SkyWars2(Earth plugin) {
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
                                //player1.performCommand("WorldRestorer load SkyWars2");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"WorldRestorer load SkyWars2");
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
            if (players.size() == 4) {
                new SkyWarsScheduler2(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);
                new SkyWarsSchedulerChest2(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);
                startFlag = true;
            }
            if(players.size() == 1){
                World world = player.getWorld();
                //1人目のガラス
                world.getBlockAt(-176, 16, -2).setType(Material.GLASS);
                world.getBlockAt(-176, 17, -2).setType(Material.GLASS);
                world.getBlockAt(-174, 16, -2).setType(Material.GLASS);
                world.getBlockAt(-174, 17, -2).setType(Material.GLASS);
                world.getBlockAt(-175, 16, -3).setType(Material.GLASS);
                world.getBlockAt(-175, 17, -3).setType(Material.GLASS);
                world.getBlockAt(-175, 16, -1).setType(Material.GLASS);
                world.getBlockAt(-175, 17, -1).setType(Material.GLASS);
                world.getBlockAt(-175, 15, -2).setType(Material.GLASS);
                world.getBlockAt(-175, 18, -2).setType(Material.GLASS);

                this.spawnChest(new Location(world, -170, 13, -2), 0);
                this.spawnChest(new Location(world, -238, 12, -2), 0);
                this.spawnChest(new Location(world, -205, 13, -34), 0);
                this.spawnChest(new Location(world, -205, 13, 31), 0);

                this.spawnChest(new Location(world, -171, 8, -2), 1);
                this.spawnChest(new Location(world, -237, 7, -2), 1);
                this.spawnChest(new Location(world, -205, 8, -33), 1);
                this.spawnChest(new Location(world, -205, 8, 30), 1);

                this.spawnChest(new Location(world, -173, 8, 0), 2);
                this.spawnChest(new Location(world, -235, 7, -4), 2);
                this.spawnChest(new Location(world, -203, 8, -31), 2);
                this.spawnChest(new Location(world, -207, 8, 28), 2);

                this.spawnChest(new Location(world, -200, 12, 4), 3);
                this.spawnChest(new Location(world, -211, 12, -6), 3);

                this.spawnChest(new Location(world, -202, 6, 1), 4);
                this.spawnChest(new Location(world, -208, 6, -5), 4);




                //ワールドに入った時にプレイヤーをテレポートさせる
                Location location = new Location(player.getWorld(), -174.504, 17, -1.504);
                player.teleport(location);


            }

            if (players.size() == 2) {
                World world2 = player.getWorld();
                //2人目のガラス
                world2.getBlockAt(-232, 16, -2).setType(Material.GLASS);
                world2.getBlockAt(-232, 17, -2).setType(Material.GLASS);
                world2.getBlockAt(-234, 16, -2).setType(Material.GLASS);
                world2.getBlockAt(-234, 17, -2).setType(Material.GLASS);
                world2.getBlockAt(-233, 16, -1).setType(Material.GLASS);
                world2.getBlockAt(-233, 17, -1).setType(Material.GLASS);
                world2.getBlockAt(-233, 16, -3).setType(Material.GLASS);
                world2.getBlockAt(-233, 17, -3).setType(Material.GLASS);
                world2.getBlockAt(-233, 15, -2).setType(Material.GLASS);
                world2.getBlockAt(-233, 18, -2).setType(Material.GLASS);

                Location location = new Location(player.getWorld(), -232.508, 17, -1.542);
                player.teleport(location);
            }

            if (players.size() == 3) {
                World world2 = player.getWorld();
                //3人目のガラス

                world2.getBlockAt(-205, 17, -28).setType(Material.GLASS);
                world2.getBlockAt(-205, 18, -28).setType(Material.GLASS);
                world2.getBlockAt(-205, 17, -30).setType(Material.GLASS);
                world2.getBlockAt(-205, 18, -30).setType(Material.GLASS);
                world2.getBlockAt(-206, 17, -29).setType(Material.GLASS);
                world2.getBlockAt(-206, 18, -29).setType(Material.GLASS);
                world2.getBlockAt(-204, 17, -29).setType(Material.GLASS);
                world2.getBlockAt(-204, 18, -29).setType(Material.GLASS);
                world2.getBlockAt(-205, 16, -29).setType(Material.GLASS);
                world2.getBlockAt(-205, 19, -29).setType(Material.GLASS);

                Location location = new Location(player.getWorld(), -204.500, 18, -28.506   );
                player.teleport(location);
            }

            if (players.size() == 4) {
                World world2 = player.getWorld();
                //4人目のガラス
                world2.getBlockAt(-205, 16, 25).setType(Material.GLASS);
                world2.getBlockAt(-205, 18, 25).setType(Material.GLASS);
                world2.getBlockAt(-205, 17, 27).setType(Material.GLASS);
                world2.getBlockAt(-205, 18, 27).setType(Material.GLASS);
                world2.getBlockAt(-206, 17, 26).setType(Material.GLASS);
                world2.getBlockAt(-206, 18, 26).setType(Material.GLASS);
                world2.getBlockAt(-204, 17, 26).setType(Material.GLASS);
                world2.getBlockAt(-204, 18, 26).setType(Material.GLASS);
                world2.getBlockAt(-205, 16, 26).setType(Material.GLASS);
                world2.getBlockAt(-205, 19, 26).setType(Material.GLASS);

                Location location = new Location(player.getWorld(), -204.485, 18, 26.553);
                player.teleport(location);
            }

            if(players.size() >= 5){
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

