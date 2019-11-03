package com.mckd.earth.Worlds.Athletic;

import com.mckd.earth.Earth;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AthleticClearScheduler extends BukkitRunnable {

    private Earth plugin;
    private Player player;
    public int count;

    public AthleticClearScheduler(Earth plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.count = 10;
    }

    @Override
    public void run() {
        this.count--;
        this.player.sendMessage(String.valueOf(count));
        if (this.count < 1) {
            this.player.getWorld().getSpawnLocation();
            this.cancel();
        }
    }
}
