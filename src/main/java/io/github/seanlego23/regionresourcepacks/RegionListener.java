package io.github.seanlego23.regionresourcepacks;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class RegionListener implements Listener {
    private final RegionResourcePacks plugin;

    public RegionListener(RegionResourcePacks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String finalValue = this.getValue(player.getLocation());
        if (finalValue != null && !finalValue.equals("null")) {
            player.setResourcePack(finalValue);
            player.setMetadata("regionresourcepack:name", new FixedMetadataValue(this.plugin, finalValue));
        } else {
            player.setMetadata("regionresourcepack:name", new FixedMetadataValue(this.plugin, "!server!"));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String oldValue = "";
        for (MetadataValue metadataValue1 : player.getMetadata("regionresourcepack:name")) {
            if (metadataValue1.getOwningPlugin() instanceof RegionResourcePacks) {
                oldValue = metadataValue1.asString();
                break;
            }
        }
        String finalValue = this.getValue(event.getTo() == null ? player.getLocation() : event.getTo());
        if (finalValue != null && !finalValue.equals("null") && !finalValue.equals(oldValue)) {
            player.setResourcePack(finalValue);
            player.setMetadata("regionresourcepack:name", new FixedMetadataValue(this.plugin, finalValue));
        } else if ((finalValue == null || finalValue.equals("null")) && !oldValue.equals("!server!")) {
            player.setResourcePack(RegionResourcePacks.serverResourcePackURL);
            player.setMetadata("regionresourcepack:name", new FixedMetadataValue(this.plugin, "!server!"));
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        String oldValue = "";
        for (MetadataValue metadataValue1 : player.getMetadata("regionresourcepack:name")) {
            if (metadataValue1.getOwningPlugin() instanceof RegionResourcePacks) {
                oldValue = metadataValue1.asString();
                break;
            }
        }
        String finalValue = this.getValue(event.getTo() == null ? player.getLocation() : event.getTo());
        if (finalValue != null && !finalValue.equals("null") && !finalValue.equals(oldValue)) {
            player.setResourcePack(finalValue);
            player.setMetadata("regionresourcepack:name", new FixedMetadataValue(this.plugin, finalValue));
        } else if ((finalValue == null || finalValue.equals("null")) && !oldValue.equals("!server!")) {
            player.setResourcePack(RegionResourcePacks.serverResourcePackURL);
            player.setMetadata("regionresourcepack:name", new FixedMetadataValue(this.plugin, "!server!"));
        }
    }

    private String getValue(org.bukkit.Location location) {
        World world = new BukkitWorld(location.getWorld());
        Location loc = new Location(world, location.getX(), location.getY(), location.getZ());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager = container.get(world);
        if (manager != null) {
            ApplicableRegionSet set = manager.getApplicableRegions(loc.toVector().toBlockPoint());
            String finalValue = null;
            Integer finalPriority = null;
            for (ProtectedRegion region : set) {
                String value = region.getFlag(RegionResourcePacks.flag);
                int priority = region.getPriority();
                if (finalPriority == null) {
                    finalPriority = priority;
                    finalValue = value;
                } else if (priority > finalPriority) {
                    finalPriority = priority;
                    finalValue = value;
                }
            }
            if (finalValue == null) {
                ProtectedRegion region = manager.getRegion("__global__");
                if (region != null) {
                    String url = region.getFlag(RegionResourcePacks.flag);
                    if (url != null && !url.equals("null"))
                        finalValue = url;
                } else
                    this.plugin.getLogger().warning("__global__ region could not be found.");
            }
            return finalValue;
        }
        return null;
    }
}
