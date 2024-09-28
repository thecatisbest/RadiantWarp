package me.thecatisbest.radiantwarp.commands;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.menus.WarpsGUI;
import me.thecatisbest.radiantwarp.utils.Settings;
import me.thecatisbest.radiantwarp.utils.Utils;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ListWarps implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String perms = "radiantwarp.command.listwarps";

        if (Settings.listWarpsRequiresPerms && !sender.hasPermission(perms)) {
            HashMap<String, String> values = new HashMap<>();
            values.put("node", perms);

            Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + sender.getName() + " 缺少了 " + perms + " 權限"));
            Utils.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
            return true;
        }

        /*
        int page = 1;
        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (Exception e) {
                HashMap<String, String> values = new HashMap<>();
                values.put("command", label);

                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-page"), values);
                return true;
            }
        }

         */

        new WarpsGUI(0, (Player) sender, RadiantWarp.getInstance()).openGUI();

        return true;

        /*

        page = Math.min(page, (((WarpManager.getAvailable(sender).size() - 1) / 8) + 1));
        int pages = ((WarpManager.getAvailable(sender).size() - 1) / 8) + 1;

        HashMap<String, String> values = new HashMap<>();
        values.put("page", page + "");
        values.put("pages", pages + "");

        Utils.sendParsedMessage(sender, Settings.getMessage("warp.list"), values);

        for (int i = ((page - 1) * 8); i < Math.min(WarpManager.getAvailable(sender).size(), ((page - 1) * 8) + 8); i++) {
            sender.sendMessage(ChatColor.WHITE + "" + (i + 1) + ". " + ChatColor.AQUA + WarpManager.getAvailable(sender).get(i));
        }

        return true;
    }
         */
    }
}
