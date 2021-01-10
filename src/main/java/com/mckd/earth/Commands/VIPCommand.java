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
            //yurelx↓
            if (player.getWorld().getName().equals("build")) {
                if (player.getUniqueId().toString().equals("de944974-473d-33bd-a6ec-04370d649004")){
                    player.setGameMode(GameMode.CREATIVE);
                }
            }
        }
        return true;
    }
}
