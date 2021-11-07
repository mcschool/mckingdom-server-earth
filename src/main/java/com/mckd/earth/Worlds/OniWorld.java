package com.mckd.earth.Worlds;


import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.*;

public class OniWorld implements Listener {
    private Earth plugin;
    private List<UUID> oni = new ArrayList<UUID>();
    String worldName = "oni";
    UUID firstoni;



    public OniWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /*private List<UUID> oniList(Player player) {

        List<UUID> oni = new ArrayList<UUID>();


        return oni;
    }*/



    private Map<Integer,UUID> UniqueIdMap(Player player) {

        Map<Integer, UUID> UniqueId = new HashMap<>();



        return UniqueId;
    }


    private void addUniqueId(Player player) {
        World world = player.getWorld();

        Map<Integer, UUID> UniqueId = this.UniqueIdMap(player);

        Integer i = 0;
        for (Player p : world.getPlayers()) {
            UUID uuid = p.getPlayer().getUniqueId();
            player.sendMessage(String.valueOf(i));
            player.sendMessage(String.valueOf(uuid));
            UniqueId.put(i, uuid);
            i++;
        }

        for(Map.Entry<Integer,UUID> entrySet : UniqueId.entrySet()){
            player.sendMessage(entrySet.getKey() + " = " + entrySet.getValue());
        }


        int random = new Random().nextInt(UniqueId.size());
        UUID result = UniqueId.get(random);

        player.sendMessage(String.valueOf(result));

        this.firstoni = result;


        //player.sendMessage(String.valueOf(UniqueId.get(i)));

    }

    private void addList(Player player){

        //List<UUID> oni = this.oniList(player);

        UUID uuid = player.getUniqueId();
        //oni.add(uuid);

        if (oni.contains(player.getUniqueId())) {
            player.sendMessage("Listに含まれています");
            player.sendMessage(String.valueOf(oni));
        } else {
            player.sendMessage("Listに含まれていません");
            player.sendMessage(String.valueOf(oni));
        }
    }



    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(!player.getWorld().getName().equals(this.worldName)) {
            return;
        }

        //List<UUID> oni = this.oniList(player);
        Block block = e.getClickedBlock();

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType() == Material.SIGN_POST){

            Sign sign;
            sign = (Sign) block.getState();
            String line = sign.getLine(1);

            if(line.equals("test")) {
                this.addUniqueId(player);

            }
            if (line.equals("oni")){
                if (e.getPlayer().getUniqueId() == firstoni){
                    player.sendMessage("あなたは鬼です");
                    player.sendMessage(String.valueOf(this.firstoni));
                } else {
                    player.sendMessage("あなたは鬼じゃないです");
                    player.sendMessage(String.valueOf(this.firstoni));
                }
            }
            if (line.equals("add")){
                //this.addList(player);

                UUID uuid = player.getUniqueId();
                oni.add(uuid);

                if (oni.contains(player.getUniqueId())) {
                    player.sendMessage("Listに含まれています");
                    player.sendMessage(String.valueOf(oni));
                } else {
                    player.sendMessage("Listに含まれていません");
                    player.sendMessage(String.valueOf(oni));
                }
            }
            if (line.equals("check")){
                if (oni.contains(player.getUniqueId())) {
                    player.sendMessage("Listに含まれています");
                    player.sendMessage(String.valueOf(oni));
                } else {
                    player.sendMessage("Listに含まれていません");
                    player.sendMessage(String.valueOf(oni));
                }

            }

        }
    }




    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (!player.getWorld().getName().equals(this.worldName)) return;
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFoodLevel(20);
        player.getWorld().setPVP(false);
        player.setHealth(20.0);


    }


    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
        if (event.getPlayer().getWorld().getName().equals(this.worldName)){
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event){
        if (event.getPlayer().getWorld().getName().equals(this.worldName)){
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if (player.getWorld().getPlayers().size() == 1){
        }
    }
    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        if (event.getPlayer().getWorld().getName().equals(this.worldName)) {
            //管理者とVIPでチャットするときの名前の文字の色を変える
            // 管理者権限(admin)を持っている場合
            PermissionUser user = PermissionsEx.getUser(event.getPlayer());
            PermissionGroup permissionGroup = PermissionsEx.getPermissionManager().getGroup("admin");
            //管理者以上
            if (user.inGroup(permissionGroup)) {
                event.setMessage(ChatColor.AQUA + event.getMessage());
            }
            PermissionUser user2 = PermissionsEx.getUser(event.getPlayer());
            PermissionGroup permissionGroup2 = PermissionsEx.getPermissionManager().getGroup("vip");
            //管理者以上
            if (user2.inGroup(permissionGroup2)) {
                event.setMessage(ChatColor.GREEN + event.getMessage());
            }
        }
    }

    /*@EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event){
        String worldname = event.getEntity().getWorld().getName();
        if (worldname.equals(this.worldName)){
            event.setCancelled(true);
            return;
        }
    }*/

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity().getWorld().getName().equals(this.worldName)) {
            Player player = (Player) event.getEntity();
            //List<UUID> oni = this.oniList(player);
            World world = event.getEntity().getWorld();
            Player damager = (Player) event.getDamager();
            //ダメージを受けたエンティティがプレイヤーの場合
            if (event.getEntity() instanceof Player) {
                if (oni.contains(event.getDamager().getUniqueId())) {

                    boolean allOni = true;
                    for (Player p : event.getEntity().getWorld().getPlayers()) {
                        if (isEscaper(p)) {
                            allOni = false;
                        }
                    }
                    for (Player p : event.getEntity().getWorld().getPlayers()) {
                        p.sendTitle("鬼が増えました！", "「" + player.getName() + "」さんが鬼になりました", 40, 40, 40);
                    }
                    if (allOni) {
                        //終了
                        for (Player p : world.getPlayers()) {
                            p.sendMessage("ゲームが終了しました。ロビーに戻ります");
                            new OniCountDownScheduler(this.plugin, p, 5).runTaskTimer(this.plugin, 0, 20);
                            p.removePotionEffect(PotionEffectType.GLOWING);
                        }
                    }
                } else {
                }
            }
        }
    }


    //鬼という定義を作る
    private boolean isOni(Player player) {
        if (player.hasPotionEffect(PotionEffectType.GLOWING)){
            /*player.sendMessage(player.getEquipment().getHelmet().getType().toString());
            player.sendMessage(player.getDisplayName() + "鬼だ");*/
            return true;
        }
        /*player.sendMessage(player.getDisplayName() + "人だ");
        player.sendMessage(player.getEquipment().getHelmet().getType().toString());*/
        return false;
    }
    private boolean isEscaper(Player player) {
        if (!isOni(player)){
            return true;
        }
        return false;
    }
    private boolean isFirstJoiner(Player player){
        if (player.getWorld().getPlayers().size() == 1){
            return true;
        }
        return false;
    }
}
