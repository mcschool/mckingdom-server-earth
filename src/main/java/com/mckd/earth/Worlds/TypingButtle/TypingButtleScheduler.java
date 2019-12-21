package com.mckd.earth.Worlds.TypingButtle;

import com.mckd.earth.Earth;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TypingButtleScheduler extends BukkitRunnable implements Listener {
    private int count;
    private Player player;
    private Earth plugin;
    String worldname = "ty";

    public TypingButtleScheduler(Earth plugin, Player player, int count){
        this.plugin = plugin;
        this.player = player;
        this.count = count;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void run() {
        Random rnd = new Random();
        int n = rnd.nextInt(27);
        String q = "a", qs = "as";
        if (n == 0) {
            q = "HELLO";
            qs = "こんにちわ";
        }
        if (n == 1) {
            q = "GOODBY";
            qs = "さようなら";
        }
        if (n == 2) {
            q = "SEEYOU";
            qs = "またね";
        }
        if (n == 3) {
            q = "MONDAY";
            qs = "げつようび";
        }
        if (n == 4) {
            q = "SUNDAY";
            qs = "にちようび";
        }
        if (n == 5) {
            q = "RED";
            qs = "あか";
        }
        if (n == 6) {
            q = "BLUE";
            qs = "あお";
        }
        if (n == 7) {
            q = "YELLOW";
            qs = "きいろ";
        }
        if (n == 8) {
            q = "TURTLE";
            qs = "かめ";
        }
        if (n == 9) {
            q = "FORWARD";
            qs = "まえ";
        }
        if (n == 10) {
            q = "BACK";
            qs = "うしろ";
        }
        if (n == 11) {
            q = "UP";
            qs = "うえ";
        }
        if (n == 12) {
            q = "DOWN";
            qs = "した";
        }
        if (n == 13) {
            q = "DIG";
            qs = "こわす";
        }
        if (n == 14) {
            q = "REDSTONE";
            qs = "レッドストーン";
        }
        if (n == 15) {
            q = "MINECRAFT";
            qs = "マインクラフト";
        }
        if (n == 16) {
            q = "SWORD";
            qs = "けん";
        }
        if (n == 17) {
            q = "ARROW";
            qs = "や(矢)";
        }
        if (n == 18) {
            q = "BOW";
            qs = "ゆみ";
        }
        if (n == 19) {
            q = "STONE";
            qs = "いし";
        }
        if (n == 20) {
            q = "LAMP";
            qs = "ランプ";
        }
        if (n == 21) {
            q = "ARMOR";
            qs = "よろい";
        }
        if (n == 22) {
            q = "DIAMOND";
            qs = "ダイアモンド";
        }
        if (n == 23) {
            q = "GOLD";
            qs = "きん(金)";
        }
        if (n == 24) {
            q = "SAND";
            qs = "すな(砂)";
        }
        if (n == 25) {
            q = "BOOK";
            qs = "ほん(本)";
        }
        if (n == 26) {
            q = "FLOWER";
            qs = "はな(花)";
        }
        if (n == 27) {
            q = "DOOR";
            qs = "ドア(鉄)";
        }

        FileConfiguration conf = this.plugin.getConfig();
        conf.set(this.player.getName() + "-kids-move", "not-move");
        conf.set(this.player.getName() + "-kids-q", q);
        this.player.sendTitle(ChatColor.WHITE + q, ChatColor.WHITE + qs, 0, 200, 0);


    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        if(event.getPlayer().getWorld().getName().equals(worldname)){
            String mes = event.getMessage();
            FileConfiguration conf = this.plugin.getConfig();
            String q = conf.getString(this.player.getName()+"-kids-q");
            if(q.toLowerCase().equals(mes)){
                conf.set(this.player.getName()+"-kids-move", "ok");
            }
        }
    }

}
