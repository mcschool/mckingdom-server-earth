package com.mckd.earth.Worlds.SkyWars;

import com.mckd.earth.Earth;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SkyWarsScheduler extends BukkitRunnable {
    private final World world;
    private int count;
    private Earth plugin;
    private int enemyCount = 0;


    public SkyWarsScheduler(Earth plugin, World world) {
        this.plugin = plugin;
        this.world = world;
        this.count = 10;

    }


    @Override
    public void run() {
        this.count--;
        this.sendMessageToPlayers(this.world, String.valueOf(this.count));
        if (this.count < 1) {
        }
    }

    private void sendMessageToPlayers(World world, String msg){
        for( Player player: world.getPlayers() ){
            player.sendMessage(msg);
            player.sendTitle(msg,"", 0,20,0);
        }
    }
}
