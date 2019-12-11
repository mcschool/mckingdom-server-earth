package com.mckd.earth.TntRun;

import com.mckd.earth.Earth;
import org.bukkit.event.Listener;

public class TntRunWorld implements Listener {

    Earth plugin;
    String status = "wait";

    public TntRunWorld(Earth plugin) {
        this.plugin = plugin;
    }


}
