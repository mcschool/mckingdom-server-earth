package com.mckd.earth.Worlds.TypingButtle;

import com.mckd.earth.Earth;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class TypingButtleWorld implements Listener {
    private Earth plugin;
    String worldname = "ty";

    public TypingButtleWorld(Earth plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equals(this.worldname)) return;
        player.setGameMode(GameMode.ADVENTURE);
        if(player.getWorld().getPlayers().size() == 1){
            Location location = new Location(player.getWorld(),-946, 18, 179);
            player.teleport(location);
            player.sendTitle(ChatColor.WHITE+ "あなたは", ChatColor.RED + "赤チーム" + ChatColor.WHITE + "です", 60, 80, 60);
        }

        if(player.getWorld().getPlayers().size() == 2){
            Location location1 = new Location(player.getWorld(),-946, 18, 166);
            player.teleport(location1);
            player.sendTitle(ChatColor.WHITE+ "あなたは", ChatColor.BLUE + "青チーム" + ChatColor.WHITE + "です", 60, 80, 60);
        }
        if(player.getWorld().getPlayers().size() > 2){
            Location location2 = new Location(player.getWorld(),-946, 27, 170);
            player.teleport(location2);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.GREEN + "観覧車", ChatColor.WHITE + "です", 60, 80, 60);
        }
    }
}
