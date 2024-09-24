package me.thecatisbest.radiantwarp.managers;

import me.thecatisbest.radiantwarp.utils.Utils;
import me.thecatisbest.radiantwarp.objects.Warp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class FileManager {
    private final FileConfiguration warpConfig;
    private final File warpConfigFile;

    public FileManager() {
        warpConfigFile = new File(Utils.getPlugin().getDataFolder(), "warps.yml");
        warpConfig = YamlConfiguration.loadConfiguration(warpConfigFile);
    }

    public void saveWarps() {
        for(String key : warpConfig.getKeys(true)){
            warpConfig.set(key, null);
        }

        for (Warp warp : WarpManager.getWarps()) {
            warpConfig.set("warps." + warp.getName() + ".world", warp.getLocation().getWorld().getName());
            warpConfig.set("warps." + warp.getName() + ".x", warp.getLocation().getX());
            warpConfig.set("warps." + warp.getName() + ".y", warp.getLocation().getY());
            warpConfig.set("warps." + warp.getName() + ".z", warp.getLocation().getZ());

            warpConfig.set("warps." + warp.getName() + ".yaw", warp.getLocation().getYaw());
            warpConfig.set("warps." + warp.getName() + ".pitch", warp.getLocation().getPitch());
            if(warp.getOwner() != null) {
                warpConfig.set("warps." + warp.getName() + ".owner", warp.getOwner().toString());
            }
            warpConfig.set("warps." + warp.getName() + ".date", warp.getFormattedCreationDate());
        }

        try {
            warpConfig.save(warpConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWarps() {

        ArrayList<String> list = Utils.getChildren(warpConfig, "warps");

        for (String name : list) {
            String worldName = warpConfig.getString("warps." + name + ".world");
            double x = warpConfig.getDouble("warps." + name + ".x");
            double y = warpConfig.getDouble("warps." + name + ".y");
            double z = warpConfig.getDouble("warps." + name + ".z");
            float yaw = 0F, pitch = 0F;
            UUID owner = null;
            LocalDate creationDate = null;

            if (warpConfig.contains("warps." + name + ".yaw")) {
                yaw = Float.parseFloat(warpConfig.getString("warps." + name + ".yaw"));
            }

            if (warpConfig.contains("warps." + name + ".pitch")) {
                pitch = Float.parseFloat(warpConfig.getString("warps." + name + ".pitch"));
            }

            if (warpConfig.contains("warps." + name + ".owner")) {
                owner = UUID.fromString(warpConfig.getString("warps." + name + ".owner"));
            }

            // 读取并解析创建日期
            if (warpConfig.contains("warps." + name + ".creationDate")) {
                String creationDateString = warpConfig.getString("warps." + name + ".creationDate");
                creationDate = LocalDate.parse(creationDateString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            }


            Warp warp = new Warp(name, worldName, x, y, z, yaw, pitch, owner, creationDate);
            WarpManager.addWarp(warp);
        }
    }
}
