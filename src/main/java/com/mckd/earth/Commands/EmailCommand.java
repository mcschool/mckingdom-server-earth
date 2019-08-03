package com.mckd.earth.Commands;

import com.google.gson.JsonObject;
import com.mckd.earth.Utils.HttpReq;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EmailCommand {
    public static boolean command(CommandSender sender, Command command, String label, String args[]){
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            System.out.println("=====");
            System.out.println(args[0]);
            System.out.println("=====");
            HttpReq req= new HttpReq();
            JsonObject obj = new JsonObject();
            obj.addProperty("uuid", player.getUniqueId().toString());
            obj.addProperty("email", "aaaaaaaaa@.com");
            System.out.println("BEOFRE API");
            JsonObject result = req.post("/api/game/command/email", obj);
            System.out.println("=====");
            System.out.println(result);
        }
        return true;
    }
}
