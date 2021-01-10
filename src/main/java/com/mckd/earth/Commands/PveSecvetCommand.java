package com.mckd.earth.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class PveSecvetCommand {
    public static boolean command(CommandSender sender, Command command,Player player, String label, String args[]) {
        player.sendMessage("test1");
        if (player.getWorld().getName().equals("pve")) {
            player.sendMessage("test2");
            if (sender instanceof Player) {
                player = (Player) sender;
                ScoreboardManager sbm = Bukkit.getScoreboardManager();
                player.sendMessage("test3");
                Scoreboard sb = sbm.getMainScoreboard();
                Objective obj = ((Scoreboard) sb).getObjective("point");
                player.sendMessage("test4");
                if (obj != null) {
                    player.sendMessage("test5");
                    Score score = obj.getScore(player.getDisplayName());
                    int point = (int) score.getScore();
                    player.sendMessage("test6");
                    score.setScore(point + 2000);
                    player.setScoreboard(sb);
                }
            }
        }

        //}
        return true;
    }
}



