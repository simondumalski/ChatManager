package me.simondumalski.chatmanager.colors.titles;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.enums.Log;
import me.simondumalski.chatmanager.utils.ItemBuilder;
import me.simondumalski.chatmanager.utils.ConsoleLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TitleManager {

    private final Main plugin;
    private final List<Title> titles = new ArrayList<>();

    /**
     * Constructor for the TitleManager
     * Loads all the Titles created in the config.yml, as well as the Menu and Voucher Items
     * @param plugin Instance of the main plugin class
     */
    public TitleManager(Main plugin) {
        this.plugin = plugin;
        loadTitles();
    }

    /**
     * Loads all the Titles from the config.yml and adds them to the list of available Titles
     */
    public void loadTitles() {

        //Create the default Title menu item
        ConfigurationSection defaultMenuItemSection = plugin.getConfig().getConfigurationSection("titles.default.menu-item");
        ItemStack defaultMenuItem = ItemBuilder.buildItem(defaultMenuItemSection);

        //Create the default Title voucher item
        ConfigurationSection defaultVoucherItemSection = plugin.getConfig().getConfigurationSection("titles.default.voucher-item");
        ItemStack defaultVoucherItem = ItemBuilder.buildItem(defaultVoucherItemSection);

        //Create the default Title and add it to the list of Titles
        titles.add(new Title("default", "", defaultMenuItem, defaultVoucherItem));

        //Load the Titles that were created in the config.yml
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("titles");

        for (String name : section.getKeys(false)) {

            //Check if the current section is the default one, skipping it if it is
            if (name.equalsIgnoreCase("default")){
                continue;
            }

            //Get the title string for the Title
            String titleString = section.getString(name + ".title");

            if (titleString == null) {
                ConsoleLogger.logToConsole(Log.INVALID_COLOR, new String[]{"Title", name});
                continue;
            }

            //Check if the last character in the title string is a space and add one if it isn't
            if (titleString.charAt(titleString.length() - 2) != ' ') {
                titleString = titleString + " ";
            }

            //Build the Menu item
            ConfigurationSection menuItemSection = section.getConfigurationSection(name + ".menu-item");
            ItemStack menuItem = ItemBuilder.buildItem(menuItemSection);

            //Check that the Menu item was created successfully
            if (menuItem == null) {
                ConsoleLogger.logToConsole(Log.INVALID_ITEM, new String[]{"menu", "Title", name});
                continue;
            }

            //Build the Voucher item
            ConfigurationSection voucherItemSection = section.getConfigurationSection(name + ".voucher-item");
            ItemStack voucherItem = ItemBuilder.buildItem(voucherItemSection);

            //Check that the Voucher item was created successfully
            if (voucherItem == null) {
                ConsoleLogger.logToConsole(Log.INVALID_ITEM, new String[]{"voucher", "Title", name});
                continue;
            }

            //Create the Title
            Title title = new Title(name, titleString, menuItem, voucherItem);

            //Add the Title to the list of Titles
            titles.add(title);

        }

        //Log how many Titles were loaded
        ConsoleLogger.logToConsole(Log.LOADED_ITEMS, new String[]{Integer.toString(titles.size()), "Titles"});

    }

    /**
     * Returns the Title with the matching name
     * @param name Name of the Title
     * @return Matching Title, null if none was found
     */
    public Title findTitle(String name) {

        for (Title title : titles) {
            if (title.getName().equalsIgnoreCase(name)) {
                return title;
            }
        }

        return null;
    }

    /**
     * Returns the default Title
     * @return Default Title
     */
    public Title getDefaultTitle() {
        return titles.get(0);
    }

    /**
     * Returns the Title at the specified index in the list
     * @param index Index of the title to get
     * @return Title at the specified index
     */
    public Title getTitle(int index) {
        return titles.get(index);
    }

    /**
     * Returns the list of all Titles
     * @return List of Titles
     */
    public List<Title> getTitles() {
        return titles;
    }

}
