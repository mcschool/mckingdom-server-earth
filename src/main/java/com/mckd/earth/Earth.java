package com.mckd.earth;

import com.mckd.earth.Worlds.LobbyWorld;
import org.bukkit.plugin.java.JavaPlugin;

public final class Earth extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new LobbyWorld(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
