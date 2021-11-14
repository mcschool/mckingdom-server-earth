package com.mckd.earth.Worlds;


import com.mckd.earth.Earth;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.*;

public class OniWorld implements Listener {
    private Earth plugin;
    private List<UUID> oni = new ArrayList<UUID>();
    private List<UUID> ko = new ArrayList<UUID>();
    String worldName = "oni";
    UUID firstoni;
    BossBar bossBar;


    public OniWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private BossBar getBar(){
        int oniSize = oni.size();
        int koSize = ko.size();
        BossBar bar = Bukkit.createBossBar("鬼の人数" + ChatColor.RED + oniSize + "人" + " : " + "子の人数" + ChatColor.BLUE + koSize + "人",BarColor.PURPLE, BarStyle.SOLID);
        return bar;
    }



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
            //.sendMessage(String.valueOf(i));
            //player.sendMessage(String.valueOf(uuid));
            UniqueId.put(i, uuid);
            i++;
        }

        /* for(Map.Entry<Integer,UUID> entrySet : UniqueId.entrySet()){
            player.sendMessage(entrySet.getKey() + " = " + entrySet.getValue());
        } */


        int random = new Random().nextInt(UniqueId.size());
        UUID result = UniqueId.get(random);

        //player.sendMessage(String.valueOf(result));

        this.firstoni = result;
        oni.add(result);

        Player playerOni = Bukkit.getPlayer(this.firstoni);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.RED);
        helmet.setItemMeta(helmetMeta);
        playerOni.getEquipment().setHelmet(helmet);

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.RED);
        chestplate.setItemMeta(chestplateMeta);
        playerOni.getEquipment().setChestplate(chestplate);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.RED);
        leggings.setItemMeta(leggingsMeta);
        playerOni.getEquipment().setLeggings(leggings);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.RED);
        boots.setItemMeta(bootsMeta);
        playerOni.getEquipment().setBoots(boots);



    }




    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        if (!e.getPlayer().getWorld().getName().equals(this.worldName)){
            return;
        }
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        World world = player.getWorld();

        Location location = new Location(player.getWorld(),-556,5,-121);
        Location location2 = new Location(player.getWorld(),-640,5,-121);

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType() == Material.SIGN_POST){

            Sign sign;
            sign = (Sign) block.getState();
            String line = sign.getLine(1);

            if (line.equals("Start")) {
                oni.clear();
                this.addUniqueId(player);
                for (Player p : world.getPlayers()){
                    bossBar = this.getBar();
                    bossBar.addPlayer(p);
                    if(p.getUniqueId() == this.firstoni){
                        p.teleport(location);
                        p.sendTitle(ChatColor.RED +"あなたは最初の鬼です","制限時間までに全員捕まえましょう",0,100,0 );
                        p.addPotionEffect(PotionEffectType.SLOW.createEffect(15,10));
                        p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(15,10));
                    } else {
                        ko.add(p.getUniqueId());
                        p.teleport(location2);
                        p.sendTitle(ChatColor.BLUE + "あなたは子です","制限時間まで逃げ切りましょう",0,100,0);
                    }

                }
                player.getWorld().setPVP(true);
            }

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
            if (line.equals("openList")){
                player.sendMessage("あなたのUUID " + player.getUniqueId());
                player.sendMessage("鬼のリスト " + oni);
                player.sendMessage("");
                player.sendMessage("子のリスト " + ko);
            }

        }
    }




    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        if (!event.getPlayer().getWorld().getName().equals(this.worldName)){
            return;
        }
        Player player = event.getPlayer();
        bossBar.removePlayer(player);
        World world = player.getWorld();
        Location location = new Location(player.getWorld(),-489,12,-121);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.getWorld().setPVP(false);
        player.teleport(location);
        player.setFoodLevel(20);
        player.setHealth(20.0);


    }


    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e){
        if (!e.getWhoClicked().getWorld().getName().equals(this.worldName)){
            return;
        }
        Player player = (Player) e.getWhoClicked();
        if (oni.contains(player.getUniqueId())){
            e.setCancelled(true);
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
        if (event.getEntity().getWorld().getName().equals(this.worldName)){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent e){
        if (!e.getEntity().getWorld().getName().equals(this.worldName)){
            return;
        }
        if (e.getEntity() instanceof  Player){
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL){
                e.setCancelled(true);
            } else {
                return;
            }
        }
    }




    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getEntity().getWorld().getName().equals(this.worldName)) {
            Player player = (Player) event.getEntity();
            World world = event.getEntity().getWorld();
            Player damager = (Player) event.getDamager();
            UUID uuid = player.getUniqueId();

            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
            helmetMeta.setColor(Color.RED);
            helmet.setItemMeta(helmetMeta);

            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
            chestplateMeta.setColor(Color.RED);
            chestplate.setItemMeta(chestplateMeta);

            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
            LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
            leggingsMeta.setColor(Color.RED);
            leggings.setItemMeta(leggingsMeta);

            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
            bootsMeta.setColor(Color.RED);
            boots.setItemMeta(bootsMeta);

            //ダメージを受けたエンティティがプレイヤーの場合
            if (event.getEntity() instanceof Player) {
                if (oni.contains(damager.getUniqueId())) {
                    if (!oni.contains(player.getUniqueId())) {
                        oni.add(uuid);
                        ko.remove(uuid);

                        player.getEquipment().setHelmet(helmet);
                        player.getEquipment().setChestplate(chestplate);
                        player.getEquipment().setLeggings(leggings);
                        player.getEquipment().setBoots(boots);
                        bossBar = this.getBar();
                        for (Player p : world.getPlayers()) {
                            p.sendTitle("鬼が増えました！", "「" + player.getName() + "」さんが鬼になりました", 40, 40, 40);
                            bossBar.addPlayer(p);
                        }
                        if (oni.size() == world.getPlayers().size()) {
                            for (Player p : world.getPlayers()) {
                                p.sendMessage("すべてのプレイヤーが捕まったのでゲームが終了します。");
                                bossBar.removePlayer(p);
                                new OniCountDownScheduler(this.plugin, p, 5).runTaskTimer(this.plugin, 0, 20);
                                oni.clear();
                            }
                        }
                    } else {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }


}
