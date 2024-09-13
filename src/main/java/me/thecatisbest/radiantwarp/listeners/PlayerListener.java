package me.thecatisbest.radiantwarp.listeners;

import me.thecatisbest.radiantwarp.Utils.Settings;
import me.thecatisbest.radiantwarp.Utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class PlayerListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            cancelWarps(player);
        }
    }

    @EventHandler
    public void onHurt(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            cancelWarps(player);
        }
    }

    public void cancelWarps(Player player) {
        if (Utils.isWarping(player)) {
            Utils.stopWarping(player);

            HashMap<String, String> values = new HashMap<>();
            Utils.sendParsedMessage(player, Settings.getMessage("warp.cancelled"), values);
        }
    }

}
