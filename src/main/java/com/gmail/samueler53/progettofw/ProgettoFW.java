package com.gmail.samueler53.progettofw;

import com.gmail.samueler53.progettofw.command.EasterEggCommands;
import com.gmail.samueler53.progettofw.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class ProgettoFW extends JavaPlugin {

    public static boolean maledizione = false;
    public Config config;
    public static int riavvio = 0;
    public static ProgettoFW instance;


    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("easteregg").setExecutor(new EasterEggCommands());
        try {
            config = new Config("Config.yml",this);
            riavvio = config.getConfig().getInt("riavvio")+1; //increment riavvio
            maledizione = config.getConfig().getBoolean("maledizione");
            if(riavvio == 8) {
                riavvio = 0; //si resetta per motivi di test
            }
            config.getConfig().set("riavvio",riavvio);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        config.getConfig().set("maledizione",maledizione);
        config.save();
    }
}
