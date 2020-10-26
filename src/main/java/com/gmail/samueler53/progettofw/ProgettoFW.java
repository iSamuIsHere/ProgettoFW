package com.gmail.samueler53.progettofw;

import com.gmail.samueler53.progettofw.command.EasterEggCommands;
import com.gmail.samueler53.progettofw.listener.PlayerListener;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;

public final class ProgettoFW extends JavaPlugin {

    public Config config;
    public static ProgettoFW instance;
    private Data data;


    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("easteregg").setExecutor(new EasterEggCommands(this));
        try {
            data = new Data(Data.loadData("Saved.data")); //load data
            config = new Config("Config.yml",this);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public  Data getData() {
        return data;
    }
}
