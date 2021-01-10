package com.mckd.earth.Commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VIPCommand {
    public  static boolean command(CommandSender sender, Command command, String label, String args[]) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (player.getWorld().getName().equals("build")) {
                if (player.getUniqueId().toString().equals("de944974-473d-33bd-a6ec-04370d649004")){
                    player.setGameMode(GameMode.CREATIVE);
                }
                if (player.getUniqueId().toString().equals("3bbeb6a5-66dc-3233-8e93-8747a907dd9c")){
                    player.setGameMode(GameMode.CREATIVE);
                }
            }
        }
        return true;
    }
}
