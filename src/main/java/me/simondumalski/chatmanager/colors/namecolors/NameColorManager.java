package me.simondumalski.chatmanager.colors.namecolors;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.enums.Log;
import me.simondumalski.chatmanager.utils.ItemBuilder;
import me.simondumalski.chatmanager.utils.ConsoleLogger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NameColorManager {

    private final Main plugin;
    private final List<NameColor> nameColors = new ArrayList<>();

    /**
     * Constructor for the NameColorManager
     * Loads all the NameColors created in the config.yml, as well as the Menu and Voucher items
     * @param plugin Instance of the main plugin class
     */
    public NameColorManager(Main plugin) {
        this.plugin = plugin;
        loadNameColors();
    }

    /**
     * Loads all the NameColors from the config.yml and adds them to the list of available NameColors
     */
    public void loadNameColors() {

        //Create the default NameColor menu item
        ConfigurationSection defaultMenuItemSection = plugin.getConfig().getConfigurationSection("name-colors.default.menu-item");
        ItemStack defaultMenuItem = ItemBuilder.buildItem(defaultMenuItemSection);

        //Check that the menu item was created successfully
        if (defaultMenuItem == null) {
            ConsoleLogger.logToConsole(Log.INVALID_ITEM_SEVERE, new String[]{"menu", "NameColor", "default"});
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        //Create the default NameColor voucher item
        ConfigurationSection defaultVoucherItemSection = plugin.getConfig().getConfigurationSection("name-colors.default.voucher-item");
        ItemStack defaultVoucherItem = ItemBuilder.buildItem(defaultVoucherItemSection);

        //Check that the voucher item was created successfully
        if (defaultVoucherItem == null) {
            ConsoleLogger.logToConsole(Log.INVALID_ITEM_SEVERE, new String[]{"voucher", "NameColor", "default"});
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        //Create the default NameColor and add it to the list of NameColors
        List<ChatColor> defaultColors = new ArrayList<>();
        defaultColors.add(ChatColor.WHITE);
        nameColors.add(new NameColor("default", defaultColors, defaultMenuItem, defaultVoucherItem));

        //Load the NameColors that were created in the config.yml
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("name-colors");

        for (String name : section.getKeys(false)) {

            //Check if the current section is the default one, skipping it if it is
            if (name.equalsIgnoreCase("default")) {
                continue;
            }

            //Get the colors for the NameColor
            List<ChatColor> colors = new ArrayList<>();

            for (String colorString : section.getStringList(name + ".colors")) {

                ChatColor chatColor = ChatColor.getByChar(colorString.charAt(1));

                if (chatColor == null) {
                    continue;
                }

                colors.add(chatColor);

            }

            //Check if the list of ChatColors is empty
            if (colors.isEmpty()) {
                ConsoleLogger.logToConsole(Log.INVALID_COLOR, new String[]{"NameColor", name});
                continue;
            }

            //Build the menu item
            ConfigurationSection menuItemSection = section.getConfigurationSection(name + ".menu-item");
            ItemStack menuItem = ItemBuilder.buildItem(menuItemSection);

            //Check that the menu item was created successfully
            if (menuItem == null) {
                ConsoleLogger.logToConsole(Log.INVALID_ITEM, new String[]{"menu", "NameColor", name});
                continue;
            }

            //Build the voucher item
            ConfigurationSection voucherItemSection = section.getConfigurationSection(name + ".voucher-item");
            ItemStack voucherItem = ItemBuilder.buildItem(voucherItemSection);

            //Check that the voucher item was created successfully
            if (voucherItem == null) {
                ConsoleLogger.logToConsole(Log.INVALID_ITEM, new String[]{"voucher", "NameColor", name});
                continue;
            }

            //Create the NameColor
            NameColor nameColor = new NameColor(name, colors, menuItem, voucherItem);

            //Add the NameColor to the list of NameColors
            nameColors.add(nameColor);

        }

        //Log how many NameColors were loaded
        ConsoleLogger.logToConsole(Log.LOADED_ITEMS, new String[]{Integer.toString(nameColors.size()), "NameColors"});

    }

    /**
     * Returns the NameColor with the matching name
     * @param name Name of the NameColor
     * @return Matching NameColor, null if none was found
     */
    public NameColor findNameColor(String name) {

        for (NameColor nameColor : nameColors) {
            if (nameColor.getName().equalsIgnoreCase(name)) {
                return nameColor;
            }
        }

        return null;
    }

    /**
     * Returns the default NameColor
     * @return Default NameColor
     */
    public NameColor getDefaultNameColor() {
        return nameColors.get(0);
    }

    /**
     * Returns the NameColor at the specified index in the list
     * @param index Index of the NameColor to get
     * @return NameColor at the specified index
     */
    public NameColor getNameColor(int index) {
        return nameColors.get(index);
    }

    /**
     * Returns the list of all NameColors
     * @return List of NameColors
     */
    public List<NameColor> getNameColors() {
        return nameColors;
    }

}
