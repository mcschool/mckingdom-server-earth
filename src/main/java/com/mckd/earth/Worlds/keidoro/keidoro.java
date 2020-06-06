package com.mckd.earth.Worlds.keidoro;

import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFoodLevel(20);
        player.getWorld().setPVP(true);
        player.setHealth(18.0);
        for (Player p :world.getPlayers()){
            if (isOni(p)){
                player.sendMessage(ChatColor.WHITE + "現在の鬼は" + ChatColor.RED + p.getDisplayName() + ChatColor.WHITE  + "です");
            }else{
                player.sendMessage(p.getDisplayName());
            }
        }

        if (player.getWorld().getPlayers().size() == 1) {
            Location location = new Location(player.getWorld(),1832, 6, 220);
            player.teleport(location);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.RED + "鬼" + ChatColor.WHITE + "です", "プレイヤーを捕まえましょう", 60, 80, 60);
            player.setDisplayName("Oni");
            player.setCustomName("Oni");
        } else {
            Location location1 = new Location(player.getWorld(),1832,6,226);
            player.teleport(location1);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.RED + "逃げる人" + ChatColor.WHITE + "です", "鬼から逃げきりましょう", 60, 80, 60);
            player.setDisplayName("Escaper");
            player.setCustomName("Escaper");
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
                            player.setDisplayName("Caught");
                            player.setCustomName("Caught");
                            Location location = new Location(player.getWorld(), 1830, 6, 208);
                            player.teleport(location);
                            boolean allCaught = true;
                            for(Player p : e.getEntity().getWorld().getPlayers()){
                                if(isEscaper(p)){
                                    allCaught = false;
                                }
                            }
                            for (Player p : e.getEntity().getWorld().getPlayers()) {
                                p.sendTitle("", "「" + player.getName() + "」さんが捕まりました!", 40, 40, 40);
                            }
                            if (allCaught){

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

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(this.worldName)){
            return;
        }
        Block block =  event.getClickedBlock();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (event.getMaterial() == Material.SIGN){
                Sign sign;
                sign = (Sign) block.getState();
                String line = sign.getLine(1);
                if (line.equals("Click here")){
                    if (player.getDisplayName().equals("Caught")){
                        Bukkit.getLogger().info("ログです");
                        player.setDisplayName(null);
                        player.setCustomName(null);
                        player.setDisplayName("Escaper");
                        player.setCustomName("Escaper");
                    }
                }
            }
        }
    }

    private boolean isOni(Player player) {
        if (player.getDisplayName().equals("Oni")){
            return true;
        }

        return false;
    }

    private boolean isEscaper(Player player) {
        if (player.getDisplayName().equals("Escaper")){
            return true;
        }
        return false;
    }

    private boolean isCaught(Player player) {
        if (player.getDisplayName().equals("caught")){
            return true;
        }

        return false;
    }

}