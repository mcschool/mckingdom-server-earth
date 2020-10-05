package com.mckd.earth.Worlds.Pve;

import com.mckd.earth.Earth;
import com.mckd.earth.Worlds.SkyWars.SkyWars;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scoreboard.*;

import java.util.*;

public class PveWorld implements Listener {

    private Earth plugin;
    private int waveCount = 1;
    private int enemyCount = 0;


    public PveWorld(Earth plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static boolean inTower(Player player) {
        Location location = player.getLocation();
        if (location.getY() < 70) {
            return true;
        } else {
            return false;
        }
    }

    @EventHandler
    public void enterWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("pve")) {
            player.setGameMode(GameMode.ADVENTURE);
            player.setFoodLevel(20);
            player.setHealth(20.0);
            player.getWorld().setPVP(false);
            player.getInventory().clear();
            Location location = new Location(player.getWorld(), -497, 77, -107);
            player.teleport(location);
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

    private void start(Player player) {
        if (player.getWorld().getName().equals("pve")) {

            player.sendMessage("Mobs Killer");

            // ワールドにいる人数が1人だった場合スケジューラースタート
            int inTowerCount = 0;
            List<Player> players = player.getWorld().getPlayers();
            for (Player p : players) {
                if (this.inTower(p)) {
                    //p.sendMessage("Y:"+p.getLocation().getY());
                    inTowerCount++;
                }
            }

            if (inTowerCount <= 1) {
                World world = player.getWorld();
                world.getBlockAt(-501, 19, -120).setType(Material.FENCE);
                world.getBlockAt(-502, 19, -120).setType(Material.FENCE);
                world.getBlockAt(-498, 27, -118).setType(Material.FENCE);
                world.getBlockAt(-498, 27, -117).setType(Material.FENCE);
                world.getBlockAt(-498, 32, -127).setType(Material.FENCE);
                world.getBlockAt(-497, 32, -127).setType(Material.FENCE);
                this.waveCount = 1;
                Collection<Entity> monsters = world.getEntitiesByClasses(Monster.class);
                for (Entity monster : monsters) {
                    monster.remove();
                }
                Collection<Entity> items = world.getEntitiesByClasses(Item.class);
                for (Entity item : items) {
                    item.remove();
                }
                new PveScheduler(this.plugin, player.getWorld(), this.waveCount).runTaskTimer(this.plugin, 0, 20);
            }

            //Score board
            ScoreboardManager sbm = Bukkit.getScoreboardManager();
            Scoreboard sb = sbm.getMainScoreboard();
            Objective obj = sb.getObjective("point");
            // TODO: ↓でぬるぽが発生
            if (obj == null) {
                obj = sb.registerNewObjective("point", "test");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            obj.setDisplayName(ChatColor.GOLD + "ポイント");
            Score score = obj.getScore(player.getDisplayName());
            score.setScore(0);
            player.setScoreboard(sb);

            ScoreboardManager sbm2 = Bukkit.getScoreboardManager();
            Scoreboard sb2 = sbm2.getMainScoreboard();
            Objective obj2 = ((Scoreboard) sb2).getObjective("point");
            if (obj2 != null) {
                Score score2 = obj2.getScore(player.getDisplayName());
                int point = (int) score2.getScore();
                score2.setScore(point + 100);
                player.setScoreboard(sb2);
            }
        }
    }

    @EventHandler
    public void signClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        // プレーヤーがいるワールドがpveじゃなかったら何も終わり
        if (!p.getWorld().getName().equals("pve")) {
            return;
        }
        Block b = e.getClickedBlock();
        // 右クリックした "かつ" クリックしたブロックが看板だった場合
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && b.getType() == Material.SIGN_POST) {
            // ここでスコアをみてアイテムをあげる
            // あらかじめ利用する変数を用意しておく
            Sign sign;
            sign = (Sign) b.getState();
            ScoreboardManager sbm = Bukkit.getScoreboardManager();
            Scoreboard sb = sbm.getMainScoreboard();
            Objective obj = sb.getObjective("point");
            String line = sign.getLine(1);
            String line2 = sign.getLine(2);
            if (line.equals("GameStart")) {
                Location location = new Location(p.getWorld(), -504, 13, -124);
                p.teleport(location);
                this.start(p);
            }
            if (line.equals("クリア")) {
                this.showRanking(p);
            }
            Score score = obj.getScore(p.getDisplayName());
            int point = score.getScore();
            // 鉄の剣
            if (line.equals("鉄の剣") && line2.equals("-100ポイント")) {
                if (point >= 100) {
                    ItemStack item = new ItemStack(Material.IRON_SWORD);
                    p.getInventory().addItem(item);
                    score.setScore(point - 100);
                } else {
                    p.sendMessage("ポイントが100以上必要です!");
                }
            }
            // 鉄の頭
            if (line.equals("鉄のヘルメット") && line2.equals("-200ポイント")) {
                if (point >= 200) {
                    ItemStack item = new ItemStack(Material.IRON_HELMET);
                    p.getInventory().addItem(item);
                    score.setScore(point - 200);
                } else {
                    p.sendMessage("ポイントが200以上必要です!");
                }
            }
            //鉄の足
            if (line.equals("鉄のブーツ") && line2.equals("-200ポイント")) {
                if (point >= 200) {
                    ItemStack item = new ItemStack(Material.IRON_BOOTS);
                    p.getInventory().addItem(item);
                    score.setScore(point - 200);
                } else {
                    p.sendMessage("ポイントが200以上必要です!");
                }
            }
            //弓
            if (line.equals("弓") && line2.equals("-300ポイント")) {
                if (point >= 300) {
                    ItemStack item = new ItemStack(Material.BOW);
                    p.getInventory().addItem(item);
                    score.setScore(point - 300);
                } else {
                    p.sendMessage("ポイントが300以上必要です!");
                }
            }
            //矢
            if (line.equals("矢") && line2.equals("-100ポイント")) {
                if (point >= 100) {
                    ItemStack item = new ItemStack(Material.ARROW, 5);
                    p.getInventory().addItem(item);
                    score.setScore(point - 100);
                } else {
                    p.sendMessage("ポイントが100以上必要です!");
                }
            }
            //治癒のポーション
            if (line.equals("治癒のポーション") && line2.equals("-300ポイント")) {
                if (point >= 300) {
                    ItemStack potion = new ItemStack(Material.POTION);
                    //ポーションの種類を準備する
                    PotionType potionType = PotionType.INSTANT_HEAL;
                    //ポーションの効果とかを準備する
                    PotionData potionData = new PotionData(potionType, false, true);
                    //ポーションのメタ情報を準備する
                    PotionMeta meta = (PotionMeta) potion.getItemMeta();
                    //準備したポーションのデータをメタ情報にセットする
                    meta.setBasePotionData(potionData);
                    //メタ情報をアイテム:ポーションに適用する
                    potion.setItemMeta(meta);
                    //プレーヤーに渡す
                    p.getInventory().addItem(potion);
                    score.setScore(point - 300);
                } else {
                    p.sendMessage("ポイントが300以上必要です!");
                }
            }
            //鉄のチェストプレート
            if (line.equals("鉄のチェストプレート") && line2.equals("-400ポイント")) {
                if (point >= 400) {
                    ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
                    p.getInventory().addItem(item);
                    score.setScore(point - 400);
                } else {
                    p.sendMessage("ポイントが400以上必要です!");
                }
            }
            //鉄のレギンス
            if (line.equals("鉄のレギンス") && line2.equals("-300ポイント")) {
                if (point >= 300) {
                    ItemStack item = new ItemStack(Material.IRON_LEGGINGS);
                    p.getInventory().addItem(item);
                    score.setScore(point - 300);
                } else {
                    p.sendMessage("ポイントが300以上必要です!");
                }
            }
            //焼き鳥
            if (line.equals("焼き鳥") && line2.equals("-200ポイント")) {
                if (point >= 200) {
                    ItemStack item = new ItemStack(Material.COOKED_CHICKEN, 2);
                    p.getInventory().addItem(item);
                    score.setScore(point - 200);
                } else {
                    p.sendMessage("ポイントが200以上必要です!");
                }
            }
            //ラピスラズリ
            if (line.equals("ラピスラズリ") && line2.equals("-100ポイント")) {
                if (point >= 100) {
                    ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 4);
                    p.getInventory().addItem(item);
                    score.setScore(point - 100);
                } else {
                    p.sendMessage("ポイントが100以上必要です!");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().equals("pve")) {
            if (e.getEntity() instanceof Player) {
                Player player = e.getEntity();
                player.sendMessage("You died!");
                player.setHealth(20.0);
                player.setFoodLevel(10);
                player.getInventory().clear();
                player.setGameMode(GameMode.SPECTATOR);
                player.hidePlayer(this.plugin, player);
                //player.performCommand("mvtp world");
                new PveRespawnScheduler(this.plugin, player).runTaskTimer(this.plugin, 0, 20);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Zombie) { //    if zombie dies
            e.getDrops().clear();
        }
        if (e.getEntity() instanceof Skeleton) { // if Skeleton dies
            e.getDrops().clear();
        }
    }




    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        World world = event.getEntity().getWorld();
        if (world.getName().equals("pve")) {
            Player p = event.getEntity().getKiller();
            ScoreboardManager sbm = Bukkit.getScoreboardManager();
            Scoreboard sb = sbm.getMainScoreboard();
            Objective obj = ((Scoreboard) sb).getObjective("point");
            if (obj != null) {
                Score score = obj.getScore(p.getDisplayName());
                int point = (int) score.getScore();
                score.setScore(point + 100);
                p.setScoreboard(sb);
            }
            if (this.waveCount > 15) this.waveCount = 1;
            List<Entity> entities = world.getEntities();
            int count = 0;
            p.sendMessage("1");
            for (Entity entity : world.getEntities()) {
                p.sendMessage("2");
                if (entity.isDead() == false) {
                    p.sendMessage("3");
                    if (entity instanceof Monster) {
                        p.sendMessage("4");
                        count++;
                        p.sendMessage("5");
                    }
                }
            }
            if (count > 0) {
                if (this.enemyCount != count) {
                    this.sendMessageToPlayers(world, "モンスターは残り" + count + "匹!");
                    this.enemyCount = count;
                }
            } else {
                this.sendMessageToPlayers(world, "全モンスターを倒しました!");
                if (this.waveCount < 15) {
                    this.waveCount++;
                    new PveScheduler(this.plugin, world, this.waveCount).runTaskTimer(this.plugin, 0, 20);
                } else {
                    this.sendMessageToPlayers(world, "ゲームクリア!");
                    this.waveCount = 1;
                    for (Player player : world.getPlayers()) {
                        player.performCommand("mvtp world");
                        Score score = obj.getScore(player.getDisplayName());
                        int point = score.getScore();
                        this.setPoint(player, point);
                        player.sendMessage(String.valueOf(point) + "ポイント持ってクリアしました!!");
                    }
                }
            }
        }
    }



    @EventHandler
    public void noPlayerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("pve") && this.inTower(player)) {
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = e.getClickedBlock();
            if (block.getType() == Material.FENCE) {
                Player p = e.getPlayer();
                ScoreboardManager sbm = Bukkit.getScoreboardManager();
                Scoreboard sb = sbm.getMainScoreboard();
                Objective obj = sb.getObjective("point");
                Score score = obj.getScore(p.getDisplayName());
                int point = score.getScore();
                p.sendMessage("今" + String.valueOf(point) + "ポイント持っています");
                if (point >= 500) {
                    score.setScore(point - 500);
                    /* 鉄のドアが右クリックされた時
                    BlockState state = block.getState();
                    Openable o = (Openable) state.getData();
                    Door door = (Door) state.getData();
                    if (door.isTopHalf()) {
                        Block set = block.getRelative(BlockFace.DOWN, 1);
                        state = set.getState();
                        o = (Openable) state.getData();
                    }

                    o.setOpen(true);
                    state.update();*/
                    BlockState state = block.getState();
                    Location location = e.getClickedBlock().getLocation();
                    World world = e.getPlayer().getWorld();
                    Double locationX = Math.floor(location.getX());
                    Double locationY = Math.floor(location.getY());
                    Double locationZ = Math.floor(location.getZ());
                    Boolean flag2f = false;
                    if (locationX == -502) {
                        if (locationY == 19) {
                            if (locationZ == -120) {
                                location.getWorld().getBlockAt(location).setType(Material.AIR);
                                Location location2 = new Location(location.getWorld(), ++locationX, locationY, locationZ);
                                location2.getWorld().getBlockAt(location2).setType(Material.AIR);
                                flag2f = true;
                            }
                        }
                    }
                    if (locationX == -501) {
                        if (locationY == 19) {
                            if (locationZ == -120) {
                                location.getWorld().getBlockAt(location).setType(Material.AIR);
                                Location location2 = new Location(location.getWorld(), --locationX, locationY, locationZ);
                                location2.getWorld().getBlockAt(location2).setType(Material.AIR);
                                flag2f = true;
                            }
                        }
                    }
                    Boolean flag4f = false;
                    Double locationX2 = Math.floor(location.getX());
                    Double locationY2 = Math.floor(location.getY());
                    Double locationZ2 = Math.floor(location.getZ());
                    if (locationX2 == -498) {
                        if (locationY2 == 27) {
                            if (locationZ2 == -117) {
                                location.getWorld().getBlockAt(location).setType(Material.AIR);
                                Location location2 = new Location(location.getWorld(), locationX2, locationY2, --locationZ2);
                                location2.getWorld().getBlockAt(location2).setType(Material.AIR);
                                flag4f = true;
                            }
                        }
                    }
                    if (locationX2 == -498) {
                        if (locationY2 == 27) {
                            if (locationZ2 == -118) {
                                location.getWorld().getBlockAt(location).setType(Material.AIR);
                                Location location2 = new Location(location.getWorld(), locationX2, locationY2, ++locationZ2);
                                location2.getWorld().getBlockAt(location2).setType(Material.AIR);
                                flag4f = true;
                            }
                        }
                    }
                    Boolean flag5f = false;
                    Double locationX3 = Math.floor(location.getX());
                    Double locationY3 = Math.floor(location.getY());
                    Double locationZ3 = Math.floor(location.getZ());
                    if (locationX3 == -497) {
                        if (locationY3 == 32) {
                            if (locationZ3 == -127) {
                                location.getWorld().getBlockAt(location).setType(Material.AIR);
                                Location location2 = new Location(location.getWorld(), --locationX3, locationY3, locationZ3);
                                location2.getWorld().getBlockAt(location2).setType(Material.AIR);
                                flag5f = true;
                            }
                        }
                    }
                    if (locationX3 == -498) {
                        if (locationY3 == 32) {
                            if (locationZ3 == -127) {
                                location.getWorld().getBlockAt(location).setType(Material.AIR);
                                Location location2 = new Location(location.getWorld(), ++locationX3, locationY3, locationZ3);
                                location2.getWorld().getBlockAt(location2).setType(Material.AIR);
                                flag5f = true;
                            }
                        }
                    }

                    if (flag2f == true) {
                        this.sendMessageToPlayers(world, "2階の扉を開けました");
                    }
                    if (flag4f == true) {
                        this.sendMessageToPlayers(world, "4階の扉を開けました");
                    }
                    if (flag5f == true) {
                        this.sendMessageToPlayers(world, "5階の扉を開けました");
                    }


                    location.getWorld().getBlockAt(location).setType(Material.AIR);

                    /*Openable o = (Openable) state.getData();
                      FENCE fence = (Fence) state.getData();
                    if(fence.isTopHalf()) {
                        Location location = e.getClickedBlock().getLocation();
                        location.setY(location.getY() - 1);
                        location.getWorld().getBlockAt(location).setType(Material.AIR);
                    }else {
                        Location location = e.getClickedBlock().getLocation();
                        location.getWorld().getBlockAt(location).setType(Material.AIR);
                    }*/
                } else {
                    p.sendMessage("後" + String.valueOf(500 - point) + "ポイント必要です！");
                }
            }
        }
    }

    private void sendMessageToPlayers(World world, String msg) {
        for (Player player : world.getPlayers()) {
            if (this.inTower(player)) {
                player.sendMessage(msg);
                player.sendTitle(msg, "", 10, 40, 10);
            }
        }
    }


    /**
     * ポイント登録
     *
     * @param player
     * @param newPoint
     */

    private void setPoint(Player player, int newPoint) {
        Integer oldPoint = this.getPoint(player);
        if (newPoint > oldPoint) {
            FileConfiguration config = plugin.getConfig();
            config.set("pve." + player.getUniqueId().toString() + ".point", newPoint);
            config.set("pve." + player.getUniqueId().toString() + ".name", player.getDisplayName());
        }
        plugin.saveConfig();
    }

    /**
     * ランキング表示
     *
     * @param player
     */
    private void showRanking(Player player) {
        TreeMap<String, Integer> ranking = this.getRanking(player);
        int i = 1;
        player.sendMessage("PVE ランキング");
        for (Map.Entry<String, Integer> rank : ranking.entrySet()) {
            if (i <= 10) {
                player.sendMessage(i + "位: " + rank.getKey() + " -> " + rank.getValue());
                i++;
            }
        }
    }




    private Integer getPoint(Player player) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("pve");
        if (section == null) {
            return 0;
        }
        Integer point = 0;
        for (String key : section.getKeys(false)) {
            if (key.equals(player.getUniqueId().toString())) {
                point = config.getInt("pve." + key + ".point");

            }
        }
        return point;

    }

    private TreeMap<String, Integer> getRanking(Player player) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("pve");
        if (section == null) {
            return null;
        }
        HashMap<String, Integer> ranking = new HashMap<>();
        ValueComparator bvc = new ValueComparator(ranking);
        TreeMap<String,Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        for (String key : section.getKeys(false)) {
            String name = config.getString("pve." + key + ".name");
            Integer point = config.getInt("pve." + key + ".point");
            ranking.put(name, point);
        }
        sorted_map.putAll(ranking);

        return sorted_map;
    }

    private  HashMap<String,Integer> sort(HashMap<String,Integer> ranking) {
        List<Map.Entry<String, Integer>> list_entries = new ArrayList<Map.Entry<String, Integer>>(ranking.entrySet());

        Collections.sort(list_entries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2)
            {
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });

        HashMap<String, Integer> newRanking = new HashMap<>();
        for(Map.Entry<String, Integer> entry: list_entries){
            newRanking.put(entry.getKey(), entry.getValue());
        }
        return newRanking;
    }




}

class ValueComparator implements Comparator<String> {
    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}