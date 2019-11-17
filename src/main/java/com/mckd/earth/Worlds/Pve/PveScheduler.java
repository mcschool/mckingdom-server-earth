package com.mckd.earth.Worlds.Pve;

import com.mckd.earth.Earth;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

public class PveScheduler extends BukkitRunnable {
    private  Earth plugin;
    public  World world;
    public  Player player;
    public  int count;
    public int wave;
    public boolean flag = false;


    public PveScheduler(Earth plugin, World world, int wave) {
        this.plugin = plugin;
        this.world = world;
        this.count = 10;
        this.wave = wave;

    }

    @Override
    public  void run(){
        this.count--;
        this.sendMessageToPlayers(this.world, String.valueOf(this.count) );
        if( this.count <1) {
            this.sendMessageToPlayers(this.world, "Mobs Killer START!");
            outside: for (Chunk chunk : this.world.getLoadedChunks() ) {
                for (BlockState blockState : chunk.getTileEntities()) {
                    if (blockState instanceof Sign) {
                        Sign s = (Sign) blockState;
                        if (s.getLine(1).equals("Spawn")) {
                            Location s_loc = s.getLocation();
                            Location n_loc = new Location(s_loc.getWorld(),
                                    s_loc.getX() + 0, s_loc.getY() + 3, s_loc.getZ());
                            this.sendMessageToPlayers(this.world, String.valueOf(this.wave) );
                            if(this.wave==1) {
                                s_loc.getWorld().spawn(n_loc, Zombie.class);
                                s_loc.getWorld().spawn(n_loc, Zombie.class);
                                s_loc.getWorld().spawn(n_loc, Zombie.class);
                            }
                            if(this.wave==2) {
                                s_loc.getWorld().spawn(n_loc, Skeleton.class);
                                s_loc.getWorld().spawn(n_loc, Skeleton.class);
                            }
                            if(this.wave==3) {
                                s_loc.getWorld().spawn(n_loc, Skeleton.class);
                                s_loc.getWorld().spawn(n_loc, Skeleton.class);
                                s_loc.getWorld().spawn(n_loc, Zombie.class);
                            }
                            if(this.wave==4) {
                                s_loc.getWorld().spawn(n_loc, Skeleton.class);
                                s_loc.getWorld().spawn(n_loc, Skeleton.class);
                                s_loc.getWorld().spawn(n_loc, Zombie.class);
                                s_loc.getWorld().spawn(n_loc, Zombie.class);
                            }
                        }

                    }
                }
            }

            this.cancel();
        }
    }
    private void sendMessageToPlayers(World world, String msg){
        for( Player player: world.getPlayers() ){
            player.sendMessage(msg);
        }
    }
}