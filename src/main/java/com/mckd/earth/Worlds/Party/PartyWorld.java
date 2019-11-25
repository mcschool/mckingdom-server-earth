package com.mckd.earth.Worlds.Party;

import com.mckd.earth.Earth;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class PartyWorld implements Listener {

    Earth plugin;
    public PartyWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChange(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("party")) {
            return;
        }

        World world = player.getWorld();
        List<Player> players = world.getPlayers();
        if (players.size() == 2) {
            for (Player p: players) {
                p.sendTitle("雪", "あたらにようにね", 20, 20, 20);
            }
            new SnowSpawn().runTaskTimer(this.plugin,0 ,100);
        }
    }
}
