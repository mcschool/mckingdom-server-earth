package com.mckd.earth.Worlds.TntGame;

import com.mckd.earth.Earth;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TntGameScheduler extends BukkitRunnable {

    Earth plugin;
    Player player;
    int count;

    public TntGameScheduler(Earth plugin, Player player, int count){
        this.plugin = plugin;
        this.player = player;
        this.count = 5;
    }

    @Override
    public void run(){
        this.count--;
        this.player.sendMessage("ゲーム開始まで" + String.valueOf(count) + "秒");
        this.onPlayerMoveEvent(null);

        if (this.count < 1){
            this.cancel();
        }
    }

    public void onPlayerMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        isCancelled();
    }
}
