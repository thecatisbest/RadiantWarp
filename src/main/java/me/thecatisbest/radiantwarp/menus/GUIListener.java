package me.thecatisbest.radiantwarp.menus;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import me.thecatisbest.radiantwarp.objects.Warp;
import me.thecatisbest.radiantwarp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (player.hasMetadata("WarpsGUI")) {
            event.setCancelled(true);

            ItemMeta clickedMeta = clickedItem.getItemMeta();
            if (clickedMeta != null && clickedMeta.getPersistentDataContainer().has(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Warps"), PersistentDataType.STRING)) {
                String warpName = clickedMeta.getPersistentDataContainer().get(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Warps"), PersistentDataType.STRING);
                Warp self = WarpManager.getWarp(warpName);
                Utils.warpSelf(player, self);
                Bukkit.getLogger().info("warpName: " + warpName + ", self: " + self);
            }
        }
    }
}
