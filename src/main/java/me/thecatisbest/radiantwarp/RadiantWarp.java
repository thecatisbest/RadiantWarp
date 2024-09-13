package me.thecatisbest.radiantwarp;

import lombok.Getter;
import me.thecatisbest.radiantwarp.Utils.Settings;
import me.thecatisbest.radiantwarp.commands.*;
import me.thecatisbest.radiantwarp.listeners.PlayerListener;
import me.thecatisbest.radiantwarp.managers.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RadiantWarp extends JavaPlugin {

    @Getter private static RadiantWarp instance;
    public static FileManager fileManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        saveDefaultConfig();
        reloadConfig();
        Settings.load(this);
        fileManager = new FileManager();
        fileManager.loadWarps();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("delwarp").setExecutor(new DeleteWarp());
        getCommand("radiantwarp").setExecutor(new RadiantWarpCommand());
        getCommand("listwarps").setExecutor(new ListWarps());
        getCommand("setwarp").setExecutor(new SetWarp());
        getCommand("warp").setExecutor(new WarpCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        fileManager.saveWarps();
    }
}
