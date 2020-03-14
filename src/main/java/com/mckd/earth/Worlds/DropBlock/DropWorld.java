package com.mckd.earth.Worlds.DropBlock;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class DropWorld implements Listener {
    private Earth plugin;
    String worldname = "fall";

    public DropWorld(Earth plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(this.worldname)) return;
        player.setGameMode(GameMode.ADVENTURE);
        player.setPlayerWeather(WeatherType.CLEAR);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getWorld().setPVP(false);
        player.sendMessage("あなたの前世は王子様orお姫様だと思う");
    }

    /*
    @EventHandler
    public void signClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(this.worldname)){
            return;
        }
        Block block = event.getClickedBlock();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType() == Material.SIGN_POST) {
            Sign sign;
            sign = (Sign) block.getState();
            String line = sign.getLine(1);
            if (line.equals("YES")){
                player.sendMessage("あなたの前世は豚です");
            }else{
                player.sendMessage("朝ごはんは毎日食べる");
                if (line.equals("YES")){
                    player.sendMessage("鳩が寄ってくる");
                }else{
                    player.sendMessage("雲を眺めるのが好き");
                }
            }
        }
    }*/

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String mes = event.getMessage();
        if (mes.equals("y")){
            player.sendMessage("あなたの前世は豚です");
        }
        if (mes.equals("n")){
            player.sendMessage("朝ごはんは毎日食べている");
            if (mes.equals("y")){
                player.sendMessage("鳩が乗ってくる");
            }
            if (mes.equals("n")){
                player.sendMessage("雲を眺めるのが好き");
            }
        }
    }
}

