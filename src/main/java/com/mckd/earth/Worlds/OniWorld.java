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
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OniWorld implements Listener {
    private Earth plugin;
    String worldName = "oni";

    public OniWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private Map<String,String> addUniqueId(Player player) {

        //Map<String, String> uuid = new HashMap<>();

        addUniqueId(player).put("0", "test");
        addUniqueId(player).put("1", "test1");
        addUniqueId(player).put("2", "test2");

        player.sendMessage("map.addUniqueId");

        return addUniqueId(player);
    }

    /*private void test(){
        Map<String,String> uuid = this.Map();

    }*/

    public void random(){
        Random random = new Random();
        int randomValue = random.nextInt(2);
    }



    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(!player.getWorld().getName().equals(this.worldName)) {
            return;
        }
        World world = player.getWorld();
        Block block = e.getClickedBlock();


        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType() == Material.SIGN_POST){

            Sign sign;
            sign = (Sign) block.getState();

            String line = sign.getLine(1);

            if(line.equals("test")) {
                player.sendMessage("あ");
                Map<String,String> UniqueId = this.addUniqueId(player);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"tell nankotsu029 test");
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"gamemode 0 nankotsu029");
                player.sendMessage("い");
                player.sendMessage(UniqueId.get("0"));
                UniqueId.put("3","test3");
                player.sendMessage(UniqueId.get("3"));
                for (Player p : world.getPlayers()) {
                    p.getUniqueId();
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
        player.getWorld().setPVP(true);
        player.setHealth(18.0);
        for (Player p :world.getPlayers()){
            if (isOni(p)){
                player.sendMessage(ChatColor.WHITE + "現在の鬼は" + ChatColor.RED + p.getDisplayName() + ChatColor.WHITE  + "です");
            }else{
                player.sendMessage(p.getDisplayName());
            }
        }

        if (player.getWorld().getPlayers().size() == 1) {
            Location location = new Location(player.getWorld(),1783,4,219);
            player.teleport(location);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.RED + "鬼" + ChatColor.WHITE + "です", "プレイヤーを捕まえましょう", 60, 80, 60);
            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000 * 20, 0));
        } else {
            Location location1 = new Location(player.getWorld(),1715,4,219);
            player.teleport(location1);
            player.sendTitle(ChatColor.WHITE + "あなたは" + ChatColor.RED + "逃げる人" + ChatColor.WHITE + "です", "鬼から逃げきりましょう", 60, 80, 60);
        }

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
    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event){
        String worldname = event.getEntity().getWorld().getName();
        if (worldname.equals(this.worldName)){
            event.setCancelled(true);
            return;
        }
    }
    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            World world = event.getEntity().getWorld();
            Player damager = (Player) event.getDamager();
            if (event.getEntity().getWorld().getName().equals(this.worldName)) {
                //ダメージを受けたエンティティがプレイヤーの場合
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();
                    if (this.isOni(damager) ) {
                        if (isEscaper(player)){
                            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000 * 20, 0));
                            boolean allOni = true;
                            for (Player p : event.getEntity().getWorld().getPlayers()) {
                                if (isEscaper(p)){
                                    allOni = false;
                                }
                            }
                            for (Player p : event.getEntity().getWorld().getPlayers()) {
                                p.sendTitle("鬼が増えました！", "「" + player.getName() + "」さんが鬼になりました", 40, 40, 40);
                            }
                            if (allOni){
                                //終了
                                for (Player p :world.getPlayers()){
                                    p.sendMessage("ゲームが終了しました。ロビーに戻ります");
                                    new OniCountDownScheduler(this.plugin, p,5).runTaskTimer(this.plugin,0,20);
                                    p.removePotionEffect(PotionEffectType.GLOWING);
                                }
                            }
                        }
                        else{
                        }
                    }
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
