package com.mckd.earth.Worlds.TntGame;

import com.mckd.earth.Earth;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TntGameScheduler extends BukkitRunnable {

    Earth plugin;
    Player player;
    int count;
    String worldname = "minigame";

    public TntGameScheduler(Earth plugin, Player player, int count){
        this.plugin = plugin;
        this.player = player;
        this.count = 5;
    }

    @Override
    public void run(){
        this.count--;
        this.player.sendMessage("ゲーム開始まで" + String.valueOf(count) + "秒");
        this.movement(Bukkit.getWorld(this.worldname));

        if (this.count < 1){
            this.player.sendMessage(ChatColor.RED + "ゲーム開始");
            this.cancel();
        }
    }

    private void movement(World world){
        for (Player player : world.getPlayers()){
            isCancelled();
        }
    }

}
