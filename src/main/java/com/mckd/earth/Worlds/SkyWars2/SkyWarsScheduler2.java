package com.mckd.earth.Worlds.SkyWars2;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SkyWarsScheduler2 extends BukkitRunnable {
    private final World world;
    private int count;
    private Earth plugin;
    private int enemyCount = 0;


    public SkyWarsScheduler2(Earth plugin, World world) {
        this.plugin = plugin;
        this.world = world;
        this.count = 10;

    }


    @Override
    public void run() {
        this.count--;
        this.sendMessageToPlayers(this.world, String.valueOf(this.count));
        if (this.count < 1) {
            this.sendMessageToPlayers(this.world, "SkyWars START!");
            this.changeGamemode(this.world);
            this.cancel();
        }
    }

    private void sendMessageToPlayers(World world, String msg) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(msg);
            player.sendTitle(msg, "", 0, 20, 0);
        }
    }

    private void changeGamemode(World world) {
        for (Player player : world.getPlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        //1人目のガラス
        world.getBlockAt(-176, 16, -2).setType(Material.AIR);
        world.getBlockAt(-176, 17, -2).setType(Material.AIR);
        world.getBlockAt(-174, 16, -2).setType(Material.AIR);
        world.getBlockAt(-174, 17, -2).setType(Material.AIR);
        world.getBlockAt(-175, 16, -3).setType(Material.AIR);
        world.getBlockAt(-175, 17, -3).setType(Material.AIR);
        world.getBlockAt(-175, 16, -1).setType(Material.AIR);
        world.getBlockAt(-175, 17, -1).setType(Material.AIR);
        world.getBlockAt(-175, 15, -2).setType(Material.AIR);
        world.getBlockAt(-175, 18, -2).setType(Material.AIR);


        world.getBlockAt(-232, 16, -2).setType(Material.AIR);
        world.getBlockAt(-232, 17, -2).setType(Material.AIR);
        world.getBlockAt(-234, 16, -2).setType(Material.AIR);
        world.getBlockAt(-234, 17, -2).setType(Material.AIR);
        world.getBlockAt(-233, 16, -1).setType(Material.AIR);
        world.getBlockAt(-233, 17, -1).setType(Material.AIR);
        world.getBlockAt(-233, 16, -3).setType(Material.AIR);
        world.getBlockAt(-233, 17, -3).setType(Material.AIR);
        world.getBlockAt(-233, 15, -2).setType(Material.AIR);
        world.getBlockAt(-233, 18, -2).setType(Material.AIR);
    }
}

