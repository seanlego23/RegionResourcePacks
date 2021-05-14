package io.github.seanlego23.regionresourcepacks;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class RegionResourcePacks extends JavaPlugin {

    public static URLFlag flag;
    public static String serverResourcePackURL;

    public RegionResourcePacks() {

    }

    @Override
    public void onLoad() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        flag = new URLFlag("resource-pack", "null");
        registry.register(flag);
    }

    @Override
    public void onEnable() {
        RegionListener listener = new RegionListener(this);
        getServer().getPluginManager().registerEvents(listener, this);

        getServerResourcePack(this);
    }

    private static void getServerResourcePack(RegionResourcePacks plugin) {
        File serverPropertiesFile = new File(plugin.getServer().getWorldContainer(), "server.properties");

        BufferedReader reader;
        serverResourcePackURL = "";
        try {
            reader = new BufferedReader(new FileReader(serverPropertiesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            plugin.getLogger().warning("Could not read server.properties file.");
            return;
        }

        reader.lines().forEach(line -> {
            if (line.startsWith("resource-pack=") && line.substring(14).trim().length() > 1) {
                serverResourcePackURL = line.substring(14).replace("\\", "");
            }
        });
    }

}
