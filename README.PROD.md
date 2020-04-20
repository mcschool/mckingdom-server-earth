## /etc/systemd/sysytem/mckearth.service
```
[Unit]
Description=mckingdom-earth
After=network-online.target

[Service]
ExecStart=/home/smatosan/mckingdom-server-earth/spigot/run.sh
Restart=always
User=smatosan
Group=smatosan
Type=simple

[Install]
WantedBy=multi-user.target
```

### 起動
```
$ sudo systemctl start mckearth
```

### 再起動
```
$ sudo systemctl start mckearth
```

### 停止
```
$ sudo systemctl stop mckearth
```

### 状態
```
$ sudo systemctl status mckearth
```


### bungee起動
```
$ sudo systemctl start mckbungee
```

## d
```
$ sudo apt install maven
$ sudo apt install make
```