package me.thecatisbest.radiantwarp.listeners;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import me.thecatisbest.radiantwarp.menus.WarpsGUI;
import me.thecatisbest.radiantwarp.menus.WhereGUI;
import me.thecatisbest.radiantwarp.objects.Warp;
import me.thecatisbest.radiantwarp.utils.GUIUtils;
import me.thecatisbest.radiantwarp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) return;

        ItemMeta clickedMeta = clickedItem.getItemMeta();

        WhereGUI whereGUI = WhereGUI.getFromPlayer(player);
        if (whereGUI != null) {
            event.setCancelled(true);

            if (GUIUtils.getDestPageIndex(clickedItem) != -1) {
                int newPageIndex = GUIUtils.getDestPageIndex(clickedItem);
                whereGUI.updatePage(newPageIndex);
                whereGUI.openGUI();
            } else if (clickedItem.getItemMeta() != null && clickedItem.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Where"), PersistentDataType.STRING)) {
                String warpName = clickedItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Where"), PersistentDataType.STRING);
                Warp self = WarpManager.getWarp(warpName);
                Bukkit.getScheduler().runTaskLater(RadiantWarp.getInstance(), () -> Utils.warpSelf(player, self), 5L);
            }
        }

        if (player.hasMetadata("WarpsGUI")) {
            event.setCancelled(true);

            if (GUIUtils.getDestPageIndex(clickedItem) != -1) {
                event.setCancelled(true);
                (new WarpsGUI(clickedItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Index"),
                        PersistentDataType.INTEGER).intValue(), player, RadiantWarp.getInstance())).openGUI();
            } else if (clickedMeta != null && clickedMeta.getPersistentDataContainer().has(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Warps"), PersistentDataType.STRING)) {
                String warpName = clickedMeta.getPersistentDataContainer().get(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Warps"), PersistentDataType.STRING);
                Warp self = WarpManager.getWarp(warpName);
                Bukkit.getScheduler().runTaskLater(RadiantWarp.getInstance(), () -> Utils.warpSelf(player, self), 5L);

                /*
                Bukkit.getLogger().info("warpName: " + warpName + ", self: " + self);
                warpName: WhiteMeowGX_1,
                self: WhiteMeowGX_1,world,-40.8556614675259,68.0,146.42153800080825,68.222374,-68.28758,
                25f38894-76d8-43f0-9dba-4ef94663ae84,
                2024/09/26
                 */

            }
        }
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("WhereGUI")) {
            player.removeMetadata("WhereGUI", RadiantWarp.getInstance());
        }
        if (player.hasMetadata("WarpsGUI")) {
            player.removeMetadata("WarpsGUI", RadiantWarp.getInstance());
        }
    }
}
