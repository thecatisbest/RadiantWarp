package me.thecatisbest.radiantwarp;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "radiantwarp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "tcib_cat";
    }

    @Override
    public @NotNull String getVersion() {
        return RadiantWarp.getInstance().getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) {
            return "";
        }
        if (params.equalsIgnoreCase("getwarplimit")) {
            int timeLeft = WarpManager.getPlayerWarpLimit((Player) player);
            return String.valueOf(timeLeft);
        }
        return "";
    }
}
