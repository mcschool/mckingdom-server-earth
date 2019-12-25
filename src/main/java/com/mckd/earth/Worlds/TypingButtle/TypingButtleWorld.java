package com.mckd.earth.Worlds.TypingButtle;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitTask;

import javax.xml.soap.Text;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TypingButtleWorld implements Listener {
    Player playerRed;
    Player playerBlue;
    String current_qustion;
    //public Map<String, Integer> taskIds = new HashMap<String, Integer>();
    private Earth plugin;
    String worldname = "ty";

    public TypingButtleWorld(Earth plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equals(this.worldname)) return;
        player.setGameMode(GameMode.ADVENTURE);
        player.setPlayerWeather(WeatherType.CLEAR);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getWorld().setPVP(false);
        player.getInventory().clear();

        //赤チーム
        if(player.getWorld().getPlayers().size() == 1){
            Location location = new Location(player.getWorld(),-946, 18, 179);
            player.teleport(location);
            player.sendTitle(ChatColor.WHITE+ "あなたは", ChatColor.RED + "赤チーム" + ChatColor.WHITE + "です", 60, 80, 60);
            this.playerRed = player ;
        }

        //青チーム
        if(player.getWorld().getPlayers().size() == 2){
            Location location1 = new Location(player.getWorld(),-946,18, 166);
            player.teleport(location1);
            player.sendTitle(ChatColor.WHITE+ "あなたは", ChatColor.BLUE + "青チーム" + ChatColor.WHITE + "です", 60, 80, 60);
            this.playerBlue = player;
            this.Start();
        }
        if(player.getWorld().getPlayers().size() > 2){
            Location location2 = new Location(player.getWorld(),-946, 27, 170);
            player.teleport(location2);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.GREEN + "観覧者", ChatColor.WHITE + "です", 60, 80, 60);
        }
    }


    public void Start(){
        Random r = new Random();
        int n = r.nextInt(10);
        String q = "a";
        if(n == 0) {q="hello";}
        if(n == 1) {q="goodbye";}
        if(n == 2) {q="apple";}
        if(n == 3) {q="banana";}
        if(n == 4) {q="peach";}
        if(n == 5) {q="goodmorning";}
        if(n == 6) {q="dog";}
        if(n == 7) {q="moutain";}
        if(n == 8) {q="yellow";}
        if(n == 9) {q="orange";}
        this.playerRed.sendTitle(q,"",0,20000,0);
        this.playerBlue.sendTitle(q,"",0,20000,0);
        this.current_qustion = q;
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        if(event.getPlayer().getWorld().getName().equals("ty")){
            Player player = event.getPlayer();
            String mes = event.getMessage();
            String question = this.current_qustion;
            if(mes.equals(question)){
                if(player == this.playerRed){
                    this.playerRed.sendMessage("正解だよ");
                    this.playerBlue.sendMessage("REDが正解しました");
                    this.Start();
                }
                if(player == this.playerBlue) {
                    this.playerBlue.sendMessage("正解だよ");
                    this.playerRed.sendMessage("BLUEが正解した");
                    this.Start();
                }
            }
        }
    }
}
