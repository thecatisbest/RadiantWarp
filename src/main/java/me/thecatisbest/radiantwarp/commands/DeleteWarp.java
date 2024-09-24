package me.thecatisbest.radiantwarp.commands;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.utils.Settings;
import me.thecatisbest.radiantwarp.utils.Utils;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import me.thecatisbest.radiantwarp.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DeleteWarp implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String perms = "radiantwarp.command.delwarp";

        if (cmd.getName().equalsIgnoreCase("delwarp")) {
            Warp remove = WarpManager.getWarp(args[0]);

            if (remove == null) {
                HashMap<String, String> values = new HashMap<>();
                values.put("info", "");

                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-warp"), values);
                return true;
            }

            if (Settings.deleteWarpRequiresPerms && !sender.hasPermission(perms)) {
                HashMap<String, String> values = new HashMap<>();
                values.put("node", perms);

                Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + sender.getName() + " 缺少了 " + perms + " 權限"));
                Utils.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                return true;
            }

            if(sender instanceof Player) {
                Player player = (Player) sender;

                String permOther = perms + ".others";
                // Settings.deleteWarpRequiresPerms 是 true
                if(Settings.deleteWarpRequiresPerms && !remove.isOwner(player) && !player.hasPermission(permOther)) {
                    HashMap<String, String> values = new HashMap<>();
                    values.put("node", permOther);

                    Bukkit.getServer().getConsoleSender().sendMessage(Utils.color("&c玩家 " + player.getName() + " 缺少了 " + permOther + " 權限"));
                    player.sendMessage(Utils.color("&c你沒有權限代替其他玩家刪除公共傳送點!"));
                    // Utils.sendParsedMessage(sender, Settings.getMessage("error.no-permission"), values);
                    return true;
                }
            }

            HashMap<String, String> values = new HashMap<>();
            values.put("warp", remove.getName());
            Utils.sendParsedMessage(sender, Settings.getMessage("warp.removed"), values);

            WarpManager.removeWarp(remove);
            RadiantWarp.fileManager.saveWarps();

            return true;
        }

        return false;
    }
}
