package com.mckd.earth.Worlds;

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
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.Map;

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
            ItemStack bed = new ItemStack(Material.BED);
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

            this.setInventory(player);
            this.setConfigration(9999, "", player, player.getLocation());
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

    public void setInventory(Player player) {
        this.inv = Bukkit.createInventory(null, 54, "すべてのステージ");
        this.inv.setItem(0, setItemStack(Material.EMPTY_MAP, "入門タイムアタック Create by red_skt"));
        this.inv.setItem(1, setItemStack(Material.EMPTY_MAP, "羊毛パラダイス! Create by MarkCreative4"));
        this.inv.setItem(2, setItemStack(Material.EMPTY_MAP, "暗闇の世界 Create by MarkCreative4"));
        this.inv.setItem(3, setItemStack(Material.EMPTY_MAP, "渓谷アスレ Create by MarkCreative2"));
        this.inv.setItem(4, setItemStack(Material.EMPTY_MAP, "わろた Create by TYGRloveZzz_1228"));
        this.inv.setItem(5, setItemStack(Material.EMPTY_MAP, "さ☆ば☆く☆ Create by MarkCreative 4"));
        this.inv.setItem(6, setItemStack(Material.EMPTY_MAP, "forever 2マス Create by MarkCreative4"));
        this.inv.setItem(7, setItemStack(Material.EMPTY_MAP, "forever 3マス Create by MarkCreative4"));
        this.inv.setItem(8, setItemStack(Material.EMPTY_MAP,"forver 4マス Create by KAzuki_I"));
        this.inv.setItem(9,setItemStack(Material.EMPTY_MAP,"forever よける Create by MarkCreative4"));
        this.inv.setItem(10,setItemStack(Material.EMPTY_MAP,"冬アスレ Create by 常連２人と管理者"));
        this.inv.setItem(11,setItemStack(Material.EMPTY_MAP,"THE アスレ Create by MarkCreative2"));
        this.inv.setItem(12,setItemStack(Material.EMPTY_MAP,"水に落ちるな！ Create by MarkCreative4"));
        this.inv.setItem(13,setItemStack(Material.EMPTY_MAP,"難しい500Mアスレ Create by taiyaki23"));
        this.inv.setItem(14,setItemStack(Material.EMPTY_MAP,"上級アスレ Create by Jikkyoreeto"));
        this.inv.setItem(15,setItemStack(Material.EMPTY_MAP,"泳げたい焼きくんアスレ Create by taiyaki23"));
    }

    public ItemStack setItemStack(Material material, String name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * オリジナルのGUIメニューを開く
     * @param player
     */
    public void openGui(Player player) {
        player.openInventory(this.inv);
    }

    /**
     * ステート地点に戻る
     */
    public void teleportStartLocation(Player player) {
        player.teleport(this.world.getSpawnLocation());
    }

    /**
     * ステージ移動
     * @param player
     * @param stageNo
     */
    public void changeStage(Player player, int stageNo) {
        World world = player.getWorld();
        switch(stageNo) {
            //case 0: player.teleport(new Location(World, -271, 72, 444)); break;
            default: break;
        }
    }

    /**
     * ステージクリア
     * @param player
     * @param location
     */
    public void done(Player player,Location location) {
        FileConfiguration config = plugin.getConfig();
        String stageName =  config.getString("athletic-stage-name-" + player.getUniqueId().toString());
        Bukkit.broadcastMessage(ChatColor.AQUA + player.getDisplayName() + "さんが" + ChatColor.GREEN + stageName + "をクリアしました");
        player.sendTitle("Congratulations!", "おめでとう！！", 40, 60, 60);


        //TODO あとでデータを合わせる
        HttpReq req = new HttpReq();
        JsonObject obj = new JsonObject();
        obj.addProperty("player_uuid", player.getUniqueId().toString());
        obj.addProperty("player_id", 4);
        obj.addProperty("athletic_course_id", 1);
        obj.addProperty("athletic_total_time", 10);
        JsonObject response = req.post("/api/game/athletic_completed_players", obj);
        System.out.println(response);



        //TODO バグがあった為一旦停止しています。
        //BukkitTask task = new AthleticDoneScheduler(this.plugin,player,4).runTaskTimer(this.plugin,0,20);
        //int taskId = task.getTaskId();
        //this.doneTaskIds.put(player.getUniqueId().toString(), taskId);

        /*this.teleportStartLocation(player);
        player.teleport(new Location(player.getWorld(),-270,71,447));*/
        /*new AthleticFireworkScheduler(this.plugin,location,3).runTaskTimer(this.plugin,0,20);*/
        player.teleport(new Location(player.getWorld(), -270,71,447));
    }


    public void setConfigration(int stageNo, String stageName, Player player, Location location) {
        FileConfiguration config = plugin.getConfig();
        config.set("athletic-stage-no-" + player.getUniqueId().toString(), stageNo);
        config.set("athletic-stage-name-" + player.getUniqueId().toString(), stageName);
        config.set("athletic-checkpoint-location-" + player.getUniqueId().toString(), location);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(!player.getWorld().getName().equals(this.worldName)){
            return;
        }
        /*
        // クリエイティブの場合終了
        if(player.getGameMode() == GameMode.CREATIVE){
            e.setCancelled(true);
        }
        */
        if(player.getGameMode() == GameMode.SURVIVAL) {
            Inventory inventory = e.getInventory();
            if (inv.getName().equals(inv.getName())) {
                // athletic-uuid
                FileConfiguration config = this.plugin.getConfig();
                if(e.getCurrentItem().getType() == Material.EMPTY_MAP) {
                    //AthleticService athleticService = new AthleticService();
                    int index = e.getRawSlot();
                    // クリックしたindexでステージ移動
                    //入門タイムアタック
                    if (index == 0){
                        Location location = new Location(player.getWorld(),-305,67,302);
                        player.teleport(location);
                        this.setConfigration(0, "入門タイムアタック", player, location);
                        //athleticService.playStage(player, 0);
                    }
                    //羊毛パラダイス
                    if (index == 1){
                        Location location = new Location(player.getWorld(),-85,67,400);
                        player.teleport(location);
                        this.setConfigration(1, "羊毛パラダイス", player, location);
                        //athleticService.playStage(player, 1);
                    }
                    //暗闇世界
                    if (index == 2 ){
                        Location location = new Location(player.getWorld(),-69,66,380);
                        player.teleport(location);
                        this.setConfigration(2, "暗闇世界", player, location);
                        //athleticService.playStage(player, 2);
                    }
                    //渓谷アスレ
                    if (index == 3){
                        Location location = new Location(player.getWorld(),-442,44,510);
                        player.teleport(location);
                        this.setConfigration(3, "渓谷アスレ", player, location);
                        //athleticService.playStage(player, 3);
                    }
                    //わろた
                    if (index == 4){
                        Location location = new Location(player.getWorld(),21,71,375);
                        player.teleport(location);
                        this.setConfigration(4,"わろた",player,location);
                        //athleticService.playStage(player,4);
                    }
                    //さばく
                    if (index == 5){
                        Location location = new Location(player.getWorld(),78,68,377);
                        player.teleport(location);
                        this.setConfigration(5,"さ☆ば☆く",player,location);
                        //athleticService.playStage(player,5);
                    }
                    //forever 2マス
                    if (index == 6){
                        Location location = new Location(player.getWorld(),-232,69,347);
                        player.teleport(location);
                        this.setConfigration(6,"forever 2マス",player,location);
                        //athleticService.playStage(player,6);
                    }

                    //forever 3マス
                    if (index == 7){
                        Location location = new Location(player.getWorld(),-241,69,347);
                        player.teleport(location);
                        this.setConfigration(7,"forever 3マス",player,location);
                        //athleticService.playStage(player,7);
                    }

                    //forever 4マス
                    if (index == 8){
                        Location location = new Location(player.getWorld(),-303,70,347);
                        player.teleport(location);
                        this.setConfigration(8,"forever 4マス",player,location);
                        //athleticService.playStage(player,8);
                    }

                    //forever よける
                    if (index == 9){
                        Location location = new Location(player.getWorld(),-255,67,345);
                        player.teleport(location);
                        this.setConfigration(9,"forever よける",player,location);
                        //athleticService.playStage(player,9);
                    }

                    //冬アスレ
                    if (index == 10){
                        Location location = new Location(player.getWorld(),-228,97,552);
                        player.teleport(location);
                        this.setConfigration(10,"冬アスレ",player,location);
                        //athleticService.playStage(player,10);
                    }

                    //THE アスレ
                    if (index == 11){
                        Location location = new Location(player.getWorld(),-92,67,501);
                        player.teleport(location);
                        this.setConfigration(11,"THE アスレ",player,location);
                        //athleticService.playStage(player,12);
                    }

                    //水に落ちるな！
                    if (index == 12){
                        Location location = new Location(player.getWorld(),-23,72,380);
                        player.teleport(location);
                        this.setConfigration(12,"水に落ちるな!",player,location);
                        //athleticService.playStage(player,13);
                    }

                    //難しい500Mアスレ
                    if (index == 13){
                        Location  location = new Location(player.getWorld(),-253,114,627);
                        player.teleport(location);
                        this.setConfigration(13,"難しい500Mアスレ",player,location);
                        //athleticService.playStage(player,14);
                    }

                    //中級アスレ
                    if (index == 14){
                        Location location = new Location(player.getWorld(),-344,67,255);
                        player.teleport(location);
                        this.setConfigration(14,"中級アスレ",player,location);
                    }

                    //泳げたい焼きくんアスレ
                    if (index == 15){
                        Location location = new Location(player.getWorld(),-28,74,306);
                        player.teleport(location);
                        this.setConfigration(15,"泳げたい焼きくんアスレ",player,location);
                    }
                }
            }
        }
    }

    /**
     * ダメージ無効化
     * @param e
     */
    @EventHandler
    public void onEntityDamege(EntityDamageEvent e){
        String worldname = e.getEntity().getWorld().getName();
        if(worldname.equals(this.worldName)){
            // ダメージを受けたエンティティがプレーヤーじゃなかったらreturn
            if(!(e.getEntity() instanceof Player)){
                return;
            }
            if(e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FALL){
                e.setCancelled(true);
            }
            return;
        }
    }

    /**
     * サバイバルの場合ブロック破壊禁止
     * @param e
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(player.getWorld().getName().equals(this.worldName)){
            if(e.getPlayer().getGameMode() == GameMode.SURVIVAL){
                e.setCancelled(true);
            }
            return;
        }
    }

    /**
     * アイテムを捨てられないようにする
     * @param e
     */
    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if(player.getWorld().getName().equals(this.worldName)) {
            if(e.getPlayer().getGameMode() == GameMode.SURVIVAL){
                e.setCancelled(true);
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
    public void EntityDamageByBlockEvent(EntityDamageByBlockEvent event){
        if (event.getEntity() instanceof Player){
            event.setCancelled(true);
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
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(!player.getWorld().getName().equals(this.worldName)) return;
        // 空中を右クリック
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            // 紙の場合: インベントリ開く
            if(e.getMaterial() == Material.PAPER) {
                openGui(player);
            }
            // ベッドの場合: ロビーに戻る
            if(e.getMaterial() == Material.BED) {
                player.performCommand("mvtp world");
            }
            // コンパスの場合: スタート地点に戻る
            if(e.getMaterial() == Material.COMPASS) {
                teleportStartLocation(player);
            }
            if (e.getMaterial() == Material.TRIPWIRE_HOOK){

                //red_skt
                if (player.getUniqueId().toString().equals("d9b88192-ff25-42ba-ac40-00f15060d9d0")){
                    player.setGameMode(GameMode.CREATIVE);
                    player.closeInventory();
                    return;
                }
                //たいやき
                if (player.getUniqueId().toString().equals("52e0d5e5-7295-4a3d-ae66-35685eec5ab1")){
                    player.setGameMode(GameMode.CREATIVE);
                    player.closeInventory();
                    return;
                }
                //ゆいんくん
                if (player.getUniqueId().toString().equals("d73e4b97-b3a8-4c9c-89dd-9129e0f11a9a")){
                    player.setGameMode(GameMode.CREATIVE);
                    player.closeInventory();
                    return;
                }
                //AKINORIITO
                if (player.getUniqueId().toString().equals("219b15ca-8b2a-41eb-be77-b809f7966298")){
                    player.setGameMode(GameMode.CREATIVE);
                    player.closeInventory();
                    return;
                }
                //_rato_CH
                if (player.getUniqueId().toString().equals("497dda46-8de7-44ed-b1b2-14be3c269ad5")){
                    player.setGameMode(GameMode.CREATIVE);
                    player.closeInventory();
                    return;
                }
                //Jikkyoreeto
                if (player.getUniqueId().toString().equals("fef0665a-1151-4ab3-928c06907af5a153")){
                    player.setGameMode(GameMode.CREATIVE);
                    player.closeInventory();
                    return;
                }

                player.sendMessage("権限を持っていません (;´･Д･)ｽﾏｿｫ････");
            }

            if (e.getMaterial() == Material.BREAD){
                //AKINORIITO
                if (player.getUniqueId().toString().equals("219b15ca-8b2a-41eb-be77-b809f7966298")){
                    player.setGameMode(GameMode.ADVENTURE);
                    player.closeInventory();
                    return;
                }

                if (player.getUniqueId().toString().equals("497dda46-8de7-44ed-b1b2-14be3c269ad5")){
                    player.setGameMode(GameMode.ADVENTURE);
                    player.closeInventory();
                    return;
                }

                //Jikkyoreeto
                if (player.getUniqueId().toString().equals("fef0665a-1151-4ab3-928c06907af5a153")){
                    player.setGameMode(GameMode.CREATIVE);
                    player.closeInventory();
                    return;
                }


            }
            // ???: 現在地をセーブする
        }
        // ブロックを右クリック
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            // エンダーチェスト
            if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                done(player,e.getClickedBlock().getLocation());
                e.setCancelled(true); // TODO: インベントリ開かないようにできた？
            }
        }
    }
}

