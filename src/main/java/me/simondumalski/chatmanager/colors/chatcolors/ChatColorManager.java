package me.simondumalski.chatmanager.colors.chatcolors;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.enums.Log;
import me.simondumalski.chatmanager.utils.ItemBuilder;
import me.simondumalski.chatmanager.utils.ConsoleLogger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChatColorManager {

    private final Main plugin;
    private final List<CMChatColor> chatColors = new ArrayList<>();

    /**
     * Constructor for the ChatColorManager
     * @param plugin Instance of the main plugin class
     */
    public ChatColorManager(Main plugin) {
        this.plugin = plugin;
        loadChatColors();
    }

    /**
     * Loads all the ChatColors and adds them to the list of available ChatColors
     */
    public void loadChatColors() {

        //Create the default Menu item
        ConfigurationSection menuItemSection = plugin.getConfig().getConfigurationSection("chat-colors.menu-item");
        ItemStack defaultMenuItem = ItemBuilder.buildItem("WHITE", menuItemSection);

        //Check that the default Menu item was created successfully
        if (defaultMenuItem == null) {
            ConsoleLogger.logToConsole(Log.INVALID_ITEM_SEVERE, new String[]{"menu", "ChatColor", "default"});
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        //Create the default Voucher item
        ConfigurationSection voucherItemSection = plugin.getConfig().getConfigurationSection("chat-colors.voucher-item");
        ItemStack defaultVoucherItem = ItemBuilder.buildItem("WHITE", voucherItemSection);

        //Check that the default Voucher item was created successfully
        if (defaultVoucherItem == null) {
            ConsoleLogger.logToConsole(Log.INVALID_ITEM_SEVERE, new String[]{"voucher", "ChatColor", "default"});
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        //Create the default ChatColor (White) and add it to the list of ChatColors
        chatColors.add(new CMChatColor("default", ChatColor.WHITE, defaultMenuItem, defaultVoucherItem));

        //Create the CMChatColors based on the default Spigot ChatColors
        for (ChatColor spigotChatColor : ChatColor.values()) {

            //If the current Spigot ChatColor is a format code, skip it
            if (spigotChatColor.isFormat()) {
                continue;
            }

            //If the current Spigot ChatColor is the reset code, skip it
            if (spigotChatColor == ChatColor.RESET) {
                continue;
            }

            //If the current Spigot ChatColor is white, skip it because it is the default
            if (spigotChatColor == ChatColor.WHITE) {
                continue;
            }

            //Get the name of the CMChatColor
            String name = spigotChatColor.name();

            //Build the Menu item
            ItemStack menuItem = ItemBuilder.buildItem(name, menuItemSection);

            //Check that the Menu item was created successfully
            if (menuItem == null) {
                ConsoleLogger.logToConsole(Log.INVALID_ITEM, new String[]{"menu", "ChatColor", name});
                continue;
            }

            //Build the Voucher item
            ItemStack voucherItem = ItemBuilder.buildItem(name, voucherItemSection);

            //Check that the Voucher item was created successfully
            if (voucherItem == null) {
                ConsoleLogger.logToConsole(Log.INVALID_ITEM, new String[]{"voucher", "ChatColor", name});
                continue;
            }

            //Create the CMChatColor
            CMChatColor chatColor = new CMChatColor(name, spigotChatColor, menuItem, voucherItem);

            //Add the CMChatColor to the list of ChatColors
            chatColors.add(chatColor);

        }

        //Log how many ChatColors were loaded
        ConsoleLogger.logToConsole(Log.LOADED_ITEMS, new String[]{Integer.toString(chatColors.size()), "ChatColors"});

    }

    /**
     * Returns the ChatColor with the matching name
     * @param name Name of the ChatColor
     * @return Matching ChatColor, null if none was found
     */
    public CMChatColor findChatColor(String name) {

        for (CMChatColor chatColor : chatColors) {
            if (chatColor.getName().equalsIgnoreCase(name)) {
                return chatColor;
            }
        }

        return null;
    }

    /**
     * Returns the default ChatColor (White)
     * @return Default ChatColor
     */
    public CMChatColor getDefaultChatColor() {
        return chatColors.get(0);
    }

    /**
     * Returns the ChatColor at the specified index in the list
     * @param index Index of the ChatColor to get
     * @return ChatColor at the specified index
     */
    public CMChatColor getChatColor(int index) {
        return chatColors.get(index);
    }

    /**
     * Returns the list of all ChatColors
     * @return List of ChatColors
     */
    public List<CMChatColor> getChatColors() {
        return chatColors;
    }

}
