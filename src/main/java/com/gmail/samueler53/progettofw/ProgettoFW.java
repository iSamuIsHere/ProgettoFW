package com.gmail.samueler53.progettofw;

import com.gmail.samueler53.progettofw.command.EasterEggCommands;
import com.gmail.samueler53.progettofw.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public final class ProgettoFW extends JavaPlugin {


    private Data data;


    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getCommand("easteregg").setExecutor(new EasterEggCommands(this));
        try {
            data = new Data(Data.loadData("Saved.data")); //load data
            Config config = new Config("Config.yml", this);
            //settate title e subtitle da qua ogni volta?
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
