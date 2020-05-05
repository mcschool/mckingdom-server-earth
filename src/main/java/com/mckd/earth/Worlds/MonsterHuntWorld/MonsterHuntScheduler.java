package com.mckd.earth.Worlds.MonsterHuntWorld;

import com.mckd.earth.Earth;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MonsterHuntScheduler extends BukkitRunnable {
    private Earth plugin;
    public Player player;
    public int count;

    public MonsterHuntScheduler(Earth plugin,int count){
        this.plugin = plugin;
        this.count = 10;
    }

    @Override
    public void run(){
        this.count--;
        this.player.sendMessage("ゲーム開始まで" + String.valueOf(count) + "秒");

        if (this.count < 1){
            this.player.sendMessage("ゲームスタート！");
            this.cancel();
        }
    }

}
