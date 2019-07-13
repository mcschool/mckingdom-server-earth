# mckingdom-server-earth
mckingdomの `earth` サーバー

## 開発の前に
### spigotをビルドする
```
$ mkdir spigot
$ cd spigot
$ curl -LO https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
$ java -jar BuildTools.jar --rev 1.12
```
10分くらいかかる


## 開発サーバー起動
```
$ make server
```