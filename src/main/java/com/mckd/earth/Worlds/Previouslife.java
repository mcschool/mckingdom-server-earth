package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Random;

public class Previouslife implements Listener {
    String worldName = "Previouslife";
    String current_qustion;
    String Correct_answer;
    String Incorrect_answer;
    Player playerRed;


    public Previouslife(Earth plugin) {
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
            this.Correct_answer();
            this.Incorrect_answer();
        }
    }



    public void Start() {
        Random r = new Random();
        int n = r.nextInt(10);
        String q = "a";
        if (n == 0) {
            q = "前世は王様だと思う";
        }
        if (n == 1) {
            q = "前世は毎日朝ごはん食べている";
        }
        if (n == 2) {
            q = "前世は水をよく飲む";
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
        if (n == 7) {
            q = "人間じゃなかった";
        }
        if (n == 8) {
            q = "大食い";
        }
        if (n == 9) {
            q = "毎日充実していた";
        }
        this.playerRed.sendTitle(q,"",0,20000,0);
        this.current_qustion = q;
    }

    public void Next() {
        Random r = new Random();
        int n = r.nextInt(5);
        String u = "a";
        if (n == 0) {
            u = "あなたの前世はセミです";
        }
        if (n == 1) {
            u = "あなたの前世は醤油です";
        }
        if (n == 2) {
            u = "あなたの前世は織田信長です";
        }
        if (n == 3) {
            u = "あなたの前世はみじんこです";
        }
        if (n == 4) {
            u = "あなたの前世はコンクリートです";
        }
        this.playerRed.sendTitle(u,"",0,20000,0);
    }


    public void Correct_answer() {
        String c = "yes";
        this.Correct_answer = c;
    }


    public void Incorrect_answer(){
        String i = "no";
        this.Incorrect_answer = i;
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        if (event.getPlayer().getWorld().getName().equals("Previouslife")) {
            Player player = event.getPlayer();
            String mes = event.getMessage();
            String question = this.Correct_answer;
            if (mes.equals(question)) {
                if (player == this.playerRed) {
                    Random r = new Random();
                    int n = r.nextInt(3);
                    if (n ==0){
                        this.Next();
                    }
                    if (n == 1) {
                        this.Start();
                    }
                }
            }
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onAsyncPlayerChatEvent2(AsyncPlayerChatEvent event) {
        if (event.getPlayer().getWorld().getName().equals("Previouslife")) {
            Player player = event.getPlayer();
            String mes = event.getMessage();
            String question = this.Incorrect_answer;
            if (mes.equals(question)) {
                if (player == this.playerRed) {
                    Random r = new Random();
                    int n = r.nextInt(3);
                    if (n ==0){
                        this.Next();
                    }
                    if (n == 1) {
                        this.Start();
                    }
                }
            }
            event.setCancelled(true);
        }
    }

    /*private void sendMessageToPlayers(World world, String msg) {
        for (Player player : world.getPlayers()) {
            player.sendMessage(msg);
            player.sendTitle(msg, "", 10, 40, 10);
        }
    }*/
}



