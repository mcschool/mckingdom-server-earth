package com.mckd.earth.Worlds.Pve;

import com.mckd.earth.Earth;
import com.mckd.earth.Worlds.Pve.PveScheduler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
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
import org.bukkit.material.Door;
import org.bukkit.material.Openable;
import org.bukkit.scoreboard.*;

import java.util.List;

public class PveWorld implements Listener {

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
            player.sendMessage("Mobs Killer");
            player.setGameMode(GameMode.ADVENTURE);
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.getWorld().setPVP(false);
            player.getInventory().clear();

            // ワールドにいる人数が1人だった場合スケジューラースタート
            List<Player> players = player.getWorld().getPlayers();
            if (players.size() <= 1) {
                new PveScheduler(this.plugin, player.getWorld(), this.waveCount).runTaskTimer(this.plugin, 0, 20);
            }

            //Score board
            ScoreboardManager sbm = Bukkit.getScoreboardManager();
            Scoreboard sb = sbm.getMainScoreboard();
            Objective obj = sb.getObjective("point");
            if (obj == null) {
                obj = sb.registerNewObjective("point", "test");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                obj.setDisplayName("ポイント");
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
        // プレーヤーがいるワールドがpveじゃなかったら何も終わり
        if (!p.getWorld().getName().equals("pve")) {
            return;
        }
        Block b = e.getClickedBlock();
        // 右クリックした "かつ" クリックしたブロックが看板だった場合
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && b.getType() == Material.SIGN_POST) {
            // ここでスコアをみてアイテムをあげる
            // あらかじめ利用する変数を用意しておく
            Sign sign;
            sign = (Sign) b.getState();
            ScoreboardManager sbm = Bukkit.getScoreboardManager();
            Scoreboard sb = sbm.getMainScoreboard();
            Objective obj = sb.getObjective("point");
            Score score = obj.getScore(p.getDisplayName());
            String line = sign.getLine(1);
            int point = score.getScore();
            // 鉄の剣
            if (line.equals("鉄の剣 -100ポイント")) {
                if (point >= 100) {
                    ItemStack item = new ItemStack(Material.IRON_SWORD);
                    p.getInventory().addItem(item);
                    score.setScore(point - 100);
                } else {
                    p.sendMessage("ポイントが100以上必要です!");
                }
            }
            // 鉄の頭
            if (line.equals("鉄の頭 -200ポイント")) {
                if (point >= 200) {
                    ItemStack item = new ItemStack(Material.IRON_HELMET);
                    p.getInventory().addItem(item);
                    score.setScore(point - 200);
                } else {
                    p.sendMessage("ポイントが200以上必要です!");
                }
            }
            //鉄の足
            if (line.equals("鉄の足 -200ポイント")) {
                if (point >= 200) {
                    ItemStack item = new ItemStack(Material.IRON_BOOTS);
                    p.getInventory().addItem(item);
                    score.setScore(point - 200);
                } else {
                    p.sendMessage("ポイントが200以上必要です!");
                }
            }
            //弓
            if (line.equals("弓 -300ポイント")) {
                if (point >= 300) {
                    ItemStack item = new ItemStack(Material.BOW);
                    p.getInventory().addItem(item);
                    score.setScore(point - 300);
                } else {
                    p.sendMessage("ポイントが300以上必要です!");
                }
            }
            //矢
            if (line.equals("矢 -100ポイント")) {
                if (point >= 100) {
                    ItemStack item = new ItemStack(Material.ARROW,5);
                    p.getInventory().addItem(item);
                    score.setScore(point - 100);
                } else {
                    p.sendMessage("ポイントが100以上必要です!");
                }
            }
            /*鉄のチェストプレート
            if (line.equals("鉄のチェストプレート -400ポイント")) {
                if (point >= 400) {
                    ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
                    p.getInventory().addItem(item);
                    score.setScore(point - 400);
                } else {
                    p.sendMessage("ポイントが400以上必要です!");
                }
            }*/
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals("pve")) {
            if (e.getEntity() instanceof Player) {
                Player player = e.getEntity();
                player.sendMessage("death!!!!");
                player.setHealth(20.0);
                player.setGameMode(GameMode.SPECTATOR);
                player.hidePlayer(this.plugin, player);
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

            if(this.waveCount>15) this.waveCount=1;
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
                if( this.waveCount<15 ) {
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

    @EventHandler
    public void noPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("pve")) {
            return;
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = e.getClickedBlock();
            if (block.getType() == Material.IRON_DOOR_BLOCK) {
                Player p = e.getPlayer();
                ScoreboardManager sbm = Bukkit.getScoreboardManager();
                Scoreboard sb = sbm.getMainScoreboard();
                Objective obj = sb.getObjective("point");
                Score score = obj.getScore(p.getDisplayName());
                int point = score.getScore();
                p.sendMessage("今" + String.valueOf(point) + "ポイント持っています");
                if (point >= 500) {
                    score.setScore(point - 500);
                    // 鉄のドアが右クリックされた時
                    BlockState state = block.getState();
                    Openable o = (Openable) state.getData();
                    Door door = (Door) state.getData();
                    if (door.isTopHalf()) {
                        Block set = block.getRelative(BlockFace.DOWN, 1);
                        state = set.getState();
                        o = (Openable) state.getData();
                    }

                    o.setOpen(true);
                    state.update();
                }else{
                    p.sendMessage("後" + String.valueOf(500-point) + "ポイント必要です！");
                }
            }
        }
    }

    private void sendMessageToPlayers(World world, String msg){
        for( Player player: world.getPlayers() ){
            player.sendMessage(msg);
            player.sendTitle(msg,"", 10,40,10);
        }
    }


}

