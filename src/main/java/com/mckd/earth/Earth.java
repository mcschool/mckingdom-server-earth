package com.mckd.earth;

import com.mckd.earth.Commands.EmailCommand;
import com.mckd.earth.Commands.PveSecvetCommand;
import com.mckd.earth.Commands.LobbyCommand;
import com.mckd.earth.Commands.VIPCommand;
import com.mckd.earth.Worlds.TntRun.TntRunWorld;
import com.mckd.earth.Worlds.*;
import com.mckd.earth.Worlds.Athletic.AthleticWorld;
import com.mckd.earth.Worlds.Lobby.LobbyWorld;
import com.mckd.earth.Worlds.Pve.PveWorld;
import com.mckd.earth.Worlds.SkyWars2.SkyWars2;
import com.mckd.earth.Worlds.TntGame.TntGameWorld;
import com.mckd.earth.Worlds.TypingButtle.TypingButtleWorld;
import com.mckd.earth.Worlds.water.water;
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
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Plugin startup logic
        new LobbyWorld(this);
        new AthleticWorld(this);
        new BuildWorld(this);
        new PveWorld(this);
        new TntRunWorld(this);
        new SkyWars2(this);
        new TypingButtleWorld(this);
        new OniWorld(this);
        new TntGameWorld(this);
        new water(this);

        // System.out.println("=== start LobbyWorldScheduler ===");
        // new LobbyWorldScheduler().runTaskTimer(this, 0, 100);
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
        if(command.getName().equalsIgnoreCase("pve_point_add")){
            PveSecvetCommand.command(sender, command,label,args);
        }
        if(command.getName().equalsIgnoreCase("lobby")){
            LobbyCommand.command(sender, command,label,args);
        }
        if(command.getName().equalsIgnoreCase("vip_creative")){
            VIPCommand.command(sender, command,label,args);
        }
        return true;
    }
}
