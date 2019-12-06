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
                            Location sign_loc = s.getLocation();
                            this.spawnMonsters(sign_loc);
                        }
                    }
                }
            }

            this.cancel();
        }
    }

    // モンスターを発生させるプログラムを関数にまとめて読みやすくする
    private void spawnMonsters(Location sign_loc) {
        Location spawn_loc = new Location(sign_loc.getWorld(), sign_loc.getX() + 0, sign_loc.getY() + 3, sign_loc.getZ());
        this.sendMessageToPlayers(this.world, String.valueOf(this.wave) );
        if(this.wave==1) {
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==2) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
        }
        if(this.wave==3) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==4) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==5) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==6) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==7) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==8) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==9) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==10) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==11) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==12) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==13) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==14) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
        if(this.wave==15) {
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Skeleton.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
            sign_loc.getWorld().spawn(spawn_loc, Zombie.class);
        }
    }

    private void sendMessageToPlayers(World world, String msg){
        for( Player player: world.getPlayers() ){
            player.sendMessage(msg);
            player.sendTitle(msg,"", 0,20,0);
        }
    }
}