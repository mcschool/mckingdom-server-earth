package com.mckd.earth.TntRun;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TntRunWorld implements Listener {

    Earth plugin;
    String status = "wait";

    public TntRunWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChanged(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("tnt")) {
            return;
        }
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.sendMessage("test1");

        World world = player.getWorld();
        if (world.getPlayers().size() == 1) {
            if (this.status.equals("wait")) {
                this.fillFirstFloor();
                this.fillSecondFloor();
                this.fillThirdFloor();

            }
        }
    }

    @EventHandler
    public void onPlayerIntract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("tnt")) {
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = e.getClickedBlock();
            if (block.getType() == Material.TNT) {
                player.sendMessage("Tnt is clicked");
                this.start();
            }
            if (block.getType() == Material.REDSTONE_BLOCK) {
                player.sendMessage("Redstone is clicked");
                this.fillFloor(player);
            }
        }
    }

    public void start() {
        World world = Bukkit.getWorld("tnt");
        world.setPVP(false);
        List<Player> players = world.getPlayers();
        int c = 0;
        for (Player p : players) {
            c += 3;
            Location location = new Location(world, 0, 0, 0);
            location.add(c, 0, 0);
            p.teleport(location);
        }
    }

    public void fillFloor(Player player) {
        World world = Bukkit.getWorld("tnt");
        Location location = player.getLocation();
        location.add(0, 10, 0);
        Double nowX = location.getX();
        Double nowZ = location.getZ();
        for (int x = 0; x < 30; x++) {
            location.setX(nowX + x);
            for (int z = 0; z < 30; z++) {
                location.setZ(nowZ + z);
                world.getBlockAt(location).setType(Material.TNT);
            }
        }
    }


    public void fillFirstFloor() {
        World world = Bukkit.getWorld("tnt");
        Location location = new Location(world, 0, 0, 0);
        Double nowX = location.getX();
        Double nowZ = location.getZ();
        for (int x = 0; x < 30 ;x++){
            location.setX(nowX + x);
            for (int z = 0; x < 30; z++) {
                location.setZ(nowZ + z);
                world.getBlockAt(location).setType(Material.TNT);
            }
        }
    }

    public void fillSecondFloor() {
        World world = Bukkit.getWorld("tnt");
        Location location = new Location(world,0,0,0);
        location.add(0, 0, 0);
        Double nowX = location.getX();
        Double nowZ = location.getZ();
        for (int X=0; X<30; X++) {
            location.setX(nowX + X);
            for (int z=0; z<30; z++) {
                location.setZ (nowZ + z);
                world.getBlockAt(location).setType(Material.TNT);

            }


        }
    }
    public void fillThirdFloor() {
        World world =Bukkit.getWorld("tnt");
        Location location = new Location(world, 0,0,0);
        location.add(0, 0, 0);
        Double nowX = location.getX();
        Double nowZ = location.getZ();
        for (int X=0; X<30; X++) {
            location.setX(nowX + X);
            for (int Z=0; Z<30; Z++) {
                location.setX(nowX + Z);

            }

        }



    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("tnt")) {
            return;
        }

        Location location = player.getLocation();
        Double nowY = location.getY();
        location.setY(nowY - 1);
        Block block = player.getWorld().getBlockAt(location);
        if (block.getType() == Material.TNT) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    block.setType(Material.AIR);
                }
            }.runTaskLater(this.plugin, 5);

        }
        if (nowY < 3 && player.getGameMode() == GameMode.ADVENTURE) {
            player.sendMessage("Change spectator");
            player.setGameMode(GameMode.SPECTATOR);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage("run mvtp");
                    player.performCommand("mvtp world");
                }
            }.runTaskLater(this.plugin, 100);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equals("tntrun")) {
            return;
        }
        if (player.getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);

        }
    }
}



