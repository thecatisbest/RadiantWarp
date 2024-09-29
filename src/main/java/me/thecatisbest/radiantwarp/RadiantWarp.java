package me.thecatisbest.radiantwarp;

import lombok.Getter;
import lombok.NonNull;
import me.thecatisbest.radiantwarp.listeners.GUIListener;
import me.thecatisbest.radiantwarp.utils.Settings;
import me.thecatisbest.radiantwarp.commands.*;
import me.thecatisbest.radiantwarp.listeners.PlayerListener;
import me.thecatisbest.radiantwarp.managers.FileManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RadiantWarp extends JavaPlugin {

    @Getter private static RadiantWarp instance;
    public static FileManager fileManager;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        saveDefaultConfig();
        reloadConfig();
        Settings.load(this);
        fileManager = new FileManager();
        fileManager.loadWarps();
        this.adventure = BukkitAudiences.create(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getCommand("delwarp").setExecutor(new DeleteWarp());
        getCommand("radiantwarp").setExecutor(new RadiantWarpCommand());
        getCommand("listwarps").setExecutor(new ListWarps());
        getCommand("setwarp").setExecutor(new SetWarp());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("where").setExecutor(new WhereCommand());

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders().register();
            Bukkit.getLogger().info("PlaceholderAPI found! PAPI placeholders will work!");
        } else {
            Bukkit.getLogger().info("PlaceholderAPI was not Found! PAPI placeholders won't work!");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        fileManager.saveWarps();
    }

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }
}
