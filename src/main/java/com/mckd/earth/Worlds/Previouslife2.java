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
    String current_qustion;



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
        int n = r.nextInt(7);
        String q = "a";
        if (n == 0) {
            q = "ゲームが好き";
        }
        if (n == 1) {
            q = "朝ごはんを食べた";
        }
        if (n == 2) {
            q = "水をよく飲む";
        }
        if (n == 3) {
            q = "勉強が得意";
        }
        if (n == 4) {
            q = "空を見るのが好き";
        }
        if (n == 5) {
            q = "運動が得意";
        }
        if (n == 6) {
            q = "肉と魚だったら肉の方が好き";
        }

        this.playerRed.sendTitle(q, "", 0, 20000, 0);
        this.current_qustion = q;
    }

    public void Next(){
        Random r = new Random();
        int n = r.nextInt(7);
        String q = "a";
        if (n == 0) {
            q = "あなたの前世はセミです";
        }
        if (n == 1) {
            q = "あなたの前世は醤油です";
        }
        if (n == 2) {
            q = "あなたの前世は織田信長です";
        }
        if (n == 3) {
            q = "あなたの前世はみじんこです";
        }
        if (n == 4) {
            q = "あなたの前世はコンクリートです";
        }
        this.playerRed.sendTitle(q, "", 0, 20000, 0);
        this.current_qustion = q;

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
                        if (player == this.playerRed) {
                            Random r = new Random();
                            int n = r.nextInt(2);
                            if (n ==0){
                                this.Next();
                            }
                            if (n == 1) {
                                this.Start();
                            }
                        }
                }
                if (line.equals("No")) {
                    player.chat("no");
                    if (player == this.playerRed) {
                        Random r = new Random();
                        int n = r.nextInt(2);
                        if (n ==0){
                            this.Next();
                        }
                        if (n == 1) {
                            this.Start();
                        }
                    }
                }
            }
        }
    }
}

