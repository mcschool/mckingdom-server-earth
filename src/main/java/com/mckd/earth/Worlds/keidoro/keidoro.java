package com.mckd.earth.Worlds.keidoro;

import com.mckd.earth.Earth;
import com.mckd.earth.Worlds.OniCountDownScheduler;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class keidoro implements Listener {

    Earth plugin;
    String worldName = "keidoro";

    public keidoro(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (!player.getWorld().getName().equals(this.worldName)) return;
        //インベントリを空にする
        player.getInventory().clear();
        //ゲームモード変更
        player.setGameMode(GameMode.SURVIVAL);
        //空腹度をMAXにする
        player.setFoodLevel(20);
        player.getWorld().setPVP(true);
        //体力をMAXにする
        player.setHealth(18.0);
        for (Player p :world.getPlayers()){
            if (isOni(p)){
                player.sendMessage(ChatColor.WHITE + "現在の鬼は" + ChatColor.RED + p.getDisplayName() + ChatColor.WHITE  + "です");
            }else{
                player.sendMessage(p.getDisplayName());
            }
        }
        /*for (Player p : world.getPlayers()){
            player.sendMessage(p.getUniqueId().toString());
        }*/
        if (player.getWorld().getPlayers().size() == 1) {
            Location location = new Location(player.getWorld(),-551,4,-120);
            player.teleport(location);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.RED + "鬼" + ChatColor.WHITE + "です", "プレイヤーを捕まえましょう", 60, 80, 60);
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000 * 20, 0));
        } else {
            Location location1 = new Location(player.getWorld(),-669,4,-121);
            player.teleport(location1);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.RED + "逃げる人" + ChatColor.WHITE + "です", "鬼から逃げきりましょう", 60, 80, 60);
        }

    }



    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event){
        String worldname = event.getEntity().getWorld().getName();
        if (worldname.equals(this.worldName)){
            event.setCancelled(true);
            return;
        }
    }



    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
        if (event.getPlayer().getWorld().getName().equals(this.worldName)){
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event){
        if (event.getPlayer().getWorld().getName().equals(this.worldName)){
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player){
            World world = e.getEntity().getWorld();
            Player damager = (Player) e.getDamager();
            if(e.getEntity().getWorld().getName().equals(this.worldName)){
                if(e.getEntity() instanceof Player){
                    Player player  = (Player)e.getEntity();
                    if(this.isOni(damager)){
                        if(isEscaper(player)){
                            Location location = new Location(player.getWorld(), 1830, 6, 208);
                           player.teleport(location);
                            boolean alltukamatta = true;
                            for(Player p : e.getEntity().getWorld().getPlayers()){
                                if(isEscaper(p)){
                                    alltukamatta = false;
                                }
                            }
                            for (Player p : e.getEntity().getWorld().getPlayers()) {
                                p.sendTitle("", "「" + player.getName() + "」さんが捕まりました!", 40, 40, 40);
                            }
                            if (alltukamatta){

                                for (Player p :world.getPlayers()){
                                    p.sendMessage("ゲームが終了しました。ロビーに戻ります");
                                    new keidoroCountDownScheduler(this.plugin, p,5).runTaskTimer(this.plugin,0,20);
                                    p.removePotionEffect(PotionEffectType.GLOWING);
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private boolean isOni(Player player) {
        if (player.hasPotionEffect(PotionEffectType.GLOWING)){

            return true;
        }

        return false;
    }

    private boolean isEscaper(Player player) {
        if (!isOni(player)){
            return true;
        }
        return false;
    }

}