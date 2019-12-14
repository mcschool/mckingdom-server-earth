package com.mckd.earth.Worlds.SkyWars;

import com.mckd.earth.Earth;
import com.mckd.earth.Worlds.Pve.PveRespawnScheduler;
import com.mckd.earth.Worlds.Pve.PveScheduler;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

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
                player.hidePlayer(this.plugin, player);
                //player.performCommand("mvtp world");
                new PveRespawnScheduler(this.plugin, player).runTaskTimer(this.plugin,0,20);
            }
        }
    }
    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("SkyWars")) {
            player.sendMessage("SkyWars");
            player.setGameMode(GameMode.SURVIVAL);
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.getWorld().setPVP(true);
            player.getInventory().clear();

            // ワールドにいる人数が1人だった場合スケジューラースタート
            List<Player> players = player.getWorld().getPlayers();
            //ワールドに入った時にチェストを置く
            if (players.size() <= 1) {
                World world = player.getWorld();
                world.getBlockAt(476, 7, -873).setType(Material.CHEST);
            }
            if (players.size() == 1) {
                Location location = new Location(player.getWorld(), 481, 9, -858);
                player.teleport(location);
            }
            if (players.size() == 2) {
                Location location = new Location(player.getWorld(), 475, 9, -858);
                player.teleport(location);
            }
        }
    }
}