package com.mckd.earth.Worlds.TypingButtle;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class TypingButtleWorld implements Listener {
    Player playerRed;
    Player playerBlue;
    Player playerGreen;
    String current_qustion;
    //public Map<String, Integer> taskIds = new HashMap<String, Integer>();
    private Earth plugin;
    String worldname = "ty";

    public TypingButtleWorld(Earth plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equals(this.worldname)) return;
        player.setGameMode(GameMode.ADVENTURE);
        player.setPlayerWeather(WeatherType.CLEAR);
        player.setHealth(20.0);
        player.setFoodLevel(2);
        player.getWorld().setPVP(false);
        player.getInventory().clear();

        //赤チームだよ
        if(player.getWorld().getPlayers().size() == 1){
            Location location = new Location(player.getWorld(),-946, 18, 179);
            player.teleport(location);
            player.sendTitle(ChatColor.WHITE+ "あなたは", ChatColor.RED + "赤チーム" + ChatColor.WHITE + "です", 0, 40, 0);
            this.playerRed = player;

        }

        //青チーム
        if(player.getWorld().getPlayers().size() == 2){
            Location location1 = new Location(player.getWorld(),-946,18, 166);
            player.teleport(location1);
            player.sendTitle(ChatColor.WHITE+ "あなたは", ChatColor.BLUE + "青チーム" + ChatColor.WHITE + "です", 0, 40, 0);
            this.playerBlue = player;
            //new TypingButtleScheduler(this.plugin, this.playerBlue, this.playerRed, 10).runTaskTimer(this.plugin,0,20);
            this.Start();
        }

        if(player.getWorld().getPlayers().size() > 2){
            Location location2 = new Location(player.getWorld(),-946, 27, 170);
            player.teleport(location2);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.GREEN + "観覧者", ChatColor.WHITE + "です", 60, 80, 60);
            this.playerGreen = player;
            ItemStack itemStack = new ItemStack(Material.CHEST);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("ロビーに戻る");
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
        }

    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event){
        String worldname = event.getEntity().getWorld().getName();
        if (worldname.equals(this.worldname)){
            event.setCancelled(true);
            return;
        }
    }


    public void Start(){
        Random r = new Random();
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
        this.playerRed.sendTitle(q,"",0,20000,0);
        this.playerBlue.sendTitle(q,"",0,20000,0);
        this.current_qustion = q;
    }

    public void GameEnd(){
        World world = Bukkit.getWorld(this.worldname);
        List<Player> players = world.getPlayers();
        //World lobby = Bukkit.getWorld("world");
        //Location location = new Location(lobby, -92,10,-251);

        for(Player player:players){
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.performCommand("mvtp world");
                }
            }.runTaskLater(this.plugin, 20);
        }

    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
        if (event.getPlayer().getWorld().getName().equals(this.worldname)) {
            if (event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
                event.setCancelled(true);
            }
            if (event.getPlayer().getGameMode() == GameMode.CREATIVE){
                event.setCancelled(true);
            }
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        if(event.getPlayer().getWorld().getName().equals("ty")){
            Player player = event.getPlayer();
            String mes = event.getMessage();
            String question = this.current_qustion;
            if(mes.equals(question)){
                if(player == this.playerRed){
                    double health = this.playerBlue.getHealth();
                    this.playerBlue.setHealth(health -2.0);
                    this.playerRed.sendMessage("REDの残りの体力は" + this.playerBlue.getHealth() + "です");
                    this.Start();
                    if (this.playerBlue.getHealth() == 0.0) {
                        this.playerBlue.setHealth(2.0);
                        this.playerBlue.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.RED + "RED" + ChatColor.WHITE + "に倒されました〜", "", 0, 60, 0);
                        this.playerRed.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.BLUE + "BLUE" + ChatColor.WHITE + "を倒しました!!", "", 0, 60, 0);
                        this.GameEnd();
                    }
                }
                if(player == this.playerBlue) {
                    double health = this.playerRed.getHealth();
                    this.playerRed.setHealth(health -2.0);
                    this.playerBlue.sendMessage("REDの残りの体力は" + this.playerRed.getHealth() + "です");
                    this.Start();
                    if (this.playerRed.getHealth() == 0.0){
                        this.playerRed.setHealth(2.0);
                        this.playerRed.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.BLUE+ "BLUE" + ChatColor.WHITE + "に倒されました〜", "", 0,60,0);
                        this.playerBlue.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.RED+ "RED" + ChatColor.WHITE + "を倒しました!!", "", 0,60,0);
                        this.GameEnd();

                    }
                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals(this.worldname)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                if (event.getMaterial() == Material.CHEST){
                    this.playerGreen.sendMessage("ロビーに戻ります!");
                    this.GameEnd();
                    this.playerGreen.sendMessage("ロビーに戻ります");
                    this.playerGreen.performCommand("mvtp world");
                }
            }
        }
    }

}
