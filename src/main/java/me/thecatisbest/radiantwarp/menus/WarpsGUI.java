package me.thecatisbest.radiantwarp.menus;

import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import me.thecatisbest.radiantwarp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class WarpsGUI {
    private int page = 0;

    private Inventory gui;

    private RadiantWarp main;

    private Player p;

    public WarpsGUI(int pageNumber, Player p, RadiantWarp main) {
        this.main = main;
        this.page = pageNumber;
        this.p = p;
        this.gui = Bukkit.createInventory(null, 36, Utils.color("&b傳送列表 &8頁數 ") + this.page + 1);
        addItemsToInventory();
    }

    public Inventory getGUI() {
        return this.gui;
    }

    public int getIndex() {
        return this.page;
    }

    public void openGUI() {
        this.p.openInventory(this.gui);
        this.p.setMetadata("WarpsGUI", new FixedMetadataValue(main, this.gui));
    }

    private void addItemsToInventory() {
        int slot = 9;
        if (WarpManager.getWarps().size() - this.page * 27 > 27) {
            ItemStack next = new ItemStack(Material.PAPER, 1);
            ItemMeta target_next = next.getItemMeta();
            target_next.setDisplayName(Utils.color("&a下一頁"));
            target_next.setLore(Utils.color(Arrays.asList(
                    "",
                    "&e查看下一頁",
                    "")));
            next.setItemMeta(target_next);
            gui.setItem(8, next);
        }

        if (this.page > 0) {
            ItemStack next = new ItemStack(Material.PAPER, 1);
            ItemMeta target_next = next.getItemMeta();
            target_next.setDisplayName(Utils.color("&a上一頁"));
            target_next.setLore(Utils.color(Arrays.asList(
                    "",
                    "&e查看上一頁",
                    "")));
            next.setItemMeta(target_next);
            gui.setItem(0, next);
        }
        for (int i = this.page * 27; i < WarpManager.getWarps().size() &&
                i < (this.page + 1) * 27; i++) {
            ItemStack warp = new ItemStack(Material.PAPER, 1);
            ItemMeta target_warp = warp.getItemMeta();
            target_warp.getPersistentDataContainer().set(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Warps"), PersistentDataType.STRING, String.join(", ", WarpManager.getWarpNames()));
            target_warp.setDisplayName(Utils.color("&c/warp ") + String.join(", ", WarpManager.getWarpNames()));
            target_warp.setLore(Utils.color(Arrays.asList(
                    "",
                    "&7擁有者: &b<名稱>",
                    "&7建立時間: " + WarpManager.getFormattedCreationDates(),
                    "",
                    "&7[&a左鍵&7] &f拜訪傳送點")));
            warp.setItemMeta(target_warp);
            this.gui.setItem(slot, warp);
            slot++;
        }
    }
}