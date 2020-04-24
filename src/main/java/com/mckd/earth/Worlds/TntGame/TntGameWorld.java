package com.mckd.earth.Worlds.TntGame;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

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
        player.setGameMode(GameMode.ADVENTURE);
        player.setPlayerWeather(WeatherType.CLEAR);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getWorld().setPVP(false);
        player.getInventory().clear();

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
        }
    }

}


