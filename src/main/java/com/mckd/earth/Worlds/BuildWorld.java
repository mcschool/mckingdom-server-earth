package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class BuildWorld implements Listener {

    private String worldName = "build";

    public BuildWorld(Earth plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        // このプログラムが動いているワールド名が "build" じゃなかったらプログラム終了
        if (!e.getPlayer().getWorld().getName().equals(this.worldName)) return;
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if (!event.getPlayer().getWorld().getName().equals(this.worldName)) return;
        if (player.getUniqueId().toString().equals("0f7c2404-2d4f-33b2-b297-8a03c2712fa1")){
            player.setGameMode(GameMode.CREATIVE);
            return;
        }
    }
}
