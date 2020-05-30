package com.mckd.earth.Worlds.Lobby;

import com.mckd.earth.Earth;
import com.mckd.earth.Utils.HttpReq;
import com.mckd.earth.Worlds.Lobby.LobbyInventory;
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
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import ru.tehkode.libs.com.google.gson.JsonObject;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class LobbyWorld implements Listener{

    public String worldName = "world";
    private Earth plugin;
    public int money = 0;

    public LobbyWorld(Earth plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        if (event.getPlayer().getWorld().equals(this.worldName)) {
            Player player = event.getPlayer();
            Random rand = new Random();
            int rnd = rand.nextInt(5);
            String r = String.valueOf(rnd);
            World world = event.getPlayer().getWorld();
            Location location = event.getPlayer().getLocation();
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());

            // 管理者権限(admin)を持っている場合
            PermissionUser user = PermissionsEx.getUser(event.getPlayer());
            PermissionGroup permissionGroup = PermissionsEx.getPermissionManager().getGroup("admin");
            //管理者以上
            if (user.inGroup(permissionGroup)) {
                Location location1 = new Location(world, location.getX(), location.getY(), location.getZ());
                world.spawnParticle(Particle.DRAGON_BREATH, location1, 3, 1, 1, 1, 0);
                Location location3 = new Location(world, location.getX(), location.getY(), location.getZ());
                world.spawnParticle(Particle.CLOUD, location3, 10, 0.5, 0, 0.5, 0);
                Location location2 = new Location(world, location.getX(), location.getY(), location.getZ());
                world.spawnParticle(Particle.VILLAGER_HAPPY, location2, 3, 1, 1, 1, 0);
            }

            PermissionUser user2 = PermissionsEx.getUser(event.getPlayer());
            PermissionGroup permissionGroup2 = PermissionsEx.getPermissionManager().getGroup("vip");
            //VIP以上
            if (user2.inGroup(permissionGroup2)) {
                Location location2 = new Location(world, location.getX(), location.getY(), location.getZ());
                world.spawnParticle(Particle.HEART, location2, 3, 0.5, 0.5, 0.5, 0);
                Location location3 = new Location(world, location.getX(), location.getY(), location.getZ());
                world.spawnParticle(Particle.FLAME, location3, 3, 1, 1, 1, 0);
            }
        }
    }


    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals(this.worldName)) {
            this.changeWorld(player);
            player.removePotionEffect(PotionEffectType.GLOWING);
            player.setGameMode(GameMode.SURVIVAL);
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.changeWorld(event.getPlayer());
        // Bossbar
        BossBar bossBar = this.plugin.getServer().createBossBar("★★ ようこそ MCKINGDOM へ ★★", BarColor.BLUE, BarStyle.SOLID);
        bossBar.addPlayer(event.getPlayer());
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        /*
        HttpReq req = new HttpReq();
        JsonObject obj = new JsonObject();
        obj.addProperty("uuid", event.getPlayer().getUniqueId().toString());
        obj.addProperty("name", event.getPlayer().getDisplayName());
        JsonObject response = req.post("/api/game/players", obj);
        System.out.println(response);
        /*
        int loginCount = Integer.parseInt(response.get("money").toString());
        System.out.println(loginCount);
        this.money = Integer.parseInt(response.get("money").toString());
         */
    }

    public void changeWorld(Player player) {
        player.performCommand("mvtp world");
        Location location = new Location(Bukkit.getWorld("world"),-106.5,3,-1506.5);
        player.teleport(location);
        // PlayerServiceでプレーヤー情報登録
        // PlayersService playerService = new PlayersService();
        // playerService.login(player);

        // タイトル
        player.sendTitle(
                ChatColor.GREEN + "M" + ChatColor.WHITE+"CKINGDOM",
                "Welcome To MCK", 60,80,60);
        if (player.getWorld().getName().equals(this.worldName)){
            // インベントリを一度空にする
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.setFlying(false);
            player.setGravity(true);
            player.setGameMode(GameMode.SURVIVAL);
            player.getWorld().setPVP(false);
            // ロビーでのプレーヤーの状態変更(空腹度とか)
            // LobbyUtil.initPlayerStatus(player);
            // ゲームメニュー用の "紙" 渡す
            ItemStack itemStack = new ItemStack(Material.PAPER);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("ゲームメニュー");
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
            player.sendMessage(ChatColor.YELLOW + "=====================");
            player.sendMessage("");
            player.sendMessage("★☆ お知らせ ☆★");
            player.sendMessage("McKingdomのウェブサイトができそうです");
            player.sendMessage("https://mc-kingdom.com/");
            player.sendMessage("");
            player.sendMessage(ChatColor.YELLOW + "=====================");
        }
        // this.sidebar(player);
        /*
        HttpReq req= new HttpReq();
        JsonObject obj = new JsonObject();
        obj.addProperty("uuid", player.getUniqueId().toString());
        obj.addProperty("name", player.getDisplayName());
        JsonObject response = req.post("/api/game/players/my_ranking", obj);
        int lobbyLoginCount = Integer.parseInt(response.get("login_count").toString());
        int ranking = Integer.parseInt(response.get("my_ranking").toString());
        int total_player = Integer.parseInt(response.get("total_players").toString());
        player.sendMessage("PlayerName: " + player.getDisplayName() + " LoginCount: " + lobbyLoginCount + " UrRank: " + ranking + "/" + total_player);
        */
     }

    /**
     * インベントリをクリック
     * @param e
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) throws IOException {
        Player player = (Player) e.getWhoClicked();
        if(!player.getWorld().getName().equals(this.worldName)) return;
        // -- ここから: インベントリ内のアイテムをクリックしたら

        // ダイヤのつるはし: サバイバルに行く(サーバー移動)
        if(e.getCurrentItem().getType() == Material.YELLOW_FLOWER) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                try {
                    dos.writeUTF("Connect");
                    //dos.writeUTF("life"); // サーバー名だけいれればOK
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Bukkit.getLogger().info("-- bungee -----");
                }
                player.sendPluginMessage(this.plugin, "BungeeCord", baos.toByteArray());
                // player.sendPluginMessage(this.plugin, "BungeeCord", baos.toByteArray());
            } catch (org.bukkit.plugin.messaging.ChannelNotRegisteredException ex) {
                ex.printStackTrace();
                Bukkit.getLogger().warning(" ERROR - Usage of bungeecord connect effects is not possible. Your server is not having bungeecord support (Bungeecord channel is not registered in your minecraft server)!");
            }
//            baos.close();
//            dos.close();
        }
        // チェスト: 建築ワールドに行く
        if(e.getCurrentItem().getType() == Material.CHEST){
            player.performCommand("mvtp build");
        }
        // ダイヤの剣: PvPに行く
        if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD){
            player.performCommand("mvtp pve");
        }
        // ダイヤのブーツ: アスレに行く
        if(e.getCurrentItem().getType() == Material.DIAMOND_BOOTS){
            player.performCommand("mvtp athletic");
        }

        if (e.getCurrentItem().getType() == Material.DIAMOND){
            player.performCommand("mvtp ty");
        }

        if (e.getCurrentItem().getType() == Material.BOW){
            player.performCommand("mvtp SkyWars2");
        }

        if (e.getCurrentItem().getType() == Material.ENDER_PEARL){
            player.performCommand("mvtp oni");
        }

        if(e.getCurrentItem().getType() == Material.TNT){
            player.performCommand("mvtp minigame");

        }
        // トリップワイヤーフック: クリエになる
        if (e.getCurrentItem().getType() == Material.TRIPWIRE_HOOK){
            //KAzuki_I
            if (player.getUniqueId().toString().equals("54bcb3ae-c98d-43e4-b91e-195ac6ce7a9e")){
                player.setGameMode(GameMode.CREATIVE);
                player.closeInventory();
                return;
            }
            //MarkCreative4
            if (player.getUniqueId().toString().equals("e3c7490a-3d9a-4e7b-bd3e-80cf0a218219")){
                player.setGameMode(GameMode.CREATIVE);
                player.closeInventory();
                return;
            }
            player.sendMessage("権限がありません (;´･Д･)ｽﾏｿｫ････");

        }
        return;
    }


   @EventHandler
    public  void PlayerUnleashEntityEvent(PlayerUnleashEntityEvent e){
        if(e.getEntity().getWorld().getName().equals("world")){
            Player p = e.getPlayer();
            e.setCancelled(true);
        }
    }

    /**
     * エンティティがダメージを受けた時
     * @param e
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().getWorld().getName().equals(this.worldName)) {
            // ダメージを受けたエンティティがプレーヤーじゃなかったらreturn
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            }
            return;
        }
    }


    /**
     * 空腹度が変わった時
     * @param event
     */
    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        // イベント情報からワールド名を取得する
        String worldname = event.getEntity().getWorld().getName();
        // もし現在のワールド名が自分のワールドだった場合
        if (worldname.equals(this.worldName)) {
            // イベントをキャンセルすることで空腹度を減らさないようにできる
            event.setCancelled(true);
            return;
        }
    }

    /**
     * チャット
     * @param event
     */
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

    /**
     * 燃え広がった時？
     * @param event
     */
    @EventHandler
    public void onBlockSpreadEvent(BlockSpreadEvent event){
        Block block = event.getBlock();
        if (block.getWorld().getName().equals(this.worldName)) {
            if (block.getType() == Material.FIRE) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * これなんだ？
     * @param event
     */
    @EventHandler
    public void ExplosionPrimeEvent(ExplosionPrimeEvent event) {
        if (event.getEntity().getWorld().getName().equals(this.worldName)) {
            event.setCancelled(true);
        }
    }

    /**
     * ブロックが設置させた時
     * @param event
     */
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        if (event.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * ブロックが壊された時
     * @param event
     */
    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        // HttpReq req = new HttpReq();

        // Ex: GET Request
        // -----------------
        /*
        String url = "/api/game/me?uuid=" + event.getPlayer().getUniqueId().toString();
        JsonObject response = req.get(url, null);
        System.out.println(response);
        */

        // Ex: Post Request
        // -------------------
        /*
        JsonObject obj = new JsonObject();
        obj.addProperty("uuid", event.getPlayer().getUniqueId().toString());
        obj.addProperty("name", event.getPlayer().getDisplayName());
        JsonObject response = req.post("/api/game/players", obj);
        System.out.println(response);
        int loginCount = Integer.parseInt(response.get("login_count").toString());
        System.out.println(loginCount);
        */

        // Ex: Put Request
        // -------------------
        /*
        JsonObject obj = new JsonObject();
        obj.addProperty("uuid", event.getPlayer().getUniqueId().toString());
        JsonObject response = req.put("/api/health/", obj);
        System.out.println(response);
        */


        // Ex: Delete Request
        // -------------------
        /*
        JsonObject obj = new JsonObject();
        obj.addProperty("uuid", event.getPlayer().getUniqueId().toString());
        JsonObject response = req.put("/api/health/", obj);
        System.out.println(response);
        */

        if (event.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                event.getPlayer().sendMessage("壊せないよ");
                event.setCancelled(true);
            }
        }
    }

    /**
     * 右クリックした場合のイベント
     * @param event
     */
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) throws IOException {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals(this.worldName)) {
            Block block = event.getClickedBlock();
            World world = event.getPlayer().getWorld();
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                /*
                if (block.getType() == Material.SIGN_POST) {
                    Sign sign = (Sign) block.getState();
                    String line = sign.getLine(1);
                    if (line.equals("JUNGLE")) {         teleport(new Location(world, 38, 4, -228), player); }
                    if (line.equals("WAFUU KENCHIKU")) { teleport(new Location(world, -222, 4, -228), player); }
                    if (line.equals("HOME")) {           teleport(new Location(world, -93, 4, -228), player); }
                }
                */
            }
            if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if(event.getMaterial() == Material.LEASH) {
                    event.setCancelled(true);
                }
            }

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){

                if (event.getMaterial() == Material.PAPER) {
                    LobbyInventory lobbyInventory = new LobbyInventory();
                    player.openInventory(lobbyInventory.gameMenu());
                }
                if (event.getMaterial() == Material.DIAMOND_PICKAXE){
                    player.sendMessage("survivalに移動します");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    DataOutputStream dos = new DataOutputStream(baos);
                    dos.writeUTF("Connect");
                    dos.writeUTF("survival"); // サーバー名だけいれればOK
                    player.sendPluginMessage(this.plugin, "BungeeCord", baos.toByteArray());
                    baos.close();
                    dos.close();
                }
                /*
                if (event.getMaterial() == Material.YELLOW_FLOWER){
                    player.sendMessage("ゲームサーバーに移動します");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    DataOutputStream dos = new DataOutputStream(baos);
                    dos.writeUTF("connect");
                    dos.writeUTF("server1");
                    player.sendPluginMessage(this.plugin, "BungeeCord", baos.toByteArray());
                    baos.close();
                    dos.close();
                }
                */
                if (event.getMaterial() == Material.CHEST){
                    player.performCommand("mvtp build");
                }
                if (event.getMaterial() == Material.DIAMOND_SWORD){
                    player.performCommand("mvtp pve");
                }
            }
        }
    }


    /**
     * テレポートさせる
     * @param location
     * @param player
     */
    public void teleport(Location location, Player player) {
        player.teleport(location);
    }

    /**
     * ダメージ無効化
     * @param e
     */
    @EventHandler
    public void onEntityDamege(EntityDamageEvent e){
        if(e.getEntity().getWorld().getName().equals(this.worldName)){
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

    /**
     * サーバーを移動する関数
     * @param player
     * @param serverName
     * @throws IOException
     */
    public void changeServer(Player player, String serverName) throws IOException {
        player.sendMessage(serverName + "に移動します");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF("Connect");
        dos.writeUTF(serverName); // サーバー名だけいれればOK
        player.sendPluginMessage(this.plugin, "BungeeCord", baos.toByteArray());
        baos.close();
        dos.close();
    }

    public void sidebar(Player player) {
        /*
        HttpReq req= new HttpReq();
        JsonObject obj = new JsonObject();
        obj.addProperty("uuid", player.getUniqueId().toString());
        obj.addProperty("name", player.getDisplayName());
        JsonObject response = req.post("/api/game/players/my_ranking", obj);
        int lobbyLoginCount = Integer.parseInt(response.get("login_count").toString());
        int ranking = Integer.parseInt(response.get("my_ranking").toString());
        int total_player = Integer.parseInt(response.get("total_players").toString());
        int money = this.money;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj1 = board.registerNewObjective("a", "b");
        obj1.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj1.setDisplayName(player.getDisplayName());
        // 項目追加
        Score permission = obj1.getScore("Login");
        permission.setScore(lobbyLoginCount);
        player.setScoreboard(board);

        Score login_count = obj1.getScore("Ur Rank");
        login_count.setScore(ranking);
        player.setScoreboard(board);

        Score all_players = obj1.getScore("All Players");
        all_players.setScore(total_player);
        player.setScoreboard(board);

        Score money_count = obj1.getScore("Money");
        money_count.setScore(money);
        player.setScoreboard(board);
        */
    }
}