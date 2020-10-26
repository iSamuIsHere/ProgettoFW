package com.gmail.samueler53.progettofw;

import com.gmail.samueler53.progettofw.command.EasterEggCommands;
import com.gmail.samueler53.progettofw.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class ProgettoFW extends JavaPlugin {

    private Data data;
    private Config config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        Objects.requireNonNull(getCommand("easter")).setExecutor(new EasterEggCommands(this));
        try {
            data = Data.loadData(getDataFolder().getPath() + "/Saved.data");
            config = new Config("Config.yml", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Data getData() {
        return data;
    }

    public Config getPluginConfig() {
        return config;
    }
}
