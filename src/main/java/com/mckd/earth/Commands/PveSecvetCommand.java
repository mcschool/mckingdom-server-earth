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
    public  static boolean command(CommandSender sender, Command command, String label, String args[]) {
        Player player = null;
        if (player.getWorld().getName().equals("pve")) {
            if (sender instanceof Player) {
                player = (Player) sender;
                ScoreboardManager sbm = Bukkit.getScoreboardManager();
                Scoreboard sb = sbm.getMainScoreboard();
                Objective obj = ((Scoreboard) sb).getObjective("point");
                if (obj != null) {
                    Score score = obj.getScore(player.getDisplayName());
                    int point = (int) score.getScore();
                    score.setScore(point + 2000);
                    player.setScoreboard(sb);
                }
            }
            return true;
        }
    }
}
