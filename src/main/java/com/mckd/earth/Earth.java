package com.mckd.earth;

import com.mckd.earth.Commands.EmailCommand;
import com.mckd.earth.Worlds.*;
import com.mckd.earth.Worlds.Athletic.AthleticWorld;
import com.mckd.earth.Worlds.Lobby.LobbyWorld;
import com.mckd.earth.Worlds.Pve.PveWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Earth extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("=== MCK ENV ======================");
        String env = System.getenv("MCK_ENV");
        System.out.println(env);
        System.out.println("=====================================");

        // Plugin startup logic
        new LobbyWorld(this);
        new AthleticWorld(this);
        new PvpWorld(this);
        new PartyWorld(this);
        new BuildWorld(this);
        new PveWorld(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(command.getName().equalsIgnoreCase("email")){
            EmailCommand.command(sender, command,label,args);
        }
        return true;
    }
}
