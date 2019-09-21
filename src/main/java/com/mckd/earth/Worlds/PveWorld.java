package com.mckd.earth.Worlds;

import com.mckd.earth.Scheduler.PveScheduler;
import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PveWorld implements Listener {

    private Earth plugin;

    public PveWorld(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /*@EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        if (!e.getPlayer().getWorld().getName().equals("pve")) return;
        Player p = e.getPlayer();
        p.setGameMode((GameMode.SURVIVAL));
        p.setFoodLevel(20);
        p.setHealth(20.0);
        p.getWorld().setPVP(false);
        p.getInventory().clear();
    }*/
    @EventHandler
    public  void signClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        if(p.getWorld().getName().equals("pve") &&
           e.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
           b.getType() == Material.SIGN_POST
    ){
            Sign sign = (Sign) b.getState();
            String line = sign.getLine(1);
            if( line.equals("Zombie") ){
                Location b_loc = b.getLocation();
                Location s_loc = new Location(b_loc.getWorld(),b_loc.getX()+5,b_loc.getY(),b_loc.getZ());
                Zombie z = b_loc.getWorld().spawn(s_loc,Zombie.class);
                z.setHealth(100.0);
            }
        }
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
            new PveScheduler(this.plugin,player,10).runTaskTimer(this.plugin, 0, 20);
        }
    }
}
