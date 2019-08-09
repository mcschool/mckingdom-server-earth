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
$ sudo systemctl start mckearth.earth
```

### 再起動
```
$ sudo systemctl start mckearth.earth
```

### 停止
```
$ sudo systemctl stop mckearth.earth
```

### 状態
```
$ sudo systemctl status mckearth.earth
```