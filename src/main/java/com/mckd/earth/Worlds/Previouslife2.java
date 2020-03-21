package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Random;

public class Previouslife2 implements Listener {
    Player playerRed;
    String worldName = "Previouslife2";


    public Previouslife2(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals(this.worldName)) {
            this.playerRed = player;
            player.setGameMode(GameMode.ADVENTURE);
            player.setPlayerWeather(WeatherType.CLEAR);
            player.setFoodLevel(20);
            player.setHealth(20);
            player.getInventory().clear();

            player.sendMessage("これからあなたの前世診断を始めます");

            this.Start();
        }
    }


    public void Start() {
        Random r = new Random();
        int n = r.nextInt(10);
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";
        String f = "f";
        String g = "g";
        if (n == 0) {
            a = "前世は王様だと思う";
        }
        if (n == 1) {
            b = "前世は毎日朝ごはん食べている";
        }
        if (n == 2) {
            c = "前世は水をよく飲む";
        }
        if (n == 3) {
            d = "勉強が得意";
        }
        if (n == 4) {
            e = "空を見るのが好き";
        }
        if (n == 5) {
            f = "運動が得意";
        }
        if (n == 6) {
            g = "肉と魚だったら肉の方が好き";
        }

        this.playerRed.sendTitle(a, "", 0, 20000, 0);
        this.playerRed.sendTitle(b, "", 0, 20000, 0);
        this.playerRed.sendTitle(c, "", 0, 20000, 0);
        this.playerRed.sendTitle(d, "", 0, 20000, 0);
        this.playerRed.sendTitle(e, "", 0, 20000, 0);
        this.playerRed.sendTitle(f, "", 0, 20000, 0);
        this.playerRed.sendTitle(g, "", 0, 20000, 0);




    }

    @EventHandler
    public void signClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equals(this.worldName)) {
            Block b = e.getClickedBlock();
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && b.getType() == Material.SIGN_POST) {
                Sign sign;
                sign = (Sign) b.getState();
                String line = sign.getLine(1);
                if (line.equals("Yes")) {
                    player.chat("yes");
                }
                if (line.equals("No")) {
                    player.chat("no");
                }
            }
        }
    }
}

