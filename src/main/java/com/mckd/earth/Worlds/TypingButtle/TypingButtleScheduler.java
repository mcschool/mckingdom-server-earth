package com.mckd.earth.Worlds.TypingButtle;

import com.mckd.earth.Earth;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TypingButtleScheduler extends BukkitRunnable {

    Earth plugin;
    Player player;
    int count;

    public TypingButtleScheduler(Earth plugin, Player player, Player player1,  int count){
        this.plugin = plugin;
        this.player = player;
        this.player = player1;
        this.count = 5;
    }

    @Override
    public void run(){
        this.count--;
        this.player.sendTitle("ゲーム開始まで" + String.valueOf(count) + "秒", "", 0, 20,0);

        if (this.count < 1){
            this.cancel();
        }
    }
}
