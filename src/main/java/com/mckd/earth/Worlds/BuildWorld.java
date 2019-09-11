package com.mckd.earth.Worlds;

import com.mckd.earth.Earth;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
}