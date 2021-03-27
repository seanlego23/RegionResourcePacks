package io.github.seanlego23.regionresourcepacks;

import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.*;
import org.bukkit.entity.Player;

import java.net.URL;

public class URLFlag extends StringFlag {

    public URLFlag(String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public String parseInput(FlagContext context) throws InvalidFlagFormat {
        LocalPlayer localPlayer;
        localPlayer = context.getPlayerSender();
        if (!(localPlayer instanceof BukkitPlayer))
            throw new InvalidFlagFormat("LocalPlayer isn't a BukkitPlayer.");
        BukkitPlayer bukkitPlayer = (BukkitPlayer) localPlayer;
        Player player = bukkitPlayer.getPlayer();
        if (player.hasPermission("regionresourcepacks.change") || player.isOp()) {
            String url = context.getUserInput();
            if (url.equals("null"))
                return "null";
            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("ftp://") &&
                !url.startsWith("file://")) {
                context = context.copyWith(null, "http://" + context.getUserInput(), null);
            }
            try {
                new URL(context.getUserInput()).toURI();
            } catch (Exception e) {
                throw new InvalidFlagFormat("Not a valid url.");
            }

            return super.parseInput(context);
        } else {
            throw new InvalidFlagFormat(player.getName() + " doesn't have permission to change this flag.");
        }
    }

}
