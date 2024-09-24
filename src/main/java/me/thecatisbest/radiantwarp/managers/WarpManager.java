package me.thecatisbest.radiantwarp.managers;

import me.thecatisbest.radiantwarp.utils.Settings;
import me.thecatisbest.radiantwarp.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;

public class WarpManager {
    private static final HashMap<String, Warp> warps = new HashMap<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static String getFormattedCreationDate(Warp warp) {
        return warp.getFormattedCreationDate();
    }

    public static ArrayList<String> getFormattedCreationDates() {
        ArrayList<String> formattedDates = new ArrayList<>();

        for (Warp warp : warps.values()) {
            String formattedDate = getFormattedCreationDate(warp);
            formattedDates.add(formattedDate);
        }

        return formattedDates;
    }

    public static void addWarp(String name, Location location, Player owner) {
        addWarp(new Warp(name, location, owner.getUniqueId(), LocalDate.now()));
    }

    public static void addWarp(Warp warp) {
        if (warp != null && !isWarp(warp.getName())) {
            warps.put(warp.getName().toLowerCase(), warp);
        } else if (warp != null && isWarp(warp.getName())) {
            warps.put(warp.getName().toLowerCase(), warp);
        }
    }

    public static Warp getWarp(String name) {
        if (isWarp(name)) {
            return warps.get(name.toLowerCase());
        }

        return null;
    }

    public static void removeWarp(Warp warp) {
        if (isWarp(warp)) {
            warps.remove(warp.getName().toLowerCase());
        }
    }

    public static boolean isWarp(String name) {
        return (warps.containsKey(name.toLowerCase()));
    }

    public static boolean isWarp(Warp warp) {
        return isWarp(warp.getName().toLowerCase());
    }

    public static Warp deserialize(String warp) {
        String args[] = warp.split(",");

        String name = args[0];
        String worldS = args[1];
        String xS = args[2];
        String yS = args[3];
        String zS = args[4];
        String pitchS = args[5];
        String yawS = args[6];
        String ownerUUID = args[7];
        String creationDate = args[8];

        try {
            World world = Bukkit.getWorld(worldS);
            double x = Double.parseDouble(xS);
            double y = Double.parseDouble(yS);
            double z = Double.parseDouble(zS);
            float pitch = Float.parseFloat(pitchS);
            float yaw = Float.parseFloat(yawS);

            Location loc = new Location(world, x, y, z, yaw, pitch);

            // 将 creationDate 字符串转换为 LocalDate
            LocalDate date = LocalDate.parse(creationDate, DATE_FORMATTER);

            return new Warp(name, loc, UUID.fromString(ownerUUID), date);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "Error parsing warp '" + name + "'");
            return null;
        }
    }

    public static ArrayList<String> getWarpNames() {
        ArrayList<String> ret = new ArrayList<>();

        for (Warp warp : warps.values()) {
            ret.add(warp.getName());
        }
        return ret;
    }

    public static ArrayList<Warp> getWarps() {
        ArrayList<Warp> ret = new ArrayList<>();

        for (Map.Entry<String, Warp> entry : warps.entrySet()) {
            ret.add(entry.getValue());
        }

        return ret;
    }

    public static ArrayList<String> getAvailable(CommandSender s) {
        if (!Settings.perWarpPerms || s.hasPermission("radiantwarp.warp.*")) {
            return getWarpNames();
        }

        ArrayList<String> ret = new ArrayList<>();

        for (String warp : getWarpNames()) {
            if (s.hasPermission("radiantwarp.warp." + warp.toLowerCase())) {
                ret.add(warp);
            }
        }

        return ret;
    }

    public static int getPlayerWarpsCount(Player player) {
        UUID playerUUID = player.getUniqueId();
        int count = 0;

        for (Warp warp : warps.values()) {
            if (warp.getOwner().equals(playerUUID)) {
                count++;
            }
        }

        return count;
    }

    public static ArrayList<String> getPlayerWarps(Player player) {
        ArrayList<String> ret = new ArrayList<>();
        UUID playerUUID = player.getUniqueId();

        for (Warp warp : warps.values()) {
            if (warp.getOwner().equals(playerUUID)) {
                ret.add(warp.getName());
            }
        }
        return ret;
    }

    private String getNextAvailableWarpName(String playerName) {
        int count = 1;
        String warpName;
        do {
            warpName = playerName + "_" + count;
            count++;
        } while (WarpManager.isWarp(warpName));
        return warpName;
    }

    public static boolean isWithinLimit(Player player, int warpNumber) {
        // 從大到小檢查，找出玩家的最大許可權限
        for (int i = warpNumber; i > 0; i--) {
            if (player.hasPermission("radiantwarp.limit." + i)) {
                return warpNumber <= i; // 如果 warpNumber 小於等於該上限，則允許創建
            }
        }
        return false;
    }

    public static int getPlayerWarpLimit(Player player) {
        // 从大到小检查，找到玩家的最大权限
        for (int i = 1000; i > 0; i--) {
            if (player.hasPermission("radiantwarp.limit." + i)) {
                return i;
            }
        }
        return 0;
    }
}
