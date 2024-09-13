package me.thecatisbest.radiantwarp.commands;

import me.thecatisbest.radiantwarp.Utils.Settings;
import me.thecatisbest.radiantwarp.Utils.Utils;
import me.thecatisbest.radiantwarp.managers.CooldownManager;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import me.thecatisbest.radiantwarp.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WarpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            HashMap<String, String> values = new HashMap<>();

            Utils.sendParsedMessage(sender, Settings.getMessage("error.no-warp-given"), values);
            return true;
        }

        Player target;
        String warpName = args[0];

        if (args.length > 1) {
            String perms = "radiantwarp.command.warpother";
            if (Settings.warpOtherRequiresPerms && !sender.hasPermission(perms)) {
                HashMap<String, String> values = new HashMap<>();
                values.put("node", perms);

                Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + sender.getName() + " 缺少了 " + perms + " 權限"));
                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                return true;
            }

            target = Bukkit.getPlayerExact(args[0]);
            warpName = args[1];

            if (!WarpManager.isWarp(warpName)) {
                HashMap<String, String> values = new HashMap<>();
                values.put("warp", warpName);

                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-warp"), values);
                return true;
            }

            if (target == null || !target.isOnline()) {
                HashMap<String, String> values = new HashMap<>();
                values.put("target", args[0]);

                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-player"), values);
                return true;
            }
        } else {
            if (!WarpManager.isWarp(warpName)) {
                HashMap<String, String> values = new HashMap<>();
                values.put("warp", warpName);

                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-warp"), values);
                return true;
            }

            if (!(sender instanceof Player)) {
                HashMap<String, String> values = new HashMap<>();

                Utils.sendParsedMessage(sender, Settings.getMessage("error.not-player"), values);
                return true;
            }

            String perms = "radiantwarp.command.warp";
            if (Settings.warpRequiresPerms && !sender.hasPermission(perms)) {
                HashMap<String, String> values = new HashMap<>();
                values.put("node", perms);

                Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + sender.getName() + " 缺少了 " + perms + " 權限"));
                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                return true;
            }

            if (!WarpManager.getAvailable(sender).contains(warpName)) {
                HashMap<String, String> values = new HashMap<>();
                values.put("node", perms);
                values.put("warp", warpName);

                Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + sender.getName() + " 缺少了 " + perms + " 權限"));
                Utils.sendParsedMessage(sender, Settings.getMessage("warp.no-permission"), values);
                return true;
            }

            String cooldownString = CooldownManager.getCooldownString((Player) sender);
            if(cooldownString != null && !cooldownString.isEmpty()){
                HashMap<String, String> values = new HashMap<>();
                values.put("time", CooldownManager.getCooldownString((Player) sender));

                Utils.sendParsedMessage(sender, Settings.getMessage("warp.cooldown"), values);
                return true;
            }

            target = (Player) sender;
        }

        Warp to = WarpManager.getWarp(warpName);

        if ((!(sender instanceof Player) || (sender) != target)) {
            HashMap<String, String> values = new HashMap<>();
            values.put("target", target.getName());
            values.put("warp", to.getName());

            Utils.sendParsedMessage(sender, Settings.getMessage("warp.other"), values);

            Utils.warpOther(target, to);
        } else {
            CooldownManager.setCooldown(target, Settings.cooldown);
            Utils.warpSelf(target, to);
        }

        return true;
    }
}
