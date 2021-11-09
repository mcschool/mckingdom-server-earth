package com.mckd.earth.Worlds.Pve;

import com.mckd.earth.Earth;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PveRespawnScheduler extends BukkitRunnable {
    private  Earth plugin;
    public  Player player;


    public PveRespawnScheduler(Earth plugin, Player _player) {
        this.plugin = plugin;
        this.player = _player;
    }

    @Override
    public  void run(){
        this.player.performCommand("mvtp world");
        this.cancel();
    }

}