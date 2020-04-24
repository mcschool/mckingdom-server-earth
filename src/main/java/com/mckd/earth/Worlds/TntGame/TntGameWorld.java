package com.mckd.earth.Worlds.TntGame;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TntGameWorld implements Listener {
    Player playerRed;
    Player playerBlue;
    Earth plugin;
    String worldname = "minigame";

    public TntGameWorld(Earth plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(this.worldname)) return;
        player.setGameMode(GameMode.SURVIVAL);
        player.setPlayerWeather(WeatherType.CLEAR);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getWorld().setPVP(false);
        player.getInventory().clear();
        //革のヘルメット
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmet.setItemMeta(helmetMeta);
        player.getInventory().setItem(1, helmet);
        //革のチェストプレート
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplate.setItemMeta(chestplateMeta);
        player.getInventory().setItem(2, chestplate);
        //革のレギンス
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemMeta leggingsMeta = leggings.getItemMeta();
        leggings.setItemMeta(leggingsMeta);
        player.getInventory().setItem(3, leggings);
        //革のブーツ
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        boots.setItemMeta(bootsMeta);
        player.getInventory().setItem(4, boots);
        //肉64枚
        ItemStack beef = new ItemStack(Material.COOKED_BEEF, 10);
        ItemMeta beefMeta = beef.getItemMeta();
        beef.setItemMeta(beefMeta);
        player.getInventory().setItem(5, beef);
        //TNT2スタック
        ItemStack tnt = new ItemStack(Material.TNT, 64);
        ItemMeta tntMeta = tnt.getItemMeta();
        tnt.setItemMeta(tntMeta);
        player.getInventory().setItem(6, tnt);
        player.getInventory().setItem(7, tnt);

        if (player.getWorld().getPlayers().size() == 1){
            Location location1 = new Location(player.getWorld(),778,6,-628);
            player.teleport(location1);
            player.sendTitle(ChatColor.RED + "あなたは赤チームです", "", 0,60,0);
            this.playerRed = player;
        }

        if(player.getWorld().getPlayers().size() == 2){
            Location location2 = new Location(player.getWorld(),748,6, -607);
            player.teleport(location2);
            player.sendTitle(ChatColor.BLUE+ "あなたは青チームです",  "", 0, 40, 0);
            this.playerBlue = player;
            new TntGameScheduler(this.plugin, this.playerBlue, 5).runTaskTimer(this.plugin,0,40);
            new TntGameScheduler(this.plugin, this.playerRed, 5).runTaskTimer(this.plugin,0,40);
        }
    }

    public void GameEnd(){
        World world = Bukkit.getWorld(this.worldname);
        List<Player> players = world.getPlayers();
        for(Player player:players){
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.performCommand("mvtp world");
                }
            }.runTaskLater(this.plugin, 20);
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event){
        if (this.playerRed.getHealth() == 0.0){
            this.playerRed.setHealth(2.0);
            this.playerBlue.sendTitle(ChatColor.RED + "You WIN!", "", 0,60,0);
            this.playerRed.sendTitle(ChatColor.BLUE + "You LOSE...", "", 0,60,0);
            this.GameEnd();
        }
        if (this.playerBlue.getHealth() == 0.0){
            this.playerBlue.setHealth(2.0);
            this.playerRed.sendTitle(ChatColor.RED + "You WIN!", "", 0,60,0);
            this.playerBlue.sendTitle(ChatColor.BLUE + "You LOSE...", "", 0,60,0);
            this.GameEnd();
        }
    }

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        if (event.getPlayer().getWorld().getName().equals(this.worldname)) {
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
        if (event.getPlayer().getWorld().getName().equals(this.worldname)) {
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                event.setCancelled(true);
            }
        }
    }

}


