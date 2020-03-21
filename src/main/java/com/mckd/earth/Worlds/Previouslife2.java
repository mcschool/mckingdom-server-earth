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
    String nowQs = "1-1";



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
        String q = "";
        if (nowQs.equals("1-1")) {
            q = "1-1";
        }
        if (nowQs.equals("1-2")) {
            this.playerRed.sendMessage("test3");
            q = "1-2";
        }
        if (nowQs.equals("1-3")) {
            q = "1-3";
        }
        if (nowQs.equals("1-4")) {
            q = "1-4";
        }
        if (nowQs.equals("1-5")) {
            q = "1-5";
        }
        if (nowQs.equals("2-1")) {
            this.playerRed.sendMessage("test4");
            q = "2-1";
        }
        if (nowQs.equals("2-2")) {
            q = "2-2";
        }
        if (nowQs.equals("2-3")) {
            q = "2-3";
        }
        if (nowQs.equals("2-4")) {
            q = "2-4";
        }
        if (nowQs.equals("3-2")) {
            q = "3-2";
        }
        if (nowQs.equals("3-3")) {
            q = "3-3";
        }
        if (nowQs.equals("3-4")) {
            q = "3-4";
        }
        if (nowQs.equals("4-2")) {
            q = "4-2";
        }
        if (nowQs.equals("4-3")) {
            q = "4-3";
        }
            this.playerRed.sendTitle(q, "", 0, 20000, 0);

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
                    if(nowQs.equals("1-1")){
                        player.sendMessage("test1");
                        nowQs = "2-1";
                    }
                    if(nowQs.equals("1-2")){
                        nowQs = "2-2";
                    }
                    if(nowQs.equals("1-3")){
                        nowQs = "2-3";
                    }
                    if(nowQs.equals("1-4")){
                        nowQs = "2-4";
                    }
                    if(nowQs.equals("1-5")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("2-1")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("2-2")){
                        nowQs = "3-2";
                    }
                    if(nowQs.equals("2-3")){
                        nowQs = "3-3";
                    }
                    if(nowQs.equals("2-4")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("3-2")){
                        nowQs = "4-2";
                    }
                    if(nowQs.equals("3-3")){
                        nowQs = "4-3";
                    }
                    if(nowQs.equals("3-4")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("4-2")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("4-3")){
                        nowQs = "1-1";
                    }

                }
                if (line.equals("No")) {
                    player.chat("no");
                    if(nowQs.equals("1-1")){
                        nowQs = "1-2";
                    }
                    if(nowQs.equals("1-2")){
                        nowQs = "1-3";
                    }
                    if(nowQs.equals("1-3")){
                        nowQs = "1-4";
                    }
                    if(nowQs.equals("1-4")){
                        nowQs = "1-5";
                    }
                    if(nowQs.equals("1-5")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("2-1")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("2-2")){
                        nowQs = "2-3";
                    }
                    if(nowQs.equals("2-3")){
                        nowQs = "2-4";
                    }
                    if(nowQs.equals("2-4")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("3-2")){
                        nowQs = "3-3";
                    }
                    if(nowQs.equals("3-3")){
                        nowQs = "3-4";
                    }
                    if(nowQs.equals("3-4")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("4-2")){
                        nowQs = "1-1";
                    }
                    if(nowQs.equals("4-3")){
                        nowQs = "1-1";
                    }
                }
                player.sendMessage("test2");
                this.Start();
            }
        }
    }
}

