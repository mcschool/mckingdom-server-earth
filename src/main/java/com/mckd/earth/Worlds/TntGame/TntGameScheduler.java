package com.mckd.earth.Worlds.TntGame;

import com.mckd.earth.Earth;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

        if (this.count < 1){
            this.player.sendMessage(ChatColor.RED + "ゲーム開始");
            this.cancel();
        }
    }
}