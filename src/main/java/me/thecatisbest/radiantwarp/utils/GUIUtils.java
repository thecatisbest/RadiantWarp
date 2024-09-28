package me.thecatisbest.radiantwarp.utils;

import me.thecatisbest.radiantwarp.RadiantWarp;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class GUIUtils {

    public static ItemStack next(int currentIndex) {
        ItemStack next = new ItemStack(Material.PAPER, 1);
        ItemMeta target_next = next.getItemMeta();
        target_next.setDisplayName(Utils.color("&a下一頁"));
        target_next.setLore(Utils.color(Arrays.asList(
                "",
                "&e查看下一頁",
                "")));
        target_next.getPersistentDataContainer().set(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Index"), PersistentDataType.INTEGER,
                Integer.valueOf(currentIndex + 1));
        next.setItemMeta(target_next);
        return next;
    }

    public static ItemStack previous(int currentIndex) {
        ItemStack previous = new ItemStack(Material.PAPER, 1);
        ItemMeta target_previous = previous.getItemMeta();
        target_previous.setDisplayName(Utils.color("&a上一頁"));
        target_previous.setLore(Utils.color(Arrays.asList(
                "",
                "&e查看上一頁",
                "")));
        target_previous.getPersistentDataContainer().set(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Index"), PersistentDataType.INTEGER,
                Integer.valueOf(currentIndex + 1));
        previous.setItemMeta(target_previous);
        return previous;
    }

    public static int getDestPageIndex(ItemStack stack) {
        if (!stack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Index"), PersistentDataType.INTEGER))
            return -1;
        return stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(RadiantWarp.getInstance(), "RadiantWarp.Index"),
                PersistentDataType.INTEGER).intValue();
    }
}
