package com.mckd.earth.Scheduler;

import com.mckd.earth.Earth;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.scheduler.BukkitRunnable;

import javax.tools.JavaFileManager;

public class PveScheduler extends BukkitRunnable {
    private  Earth plugin;
    public  Player player;
    public  int count;
    public  PveScheduler(Earth plugin, Player player, int count) {
        this.plugin = plugin;
        this.player = player;
        this.count = count;
        //player.sendMessage("aaa");

        outside: for (Chunk chunk : player.getWorld().getLoadedChunks() ) {
            for (BlockState blockState : chunk.getTileEntities()) {
                if (blockState instanceof Sign) {
                    Sign s = (Sign) blockState;
                    if (s.getLine(1).equals("Spawn")) {
                        Location s_loc = s.getLocation();
                        Location n_loc = new Location(s_loc.getWorld(),
                                s_loc.getX() + 5, s_loc.getY(), s_loc.getZ());
                        s_loc.getWorld().spawn(n_loc, Skeleton.class);
                        s_loc.getWorld().spawn(n_loc, Skeleton.class);
                        s_loc.getWorld().spawn(n_loc, Skeleton.class);
                    }

                }
            }
        }
    }

    @Override
    public void run(){
        player.sendMessage("bbb");
        this.count--;
        this.player.sendMessage( String.valueOf(this.count) );
        if( this.count < 1) {
            this.player.sendMessage("START!");
            this.cancel();
        }
    }
}
