package com.mckd.earth.Worlds.TntRun;

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
    }

    @EventHandler
    public void onPlayerChanged(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("tnt")) {
            return;
        }
        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);

        // 移動してきた時に最初の一人だった場合
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
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("tnt")) {
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = e.getClickedBlock();
            if (block.getType() == Material.TNT) {
                this.start();
            }
            if (block.getType() == Material.REDSTONE_BLOCK) {
                this.fillFloor();
            }
            // ダイヤモンドを右クリックした場合強制的に状態を待機にする
            if (block.getType() == Material.DIAMOND_BLOCK) {
                this.status = "wait";
            }
            // ダイヤモンドを右クリックした場合強制的に状態をゲーム中にする
            if (block.getType() == Material.ANVIL) {
                this.status = "playing";
            }
            // シーランタンを右クリックしたらブロックを埋める
            if (block.getType() == Material.SEA_LANTERN) {
                this.fillFirstFloor();
                this.fillSecondFloor();
                this.fillThirdFloor();
            }
        }
    }

    // ゲームを開始するプログラム
    public void start() {
        World world = Bukkit.getWorld("tnt");
        world.setPVP(false);
        List<Player> players = world.getPlayers();
        int c = 0;
        for (Player p: players) {
            // 開始地点にテレポート
            c += 3;
            Location location = new Location(world, 0, 0, 0);
            location.add(c, 0, 0);
            p.teleport(location);
        }
    }

    // TNTで埋めるプログラム
    public void fillFloor() {
        World world = Bukkit.getWorld("tnt");
        // TNTで埋めるスタート地点を指定
        Location location = new Location(world, 0, 0, 0);
        location.add(0, 0, 0);
        Double nowX = location.getX();
        Double nowZ = location.getZ();
        for (int x=0; x<30; x++) {
            location.setX(nowX + x);
            for (int z=0; z<30; z++) {
                location.setZ(nowZ + z);
                world.getBlockAt(location).setType(Material.TNT);
            }
        }
    }


    // 1階層目をTNTで埋めるプログラム
    public void fillFirstFloor() {
        World world = Bukkit.getWorld("tnt");
        // TNTで埋めるスタート地点を指定
        Location location = new Location(world, 0, 0, 0);
        location.add(0, 0, 0);
        Double nowX = location.getX();
        Double nowZ = location.getZ();
        for (int x=0; x<30; x++) {
            location.setX(nowX + x);
            for (int z=0; z<30; z++) {
                location.setZ(nowZ + z);
                world.getBlockAt(location).setType(Material.TNT);
            }
        }
    }

    // 2階層目をTNTで埋めるプログラム
    public void fillSecondFloor() {
        World world = Bukkit.getWorld("tnt");
        // TNTで埋めるスタート地点を指定
        Location location = new Location(world, 0, 0, 0);
        location.add(0, 0, 0);
        Double nowX = location.getX();
        Double nowZ = location.getZ();
        for (int x=0; x<30; x++) {
            location.setX(nowX + x);
            for (int z=0; z<30; z++) {
                location.setZ(nowZ + z);
                world.getBlockAt(location).setType(Material.TNT);
            }
        }
    }

    // 3階層目をTNTで埋めるプログラム
    public void fillThirdFloor() {
        World world = Bukkit.getWorld("tnt");
        // TNTで埋めるスタート地点を指定
        Location location = new Location(world, 0, 0, 0);
        location.add(0, 0, 0);
        Double nowX = location.getX();
        Double nowZ = location.getZ();
        for (int x=0; x<30; x++) {
            location.setX(nowX + x);
            for (int z=0; z<30; z++) {
                location.setZ(nowZ + z);
                world.getBlockAt(location).setType(Material.TNT);
            }
        }
    }

    // プレーヤーが動くたびに呼ばれる
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("tnt")) {
            return;
        }
        // プレーヤーの一個したに座標を移動して
        // ブロックの種類を調べる
        Location location = player.getLocation();
        Double nowY = location.getY();
        location.setY(nowY - 1);
        Block block = player.getWorld().getBlockAt(location);
        if (block.getType() == Material.TNT) {
            if (this.status.equals("playing")) {
                // 5tick後にブロックを消す
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.AIR);
                    }
                }.runTaskLater(this.plugin, 5);
            }
        }
        if (nowY < 10 && player.getGameMode() == GameMode.ADVENTURE) {
            player.setGameMode(GameMode.SPECTATOR);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.performCommand("mvtp world");
                }
            }.runTaskLater(this.plugin, 100);
        }
    }

    // ブロックを壊せないようにする
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
