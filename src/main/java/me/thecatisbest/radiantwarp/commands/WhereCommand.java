package me.thecatisbest.radiantwarp.commands;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.menus.WarpsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhereCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            new WarpsGUI(0, (Player) sender, RadiantWarp.getInstance()).openGUI();
        }
        return true;
    }
}