package com.mckd.earth.Worlds;

import com.google.common.cache.AbstractCache;
import com.google.gson.JsonObject;
import com.mckd.earth.Earth;
import com.mckd.earth.Utils.HttpReq;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.*;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ObjLongConsumer;

public class AthleticWorld implements Listener {

    Earth plugin;
    World world;
    String worldName = "athletic";
    Inventory inv;
    public Map<String, Integer> doneTaskIds = new HashMap<String, Integer>();

    public AthleticWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.world = this.plugin.getServer().getWorld(this.worldName);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equals(this.worldName)) {
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.getWorld().setPVP(false);

            // アイテム渡す
            // ------------------------
            // ステージ選択: 紙
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperMeta = paper.getItemMeta();
            paperMeta.setDisplayName("すべてのステージ");
            paper.setItemMeta(paperMeta);
            player.getInventory().setItem(0, paper);
            // スタート地点に戻る: コンパス
            ItemStack compass = new ItemStack(Material.COMPASS);
            ItemMeta compassMeta = compass.getItemMeta();
            compassMeta.setDisplayName("スタート地点に戻る");
            compass.setItemMeta(compassMeta);
            player.getInventory().setItem(7, compass);
            // ロビーに戻る: ベッド
            ItemStack bed = new ItemStack(Material.RED_BED);
            ItemMeta bedMeta = bed.getItemMeta();
            bedMeta.setDisplayName("ロビーに戻る");
            bed.setItemMeta(bedMeta);
            player.getInventory().setItem(8, bed);
            //トリップワイヤーフックを渡す
            ItemStack itemStack = new ItemStack(Material.TRIPWIRE_HOOK);
            player.getInventory().setItem(4,itemStack);
            //パンを渡す
            ItemStack itemStack1 = new ItemStack(Material.BREAD);
            player.getInventory().setItem(3,itemStack1);

            // this.setInventory(player);
            // this.setConfigration(9999, "", player, player.getLocation());
        }
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event){
        String worldname = event.getEntity().getWorld().getName();
        if (worldname.equals(this.worldName)){
            event.setCancelled(true);
            return;
        }
    }
}

