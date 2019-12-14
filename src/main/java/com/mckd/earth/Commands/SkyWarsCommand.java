package com.mckd.earth.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyWarsCommand {
    public  static boolean command(CommandSender sender, Command command, String label, String args[]) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            if(player.getWorld().getName().equals("SkyWars")) {
                player.sendMessage("test1");
                player.performCommand("mvtp world");
            }
        }
        return true;
    }
}
