package com.mckd.earth.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand {
    public static void command(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        World world = null;
        if (sender instanceof Player) {
            player = (Player) sender;

            if (player.getGameMode() == GameMode.CREATIVE) {
                Bukkit.broadcastMessage(player.getName() + " is in Creative!");
            }

            if (player.getGameMode() == GameMode.SURVIVAL) {
                Bukkit.broadcastMessage(player.getName() + " is in Survival!");
            }

            if (player.getGameMode() == GameMode.ADVENTURE) {
                Bukkit.broadcastMessage(player.getName() + " is in Adventure!");
            }

        }
    }

}
