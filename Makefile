# 開発用サーバーを起動する
server:
	cd spigot && java -jar spigot-1.12.2.jar

# コンパイルしてjarを配置
compile:
	mvn install && cp target/earth-1.0-SNAPSHOT.jar spigot/plugins/earth-1.0-SNAPSHOT.jar