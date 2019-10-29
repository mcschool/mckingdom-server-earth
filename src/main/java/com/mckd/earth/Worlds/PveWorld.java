package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import com.mckd.earth.Scheduler.PveScheduler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

import java.util.List;

public class PveWorld<entList> implements Listener {

    private Earth plugin;
    private int waveCount = 1;
    private int enemyCount = 0;

    public PveWorld(Earth plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("pve")) {
            player.sendMessage("Test world へようこそ");
            player.setGameMode(GameMode.ADVENTURE);
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.getWorld().setPVP(false);
            player.getInventory().clear();
            new PveScheduler(this.plugin, player.getWorld(), this.waveCount).runTaskTimer(this.plugin, 0, 20);

            //Score board

            ScoreboardManager sbm = Bukkit.getScoreboardManager();
            Scoreboard sb = sbm.getMainScoreboard();
            Objective obj = sb.getObjective("point");
            if (obj == null) {
                obj = sb.registerNewObjective("point", "test");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                obj.setDisplayName("PVE GOLD");
            }
            Score score = obj.getScore(player.getDisplayName());
            score.setScore(0);
            player.setScoreboard(sb);

            ScoreboardManager sbm2 = Bukkit.getScoreboardManager();
            Scoreboard sb2 = sbm2.getMainScoreboard();
            Objective obj2 = ((Scoreboard) sb2).getObjective("point");
            if (obj2 != null) {
                Score score2 = obj2.getScore(player.getDisplayName());
                int point = (int) score2.getScore();
                score2.setScore(point + 100);
                player.setScoreboard(sb2);
            }
        }
    }

    @EventHandler
    public void signClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        if (p.getWorld().getName().equals("pve") &&
                e.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
                b.getType() == Material.SIGN_POST
        ) {
            Sign sign;
            sign = (Sign) b.getState();
            String line = sign.getLine(1);
            if (line.equals("IRON SWORD -100")) {
                ScoreboardManager sbm = Bukkit.getScoreboardManager();
                Scoreboard sb = sbm.getMainScoreboard();
                Objective obj = sb.getObjective("point");
                if (obj != null) {
                    Score score = obj.getScore(p.getDisplayName());
                    int point = (int) score.getScore();
                    if (point >= 100) {
                        ItemStack item = new ItemStack(Material.IRON_SWORD);
                        p.getInventory().addItem(item);
                        score.setScore(point - 100);
                    } else {
                        p.sendMessage("スコアが100以上必要です!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals("pve")) {
            if (e.getEntity() instanceof Player) {
                Player player = (Player) e.getEntity();
                player.performCommand("mvtp world");
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Zombie) { // if zombie dies
            e.getDrops().clear();
        }
        if(e.getEntity() instanceof Skeleton) { // if Skeleton dies
            e.getDrops().clear();
        }
    }




    @EventHandler
    public  void onEntityDeath(EntityDeathEvent event) {
        World world = event.getEntity().getWorld();
        if (world.getName().equals("pve")) {
            Player p = event.getEntity().getKiller();
            ScoreboardManager sbm = Bukkit.getScoreboardManager();
            Scoreboard sb = sbm.getMainScoreboard();
            Objective obj = ((Scoreboard) sb).getObjective("point");
            if( obj!=null) {
                Score score = obj.getScore(p.getDisplayName());
                int point = (int)score.getScore();
                score.setScore(point+100);
                p.setScoreboard(sb);
            }

            if(this.waveCount>2) this.waveCount=1;
            List<Entity> entities = world.getEntities();
            int count = 0;
            for( Entity entity : world.getEntities() ){
                if( entity.isDead()==false) {
                    if (entity instanceof Monster) {
                        count++;
                    }
                }
            }
            if (count > 0) {
                if (this.enemyCount != count) {
                    this.sendMessageToPlayers(world, "モンスターは残り" + count + "匹!");
                    this.enemyCount = count;
                }
            }else{
                this.sendMessageToPlayers(world,"全モンスターを倒しました!");
                if( this.waveCount<2 ) {
                    this.waveCount++;
                    new PveScheduler(this.plugin,world,this.waveCount).runTaskTimer(this.plugin,0,20);
                }else{
                    this.sendMessageToPlayers(world,"ゲームクリア!");
                    this.waveCount = 1;
                    p.performCommand("mvtp world");
                }
            }
        }
    }

    private void sendMessageToPlayers(World world, String msg){
        for( Player player: world.getPlayers() ){
            player.sendMessage(msg);
        }
    }


}

