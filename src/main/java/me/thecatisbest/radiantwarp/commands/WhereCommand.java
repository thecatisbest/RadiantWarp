package me.thecatisbest.radiantwarp.commands;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.menus.WhereGUI;
import me.thecatisbest.radiantwarp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhereCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("你不能在控制台裡輸入這個指令");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            // 查看自身的传送点
            new WhereGUI(0, player, player, RadiantWarp.getInstance()).openGUI();
        } else if (args.length == 1) {
            // 查看其他玩家的传送点
            String targetPlayerName = args[0];
            OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(targetPlayerName);
            /*
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                player.sendMessage(Utils.color("&c玩家 " + targetPlayerName + " 不存在或從未加入伺服器"));
                return true;
            }

             */

            new WhereGUI(0, player, targetPlayer, RadiantWarp.getInstance()).openGUI();
        } else {
            player.sendMessage(Utils.color("&c用法: /where [玩家名稱]"));
        }
        return true;
    }
}