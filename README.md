# mckingdom-server-earth
mckingdomの `earth` サーバー

## How to use Plugins
### PermissionsEx
```
pex user <username> group add admin
```



## 開発の前に
### spigotをビルドする
```
$ mkdir spigot
$ cd spigot
$ curl -LO https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
$ java -jar BuildTools.jar --rev 1.12.2
```
10分くらいかかる


## 開発サーバー起動
```
$ make server
```

## 本番サーバー
GCP Ubuntu18 LTSでインスタンス作る例

## 01: aptをupdateする
```
$ sudo apt update
$ sudo apt upgrade
```

## 02: Javaをインストール
```
バージョン検索
$ sudo apt search openjdk-\(\.\)\+-jre$
入れる
$ sudo apt install openjdk-11-jre
確認
$ java -version
```

## 03: Spigot用意
### ビルドツールダウンロード
```
$ mkdir spigot && cd spigot
$ curl -LO https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
```

### ビルド
```
$ java -jar BuildTools.jar --rev 1.12.2
```
※10分くらいかかる

### 04: 起動
```
一旦実行する
$ java -jar spigot-1.12.2.jar
EULAに同意してくれ的な事言われるので
$ vi eula.txt
eula=false
↓
eula=true
esc -> :w -> :q

もう一回実行
$ java -jar spigot-1.12.2.jar
起動したら
>stop
で止める
```


## プラグイン
### citizens
https://wiki.citizensnpcs.co/Commands
```
/npc create <name> --type <type>
/npc create PIG --type pig
```
mobTypes
https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html

```
/npc list
/npc select <ID>
/npc look <- こっちみるようになる
```