package com.mckd.earth.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PveSecvetCommand {
    public  static boolean command(CommandSender sender, Command command, String label, String args[]){
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            player.sendMessage("test");
        }
        return  true;
    }
}
