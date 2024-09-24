package me.thecatisbest.radiantwarp.listeners;

import me.thecatisbest.radiantwarp.managers.WarpManager;
import me.thecatisbest.radiantwarp.objects.Warp;
import me.thecatisbest.radiantwarp.utils.Settings;
import me.thecatisbest.radiantwarp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getState() instanceof Sign) {
                Player player = e.getPlayer();

                Sign sign = (Sign) e.getClickedBlock().getState();

                if (match(sign.getLine(0), new String[]{"[warp]"})) {
                    e.setCancelled(true);
                    e.setUseInteractedBlock(Event.Result.DENY);
                    /*
                    String warpN = ChatColor.stripColor(sign.getLine(1));

                    if (!WarpManager.isWarp(warpN)) {
                        sign.setLine(0, ChatColor.RED + "錯誤");
                        sign.setLine(1, "");
                        sign.setLine(2, "該傳送點不存在");
                        sign.setLine(3, "");
                        return;
                    }

                    String perms = "radiantwarp.sign.use";
                    if (Settings.signsReqPerms && !player.hasPermission(perms)) {
                        HashMap<String, String> values = new HashMap<>();
                        values.put("node", perms);

                        Utils.sendParsedMessage(player, Settings.getMessage("error.no-permission"), values);
                        return;
                    }

                    perms = "radiantwarp.warp." + warpN;
                    if (Settings.signsPerWarpPerms && !player.hasPermission(perms)) {
                        HashMap<String, String> values = new HashMap<>();
                        values.put("node", perms);

                        Utils.sendParsedMessage(player, Settings.getMessage("error.no-permission"), values);
                        return;
                    }
                     */

                    String warpN = ChatColor.stripColor(sign.getLine(1));
                    Warp warp = WarpManager.getWarp(warpN);
                    if (WarpManager.isWarp(warpN)) {
                        Utils.warpSign(player, warp);
                        Bukkit.getLogger().info("Done");
                    }
                }
            }
        }
    }


    /*
    @EventHandler
    public void onSignCreate(SignChangeEvent e) {
        Player player = e.getPlayer();

        if (match(e.getLine(0), new String[]{"[warp]"})) {

            if (!player.hasPermission("radiantwarp.sign.create")) {
                e.setLine(0, ChatColor.RED + "錯誤");
                e.setLine(1, "");
                e.setLine(2, "你沒有權限創建傳送點");
                e.setLine(3, "");
                return;
            }

            String warpN = e.getLine(1);

            if (!WarpManager.isWarp(warpN)) {
                e.setLine(0, ChatColor.RED + "錯誤");
                e.setLine(1, "");
                e.setLine(2, "該傳送點不存在");
                e.setLine(3, "");
            } else {
                e.setLine(0, ChatColor.AQUA + "[公共傳送點]");
                e.setLine(1, ChatColor.BLACK + e.getLine(1));
                e.setLine(2, ChatColor.DARK_GRAY + e.getLine(2));
                e.setLine(3, ChatColor.DARK_GRAY + e.getLine(3));

                HashMap<String, String> values = new HashMap<>();
                Utils.sendParsedMessage(player, Settings.getMessage("sign.create"), values);
            }
        }
    }

     */

    public boolean match(String x, String[] split) {
        String xx = ChatColor.stripColor(x);

        for (String y : split) {
            String yy = ChatColor.stripColor(y);

            if (xx.equalsIgnoreCase(yy)) {
                return true;
            }
        }

        return false;
    }

}
