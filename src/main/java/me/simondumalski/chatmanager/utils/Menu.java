package me.simondumalski.chatmanager.utils;

import me.simondumalski.chatmanager.Main;
import org.bukkit.entity.Player;

public abstract class Menu {

    protected final Main plugin;

    /**
     * Constructor for Menus
     * @param plugin Instance of the main plugin class
     */
    public Menu(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates and opens the menu for the specified player
     * @param player Player to open the menu for
     */
    public abstract void open(Player player);

    /**
     * Performs the logic for the menu on the specified player
     * @param player Player to perform the logic for
     * @param rawSlot The slot that was clicked in the menu
     */
    public abstract void perform(Player player, int rawSlot);

}
