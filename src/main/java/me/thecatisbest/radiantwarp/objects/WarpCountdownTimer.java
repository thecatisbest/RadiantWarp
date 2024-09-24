package me.thecatisbest.radiantwarp.objects;

import me.thecatisbest.radiantwarp.utils.Settings;
import me.thecatisbest.radiantwarp.utils.Utils;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class WarpCountdownTimer extends WarpTimer{
    public int secondsRemaining;

    public WarpCountdownTimer(Player p, Warp w, int seconds, boolean triggerCooldown) {
        super(p, w, triggerCooldown);
        secondsRemaining = seconds;
    }

    @Override
    public void run() {
        secondsRemaining--;

        if(secondsRemaining <= 0) {
            super.run();
            return;
        }

        HashMap<String, String> values = new HashMap<>();
        values.put("time", Integer.toString(secondsRemaining));
        Utils.sendParsedMessage(player, Settings.getMessage("warp.countdown"), values);
    }
}
