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
    public void onPlayerDeathEvent2(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals("SkyWars")) {
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
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player1.performCommand("mvtp world");
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
            player.setPlayerWeather(WeatherType.CLEAR);
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.getWorld().setPVP(true);
            player.getInventory().clear();
            List<Player> players = player.getWorld().getPlayers();
            // ワールドにいる人数が1人だ以上だった場合スケジューラースタート

            //ワールドに入った時にチェストを置くkだkjdくぁ
            if (players.size() == 1) {
                player.sendMessage("test 0");
                World world = player.getWorld();
                //1人目のガラス
                world.getBlockAt(473, 15, -874).setType(GLASS);
                world.getBlockAt(473, 16, -874).setType(GLASS);
                world.getBlockAt(474, 15, -875).setType(GLASS);
                world.getBlockAt(474, 16, -875).setType(GLASS);
                world.getBlockAt(472, 15, -875).setType(GLASS);
                world.getBlockAt(472, 16, -875).setType(GLASS);
                world.getBlockAt(473, 15, -876).setType(GLASS);
                world.getBlockAt(473, 16, -876).setType(GLASS);
                world.getBlockAt(473, 14, -875).setType(GLASS);
                world.getBlockAt(473, 17, -875).setType(GLASS);

                //島1
                world.getBlockAt(471, 11, -875).setType(CHEST);
                world.getBlockAt(469, 11, -879).setType(CHEST);
                world.getBlockAt(472, 6, -872).setType(CHEST);
                //島2
                world.getBlockAt(430, 11, -877).setType(CHEST);
                world.getBlockAt(425, 11, -875).setType(CHEST);
                world.getBlockAt(420, 7, -871).setType(CHEST);
                //氷野島
                world.getBlockAt(438, 16, -901).setType(CHEST);
                world.getBlockAt(455, 14, -857).setType(CHEST);
                //火の島
                world.getBlockAt(447, 13, -901).setType(CHEST);
                world.getBlockAt(440, 16, -900).setType(CHEST);

                player.sendMessage("test 10");

                //島1チェスト中身
                Chest chestisland1of1 = (Chest) world.getBlockAt(471, 11, -875).getState();
                Inventory invisland1of1 = chestisland1of1.getInventory();
                invisland1of1.clear();
                invisland1of1.setItem(1, new ItemStack(STONE, 24));
                invisland1of1.setItem(18, new ItemStack(WOOD_SWORD));
                Chest chestisland1of2 = (Chest) world.getBlockAt(469, 11, -879).getState();
                Inventory invisland1of2 = chestisland1of2.getInventory();
                invisland1of2.clear();
                invisland1of2.setItem(5, new ItemStack(WOOD, 32));
                invisland1of2.setItem(20, new ItemStack(EGG, 16));
                Chest chestisland1of3 = (Chest) world.getBlockAt(472, 11, -872).getState();
                Inventory invisland1of3 = chestisland1of3.getInventory();
                invisland1of3.clear();
                invisland1of3.setItem(8, new ItemStack(LEATHER_CHESTPLATE));
                invisland1of3.setItem(22, new ItemStack(LEATHER_HELMET));

                player.sendMessage("test 11");

                //島2チェスト中身
                Chest chestisland2of1 = (Chest) world.getBlockAt(430, 11, -877).getState();
                Inventory invisland2of1 = chestisland2of1.getInventory();
                invisland2of1.clear();
                invisland2of1.setItem(1, new ItemStack(STONE, 24));
                invisland2of1.setItem(18, new ItemStack(WOOD_SWORD));
                Chest chestisland2of2 = (Chest) world.getBlockAt(425, 11, -875).getState();
                Inventory invisland2of2 = chestisland2of2.getInventory();
                invisland2of2.clear();
                invisland2of2.setItem(5, new ItemStack(WOOD, 32));
                invisland2of2.setItem(20, new ItemStack(EGG, 16));
                Chest chestisland2of3 = (Chest) world.getBlockAt(420, 7, -871).getState();
                Inventory invisland2of3 = chestisland2of3.getInventory();
                invisland2of2.clear();
                invisland2of3.setItem(8, new ItemStack(LEATHER_CHESTPLATE));
                invisland2of3.setItem(22, new ItemStack(LEATHER_HELMET));

                player.sendMessage("test 12");

            //ワールドに入った時にプレイヤーをテレポートさせる
                player.sendMessage("test1");
                Location location = new Location(player.getWorld(), 473.494, 15, -874.500);
                player.teleport(location);
                player.sendMessage(toString().valueOf(players.size()));
                player.sendMessage("test2");

                new SkyWarsScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);

            }

            if (players.size() == 2) {
                World world2 = player.getWorld();
                //2人目のガラス
                world2.getBlockAt(427, 15, -874).setType(GLASS);
                world2.getBlockAt(427, 16, -874).setType(GLASS);
                world2.getBlockAt(428, 15, -875).setType(GLASS);
                world2.getBlockAt(428, 16, -875).setType(GLASS);
                world2.getBlockAt(426, 15, -875).setType(GLASS);
                world2.getBlockAt(426, 16, -875).setType(GLASS);
                world2.getBlockAt(427, 15, -876).setType(GLASS);
                world2.getBlockAt(427, 16, -876).setType(GLASS);
                world2.getBlockAt(427, 14, -875).setType(GLASS);
                world2.getBlockAt(427, 17, -875).setType(GLASS);
            }

           /* //ワールドに入った時にチェストを置く
            if (players.size() == 1) {
                World worldchest = player.getWorld();
                //島1
                worldchest.getBlockAt(471, 11, -875).setType(CHEST);
                worldchest.getBlockAt(469, 11, -879).setType(CHEST);
                worldchest.getBlockAt(472, 6, -872).setType(CHEST);
                //島2
                worldchest.getBlockAt(430, 11, -877).setType(CHEST);
                worldchest.getBlockAt(425, 11, -875).setType(CHEST);
                worldchest.getBlockAt(420, 7, -871).setType(CHEST);
                //氷野島
                worldchest.getBlockAt(438, 16, -901).setType(CHEST);
                worldchest.getBlockAt(455, 14, -857).setType(CHEST);
                //火の島
                worldchest.getBlockAt(447, 13, -901).setType(CHEST);
                worldchest.getBlockAt(440, 16, -900).setType(CHEST);

                //島1チェスト中身
                Chest chestisland1of1 = (Chest) worldchest.getBlockAt(471, 11, -875).getState();
                Inventory invisland1of1 = chestisland1of1.getInventory();
                invisland1of1.clear();
                invisland1of1.setItem(1, new ItemStack(STONE, 24));
                invisland1of1.setItem(18, new ItemStack(WOOD_SWORD));
                Chest chestisland1of2 = (Chest) worldchest.getBlockAt(469, 11, -879).getState();
                Inventory invisland1of2 = chestisland1of2.getInventory();
                invisland1of2.clear();
                invisland1of2.setItem(5, new ItemStack(WOOD, 32));
                invisland1of2.setItem(20, new ItemStack(EGG, 16));
                Chest chestisland1of3 = (Chest) worldchest.getBlockAt(472, 11, -872).getState();
                Inventory invisland1of3 = chestisland1of3.getInventory();
                invisland1of3.clear();
                invisland1of3.setItem(8, new ItemStack(LEATHER_CHESTPLATE));
                invisland1of3.setItem(22, new ItemStack(LEATHER_HELMET));

                //島2チェスト中身
                Chest chestisland2of1 = (Chest) worldchest.getBlockAt(430, 11, -877).getState();
                Inventory invisland2of1 = chestisland2of1.getInventory();
                invisland2of1.clear();
                invisland2of1.setItem(1, new ItemStack(STONE, 24));
                invisland2of1.setItem(18, new ItemStack(WOOD_SWORD));
                Chest chestisland2of2 = (Chest) worldchest.getBlockAt(425, 11, -875).getState();
                Inventory invisland2of2 = chestisland2of2.getInventory();
                invisland2of2.clear();
                invisland2of2.setItem(5, new ItemStack(WOOD, 32));
                invisland2of2.setItem(20, new ItemStack(EGG, 16));
                Chest chestisland2of3 = (Chest) worldchest.getBlockAt(420, 7, -871).getState();
                Inventory invisland2of3 = chestisland2of3.getInventory();
                invisland2of2.clear();
                invisland2of3.setItem(8, new ItemStack(LEATHER_CHESTPLATE));
                invisland2of3.setItem(22, new ItemStack(LEATHER_HELMET));
            }
            //ワールドに入った時にプレイヤーをテレポートさせる
            if (players.size() ==0) {
                player.sendMessage("test1");
                Location location = new Location(player.getWorld(), 473.494, 15, -874.500);
                player.teleport(location);
                player.sendMessage(toString().valueOf(players.size()));
                player.sendMessage("test2");
            }*/
            if (players.size() == 2) {
                Location location = new Location(player.getWorld(), 427.494, 15, -874.491);
                player.teleport(location);
                player.sendMessage(String.valueOf(players.size()));
                player.sendMessage("test3");
            }
            new SkyWarsSchedulerChest(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 20);
        }
    }

}

