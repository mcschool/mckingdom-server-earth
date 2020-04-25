package com.mckd.earth.Worlds.TntGame;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
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
        World world = player.getWorld();
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

        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) potion.getItemMeta();
        pm.setBasePotionData(new PotionData(PotionType.NIGHT_VISION));
        player.getInventory().setItem(8,potion);

        if (player.getWorld().getPlayers().size() == 1){
            Location location1 = new Location(player.getWorld(),842.1,6,-598.5);
            player.teleport(location1);
            player.sendTitle(ChatColor.RED + "あなたは赤チームです", "", 0,60,0);
            this.playerRed = player;
        }

        if(player.getWorld().getPlayers().size() == 2){
            Location location2 = new Location(player.getWorld(),812.6,6, -577.5);
            player.teleport(location2);
            player.sendTitle(ChatColor.BLUE+ "あなたは青チームです",  "", 0, 40, 0);
            this.playerBlue = player;
            new TntGameScheduler(this.plugin, this.playerBlue, 5).runTaskTimer(this.plugin,0,40);
            new TntGameScheduler(this.plugin, this.playerRed, 5).runTaskTimer(this.plugin,0,40);
        }

        if (player.getWorld().getPlayers().size() > 2){
            player.performCommand("mvtp world");
        }

        this.spawnChest(new Location(world, 812,6,-597),0);
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
    public void BlockBreakEvent(BlockBreakEvent event){
        if (event.getPlayer().getWorld().getName().equals(this.worldname)) {
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                event.setCancelled(true);
            }
        }
    }

    public void spawnChest(Location location, int type){
        World world = Bukkit.getWorld(this.worldname);
        world.getBlockAt(location).setType(Material.CHEST);
        Chest chest = (Chest)world.getBlockAt(location).getState();
        Inventory inv = chest.getInventory();
        inv.clear();
        if (type == 0) {
            inv.setItem(1, new ItemStack(Material.DIAMOND_BOOTS));
            inv.setItem(9, new ItemStack(Material.IRON_CHESTPLATE));
            inv.setItem(20, new ItemStack(Material.DIAMOND_LEGGINGS));
        }
    }
}


