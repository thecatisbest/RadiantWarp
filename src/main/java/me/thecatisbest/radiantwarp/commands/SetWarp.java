package me.thecatisbest.radiantwarp.commands;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.utils.Settings;
import me.thecatisbest.radiantwarp.utils.Utils;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SetWarp implements CommandExecutor {
    private static final Pattern WARP_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static final Pattern VALID_NUMBER_PATTERN = Pattern.compile("^[1-9][0-9]*$");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendParsedMessage(sender, Settings.getMessage("error.not-player"), new HashMap<>());
            return true;
        }

        Player player = (Player) sender;

        String perms = "radiantwarp.command.setwarp";
        if (Settings.setWarpRequiresPerms && !player.hasPermission(perms)) {
            HashMap<String, String> values = new HashMap<>();
            values.put("node", perms);
            Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + player.getName() + " 缺少了 " + perms + " 權限"));
            Utils.sendParsedMessage(player, Settings.getMessage("error.no-permission"), values);
            return true;
        }

        String warpName;
        if (args.length == 0) {
            warpName = player.getName() + "_1";
        } else {
            warpName = args[0];
        }
        if (!isValidWarpName(player, warpName)) {
            return true;
        }

        if (WarpManager.isWarp(warpName) && !Settings.canOverwrite) {
            Utils.sendParsedMessage(player, Settings.getMessage("error.cannot-overwrite"), new HashMap<>());
            return true;
        }

        WarpManager.addWarp(warpName, player.getLocation(), player);
        RadiantWarp.fileManager.saveWarps();

        HashMap<String, String> values = new HashMap<>();
        values.put("warp", warpName);
        Utils.sendParsedMessage(player, Settings.getMessage("warp.set"), values);
        return true;
    }

    private boolean isValidWarpName(Player player, String warpName) {
        if (!player.hasPermission("radiantwarp.bypassname")) {
            String expectedFormat = player.getName() + "_\\d+";
            if (!warpName.matches(expectedFormat)) {
                player.sendMessage(Utils.color("&c使用參數: /setwarp <名稱>_<數字>"));
                return false;
            }

            // Ensure the number part is valid (no leading zeros and within the limit)
            String numberPart = warpName.substring(warpName.lastIndexOf('_') + 1);
            if (!VALID_NUMBER_PATTERN.matcher(numberPart).matches() || !WarpManager.isWithinLimit(player, Integer.parseInt(numberPart))) {

                player.sendMessage(Utils.color("&c無效的數字! 確保無前導零且在你的許可範圍內。"));
                return false;
            }
        }

        if (!WARP_NAME_PATTERN.matcher(warpName).matches()) {
            Utils.sendParsedMessage(player, Settings.getMessage("error.illegal-char"), new HashMap<>());
            return false;
        }

        String perms = "radiantwarp.command.setwarp.other";
        if (!player.hasPermission("") && !warpName.startsWith(player.getName())) {
            Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + player.getName() + " 缺少了 " + perms + " 權限"));
            player.sendMessage(Utils.color("&c你不能用其他玩家的名稱創建公開傳送點!"));
            return false;
        }

        return true;
    }
}
