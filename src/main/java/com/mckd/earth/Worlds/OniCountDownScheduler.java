package com.mckd.earth.Worlds;


import com.mckd.earth.Earth;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OniCountDownScheduler extends BukkitRunnable {
    Earth plugin;
    Player player;
    int count;
    public OniCountDownScheduler(Earth plugin, Player player, int count){
        this.plugin = plugin;
        this.player = player;
        this.count = count;
    }
    @Override
    public void run() {
        this.count--;
        this.player.sendTitle(String.valueOf(count), "", 0, 20, 0);
        if (this.count < 1){
            this.cancel();
        }
    }
}