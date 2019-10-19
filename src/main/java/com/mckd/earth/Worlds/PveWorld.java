package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import com.mckd.earth.Scheduler.PveScheduler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

public class PveWorld implements Listener {

    private  Earth plugin;
    private  int waveCount = 1;
    private  int enemyCount = 0;
    public  PveWorld(Earth plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public  void enterWorld (PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("pve")) {
            player.sendMessage("Test world へようこそ");
            player.setGameMode(GameMode.ADVENTURE);
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.getWorld().setPVP(false);
            player.getInventory().clear();
            new PveScheduler(this.plugin,player.getWorld(),this.waveCount).runTaskTimer(this.plugin, 0, 20);

            //Score board
            ScoreboardManager sbm = Bukkit.getScoreboardManager();
            Scoreboard sb = sbm.getMainScoreboard();
            Objective obj = sb.getObjective("point");
            if( obj==null) {
                obj = sb.registerNewObjective("point", "test");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                obj.setDisplayName("PVE");
            }
            Score score = obj.getScore(player.getDisplayName());
            score.setScore(0);
            player.setScoreboard(sb);
        }
    }
@EventHandler
    public void signClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        if(p.getWorld().getName().equals("pve") &&
                e.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
                b.getType() == Material.SIGN_POST
        ){
            Sign sign;
            sign = (Sign) b.getState();
            String line = sign.getLine(1);
            if( line.equals("item") ){
                ScoreboardManager sbm = Bukkit.getScoreboardManager();
                Scoreboard sb =  sbm.getMainScoreboard();
                Objective obj = sb.getObjective("point");
                if( obj!=null) {
                    Score score = obj.getScore(p.getDisplayName());
                    int point = (int)score.getScore();
                    if( point>=100 ) {
                        ItemStack item = new ItemStack(Material.WOOD_SWORD);
                        p.getInventory().addItem(item);
                        score.setScore(point - 100);
                    }else{
                        p.sendMessage("スコアが100以上必要です!");
                    }
                }
            }
        }
    }
}
