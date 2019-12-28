package com.mckd.earth.Worlds.SkyWars;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Material.GLASS;

public class SkyWarsScheduler extends BukkitRunnable {
    private final World world;
    private int count;
    private Earth plugin;
    private int enemyCount = 0;


    public SkyWarsScheduler(Earth plugin, World world) {
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
        world.getBlockAt(473, 15, -874).setType(Material.AIR);
        world.getBlockAt(473, 16, -874).setType(Material.AIR);
        world.getBlockAt(474, 15, -875).setType(Material.AIR);
        world.getBlockAt(474, 16, -875).setType(Material.AIR);
        world.getBlockAt(472, 15, -875).setType(Material.AIR);
        world.getBlockAt(472, 16, -875).setType(Material.AIR);
        world.getBlockAt(473, 15, -876).setType(Material.AIR);
        world.getBlockAt(473, 16, -876).setType(Material.AIR);
        world.getBlockAt(473, 14, -875).setType(Material.AIR);
        world.getBlockAt(473, 17, -875).setType(Material.AIR);


        world.getBlockAt(427, 15, -874).setType(Material.AIR);
        world.getBlockAt(427, 16, -874).setType(Material.AIR);
        world.getBlockAt(428, 15, -875).setType(Material.AIR);
        world.getBlockAt(428, 16, -875).setType(Material.AIR);
        world.getBlockAt(426, 15, -875).setType(Material.AIR);
        world.getBlockAt(426, 16, -875).setType(Material.AIR);
        world.getBlockAt(427, 15, -876).setType(Material.AIR);
        world.getBlockAt(427, 16, -876).setType(Material.AIR);
        world.getBlockAt(427, 14, -875).setType(Material.AIR);
        world.getBlockAt(427, 17, -875).setType(Material.AIR);
    }
}

