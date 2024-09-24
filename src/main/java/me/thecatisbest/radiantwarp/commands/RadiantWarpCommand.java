package me.thecatisbest.radiantwarp.commands;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.utils.Settings;
import me.thecatisbest.radiantwarp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class RadiantWarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        String perms = "radiantwarp.command.reload";

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(perms)) {
                HashMap<String, String> values = new HashMap<>();
                values.put("node", perms);

                Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + sender.getName() + " 缺少了 " + perms + " 權限"));
                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                return true;
            }

            RadiantWarp.fileManager.saveWarps();

            Utils.getPlugin().reloadConfig();
            Settings.loadSettings(Utils.getPlugin());

            HashMap<String, String> values = new HashMap<>();
            values.put("info", "");

            Utils.sendParsedMessage(sender, Settings.getMessage("config.reloaded"), values);
        }

        return true;
    }
}
