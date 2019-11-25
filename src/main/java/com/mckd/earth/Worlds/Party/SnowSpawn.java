package com.mckd.earth.Worlds.Party;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SnowSpawn extends BukkitRunnable {

    int count = 100;

    @Override
    public void run() {
        // 2025 22 -308 / 2051 23 -277
        this.count--;
        World world = Bukkit.getWorld("party");

        List<Player> players = world.getPlayers();
        for (Player p: players) {
            p.sendTitle("RUN", "", 0, 20, 0);
        }

        for (int x = 2025; x < 2051; x++) {
            for (int z = -308; z < -277; z++) {
                world.getBlockAt(x, 24, z).setType(Material.SNOW_BALL);
            }
        }
        if (count <= 0) {
            this.cancel();
        }
    }
}
