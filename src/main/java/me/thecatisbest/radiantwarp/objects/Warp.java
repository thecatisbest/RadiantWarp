package me.thecatisbest.radiantwarp.objects;

import lombok.Getter;
import lombok.Setter;
import me.thecatisbest.radiantwarp.managers.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Warp {
    @Setter
    @Getter
    private String name = "";
    private String worldName = "";
    private double x = 0, y = 0, z = 0;
    private float pitch = 0F, yaw = 0F;
    @Setter
    @Getter
    private UUID owner = null;
    @Setter
    @Getter
    private LocalDate creationDate;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");


    public Warp(String n, Location l, UUID owner, LocalDate creationDate) {
        setName(n);
        setLocation(l);
        setOwner(owner);
        setCreationDate(creationDate != null ? creationDate : LocalDate.now());
    }

    public Warp (String n, String world, double xx, double yy, double zz, float yyaw, float ppitch, UUID owner, LocalDate creationDate){
        setName(n);
        worldName = world;
        x = xx;
        y = yy;
        z = zz;
        yaw = yyaw;
        pitch = ppitch;
        setOwner(owner);
        setCreationDate(creationDate != null ? creationDate : LocalDate.now());
    }

    public void setLocation(Location l) {
        worldName = l.getWorld().getName();
        x = l.getX();
        y = l.getY();
        z = l.getZ();
        pitch = l.getPitch();
        yaw = l.getYaw();
    }

    public boolean isOwner(Player p) {
        if(owner == null)
            return false;
        return owner.equals(p.getUniqueId());
    }

    public String getFormattedCreationDate() {
        if (creationDate == null) {
            return "未知";
        }
        return creationDate.format(DATE_FORMATTER);
    }


    public Location getLocation() {
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(name + ",");

        String world = getLocation().getWorld().getName();
        String x = getLocation().getX() + "";
        String y = getLocation().getY() + "";
        String z = getLocation().getZ() + "";
        String pitch = getLocation().getPitch() + "";
        String yaw = getLocation().getYaw() + "";
        String ownerUUID = owner.toString();

        sb.append(world).append(",");
        sb.append(x).append(",");
        sb.append(y).append(",");
        sb.append(z).append(",");
        sb.append(pitch).append(",");
        sb.append(yaw).append(",");
        sb.append(ownerUUID).append(",");
        sb.append(getFormattedCreationDate());

        return sb.toString();
    }
}
