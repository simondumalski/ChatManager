package me.simondumalski.chatmanager.menus;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.enums.Log;
import me.simondumalski.chatmanager.enums.Message;
import me.simondumalski.chatmanager.utils.ConsoleLogger;
import me.simondumalski.chatmanager.utils.ItemBuilder;
import me.simondumalski.chatmanager.utils.Menu;
import me.simondumalski.chatmanager.utils.MessageManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ColorSelectionMenu extends Menu {

    /**
     * Constructor for the ColorSelectionMenu
     * @param plugin Instance of the main plugin class
     */
    public ColorSelectionMenu(Main plugin) {
        super(plugin);
    }

    @Override
    public void open(Player player) {

        //Create the inventory
        Inventory inventory = plugin.getServer().createInventory(null, 27, "Color Selection");

        //Create the ChatColor item
        ConfigurationSection chatColorSection = plugin.getConfig().getConfigurationSection("menus.color-selection.chat-color");
        ItemStack chatColorItem = ItemBuilder.buildItem(null, chatColorSection);

        //Check that the ChatColor item was created successfully
        if (chatColorItem == null) {
            MessageManager.messagePlayer(player, Message.INVALID_MENU_ITEM, new String[]{"ColorSelection"});
            ConsoleLogger.logToConsole(Log.INVALID_MENU_ITEM, new String[]{"ChatColor", "ColorSelection"});
            return;
        }

        //Add it to the inventory
        inventory.setItem(12, chatColorItem);

        //Create the NameColor item
        ConfigurationSection nameColorSection = plugin.getConfig().getConfigurationSection("menus.color-selection.name-color");
        ItemStack nameColorItem = ItemBuilder.buildItem(null, nameColorSection);

        //Check that the NameColor item was created successfully
        if (nameColorItem == null) {
            MessageManager.messagePlayer(player, Message.INVALID_MENU_ITEM, new String[]{"ColorSelection"});
            ConsoleLogger.logToConsole(Log.INVALID_MENU_ITEM, new String[]{"NameColor", "ColorSelection"});
            return;
        }

        //Add it to the inventory
        inventory.setItem(14, nameColorItem);

        //Open the inventory for the player
        player.openInventory(inventory);
    }

    @Override
    public void perform(Player player, int rawSlot) {

    }

}
