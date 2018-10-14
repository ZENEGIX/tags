package ru.zenegix.tags.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class LoneyTags extends JavaPlugin {

    private static BukkitTagManager tagManager;

    @Override
    public void onEnable() {
        tagManager = new BukkitTagManager(this);
    }

    public static BukkitTagManager getTagManager() {
        return tagManager;
    }

}
