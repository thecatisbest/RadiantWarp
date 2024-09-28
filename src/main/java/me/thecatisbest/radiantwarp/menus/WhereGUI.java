package me.thecatisbest.radiantwarp.menus;

import lombok.Getter;
import me.thecatisbest.radiantwarp.RadiantWarp;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import me.thecatisbest.radiantwarp.objects.Warp;
import me.thecatisbest.radiantwarp.utils.GUIUtils;
import me.thecatisbest.radiantwarp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class WhereGUI {
    private int page = 0;

    private Inventory gui;

    private RadiantWarp main;

    private Player p;

    private OfflinePlayer target;

    public WhereGUI(int pageNumber, Player p, OfflinePlayer target, RadiantWarp main) {
        this.main = main;
        this.page = pageNumber;
        this.p = p;
        this.target = target;
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
        this.p.setMetadata("WhereGUI", new FixedMetadataValue(main, this.gui));
    }

    private void addItemsToInventory() {
        int slot = 9;
        if (WarpManager.getWarps().size() - this.page * 27 > 27) {
            gui.setItem(8, GUIUtils.next(this.page));
        }

        if (this.page > 0) {
            gui.setItem(0, GUIUtils.previous(this.page));
        }
        for (int i = this.page * 27; i < WarpManager.getPlayerWarps(target).size() &&
                i < (this.page + 1) * 27; i++) {
            ItemStack warp = new ItemStack(Material.PAPER, 1);
            ItemMeta target_warp = warp.getItemMeta();
            Warp warpObject = WarpManager.getPlayerWarps(target).get(i);
            target_warp.getPersistentDataContainer().set(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Where"), PersistentDataType.STRING, warpObject.getName());
            target_warp.setDisplayName(Utils.color("&c/warp ") + warpObject.getName());
            target_warp.setLore(Utils.color(Arrays.asList(
                    "",
                    "&7擁有者: &b" + WarpManager.getWarpOwnerName(warpObject),
                    "&7建立時間: &f" + String.join(", ", warpObject.getFormattedCreationDate()),
                    "",
                    "&7[&a左鍵&7] &f拜訪傳送點")));
            warp.setItemMeta(target_warp);
            this.gui.setItem(slot, warp);
            slot++;
        }
    }
}