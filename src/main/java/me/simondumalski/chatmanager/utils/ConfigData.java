package me.simondumalski.chatmanager.utils;

import me.simondumalski.chatmanager.Main;
import org.bukkit.ChatColor;

public class ConfigData {

    private final Main plugin;

    public String ACTIVE;
    public String UNLOCKED;
    public String LOCKED;

    /**
     * Constructor for the ConfigData values
     * @param plugin Instance of the main plugin class
     */
    public ConfigData(Main plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    /**
     * Loads the data from the config.yml
     */
    public void loadConfig() {

        //ACTIVE
        if (plugin.getConfig().getConfigurationSection("menus.status.active") == null) {
            ACTIVE = ChatColor.GREEN + "" + ChatColor.BOLD + "ACTIVE" + ChatColor.RESET;
        } else {
            ACTIVE = plugin.getConfig().getString("menus.status.active");
        }

        //UNLOCKED
        if (plugin.getConfig().getConfigurationSection("menus.status.unlocked") == null) {
            UNLOCKED = ChatColor.GOLD + "" + ChatColor.BOLD + "UNLOCKED" + ChatColor.RESET;
        } else {
            UNLOCKED = plugin.getConfig().getString("menus.status.unlocked");
        }

        //LOCKED
        if (plugin.getConfig().getConfigurationSection("menus.status.locked") == null) {
            LOCKED = ChatColor.RED + "" + ChatColor.BOLD + "LOCKED" + ChatColor.RESET;
        } else {
            LOCKED = plugin.getConfig().getString("menus.status.locked");
        }

    }

}
