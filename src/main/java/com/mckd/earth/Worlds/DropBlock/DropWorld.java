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
            String line2 = sign.getLine(2);
            if (line.equals("YES")){
                player.sendMessage("あなたの前世は豚です");
            }
            if (line.equals("NO")){
                player.sendMessage("朝ごはんは毎日食べる");
                if (line.equals("YES")){
                    player.sendMessage("鳩が寄ってくる");
                    if (line.equals("YES")){
                        player.sendMessage("掛け算が割と得意");
                        if (line.equals("YES")){
                            player.sendMessage(("あなたの前世はみじんこです"));
                        }
                        if (line.equals("NO")){
                            player.sendMessage("道路に落ちている軍手をよく食べている");
                        }
                    }
                    if (line.equals("NO")){
                        player.sendMessage("雲を眺めるのが好き");
                    }
                }
                if (line.equals("NO")){
                    player.sendMessage("水をよく飲む");
                    if (line.equals("YES")){
                        player.sendMessage("雲を眺めるのが好き");
                        if (line.equals("YES")){
                            player.sendMessage("道路に落ちている軍手をよく食べる");
                            if (line.equals("YES")){
                                player.sendMessage("あなたの前世は.....ないです");
                            }
                            if (line.equals("NO")){
                                player.sendMessage("あなたの前世は醤油ですかね");
                            }
                        }
                        if (line.equals("NO")){
                            player.sendMessage("あなたの前世は食物繊維です");
                        }
                    }
                    if (line.equals("NO")){
                        player.sendMessage("肉と魚だったら、どちらかといえば魚");
                        if (line.equals("YES")){
                            player.sendMessage("あなたの前世は食物繊維です");
                        }
                        if (line.equals("NO")){
                            player.sendMessage("あなたの前世は自由の女神です");
                        }
                    }
                }
            }
        }
    }
}

