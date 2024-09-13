package me.thecatisbest.radiantwarp.objects;

import me.thecatisbest.radiantwarp.Utils.Settings;
import me.thecatisbest.radiantwarp.Utils.Utils;
import me.thecatisbest.radiantwarp.managers.CooldownManager;
import org.bukkit.entity.Player;

public class WarpTimer implements Runnable {
    public Player player;
    public Warp warp;
    public boolean triggerCooldown;

    public int id = 0;

    public WarpTimer(Player p, Warp w, boolean triggerCooldown) {
        player = p;
        warp = w;
        this.triggerCooldown = triggerCooldown;
    }

    @Override
    public void run() {
        Utils.stopWarping(player);

        if(triggerCooldown && !Utils.playerBypassesDelays(player)) {
            CooldownManager.setCooldown(player, Settings.cooldown);
        }
        Utils.warp(player, warp);
    }

}
