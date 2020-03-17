package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Random;

public class Previouslife implements Listener {
    String worldName = "Previouslife";
    String current_qustion;


    public Previouslife(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("You ar at" + player.getWorld().getName());
        if (player.getWorld().getName().equals(this.worldName)) {
            player.setGameMode(GameMode.ADVENTURE);
            player.setPlayerWeather(WeatherType.CLEAR);
            player.setFoodLevel(20);
            player.setHealth(20);
            player.getInventory().clear();

            player.sendMessage("これからあなたの前世診断を始めます");

            this.Start();
            /*Random r = new Random();
            int n = r.nextInt(10);
            String q = "a";
            if(n == 0) {q="hello";}
            if(n == 1) {q="good";}
            if(n == 2) {q="apple";}
            if(n == 3) {q="blue";}
            if(n == 4) {q="peach";}
            if(n == 5) {q="red";}
            if(n == 6) {q="dog";}
            if(n == 7) {q="eye";}
            if(n == 8) {q="nose";}
            if(n == 9) {q="hair";}
            this.current_qustion = q;*/
        }
    }

    public void Start() {
        Random r = new Random();
        int n = r.nextInt(10);
        String q = "a";
        if (n == 0) {
            q = "hello";
        }
        if (n == 1) {
            q = "good";
        }
        if (n == 2) {
            q = "apple";
        }
        if (n == 3) {
            q = "blue";
        }
        if (n == 4) {
            q = "peach";
        }
        if (n == 5) {
            q = "red";
        }
        if (n == 6) {
            q = "dog";
        }
        if (n == 7) {
            q = "eye";
        }
        if (n == 8) {
            q = "nose";
        }
        if (n == 9) {
            q = "hair";
        }
        this.current_qustion = q;
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        if (event.getPlayer().getWorld().getName().equals("Previouslife")) {
            Player player = event.getPlayer();
            String mes = event.getMessage();
            String question = this.current_qustion;
            if (mes.equals(question)) {


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



