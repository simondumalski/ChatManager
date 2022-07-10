package me.simondumalski.chatmanager.menus;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.colors.namecolors.NameColor;
import me.simondumalski.chatmanager.enums.Message;
import me.simondumalski.chatmanager.utils.Menu;
import me.simondumalski.chatmanager.utils.MessageManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NameColorMenu extends Menu {

    /**
     * Constructor for the NameColorMenu
     * @param plugin Instance of the main plugin class
     */
    public NameColorMenu(Main plugin) {
        super(plugin);
    }

    @Override
    public void open(Player player) {

        //Create the inventory
        Inventory inventory = plugin.getServer().createInventory(null, 54, "Name Colors");

        //Get the player's active NameColor
        NameColor activeNameColor = plugin.getPreferencesManager().getNameColor(player);

        //Get the list of NameColors the player is allowed to use
        List<NameColor> allowedNameColors = plugin.getPreferencesManager().getAllowedNameColors(player);

        //Add each NameColor's menu item to the inventory
        int i = 0;

        for (NameColor nameColor : plugin.getNameColorManager().getNameColors()) {

            //Check if the current index is greater than the inventory size
            if (i > 53) {
                break;
            }

            //Get the menu item
            ItemStack item = nameColor.getMenuItem().clone();

            //Get the menu item meta
            ItemMeta meta = item.getItemMeta();

            //Change the %status% placeholder in the item name based on if the NameColor is active, unlocked, or locked
            if (meta.getDisplayName().contains("%status%")) {

                if (nameColor == activeNameColor) {
                    meta.setDisplayName(meta.getDisplayName().replace("%status%", plugin.getConfigData().ACTIVE));
                } else if (allowedNameColors.contains(nameColor)) {
                    meta.setDisplayName(meta.getDisplayName().replace("%status%", plugin.getConfigData().UNLOCKED));
                } else {
                    meta.setDisplayName(meta.getDisplayName().replace("%status%", plugin.getConfigData().LOCKED));
                }

                //Set the newly changed item meta
                item.setItemMeta(meta);
            }

            //Add the item to the inventory
            inventory.setItem(i, item);

            //Increment the index variable
            i++;

        }

        //Open the inventory
        player.openInventory(inventory);
    }

    @Override
    public void perform(Player player, int rawSlot) {

        //Get the NameColor based on the slot that was clicked
        NameColor nameColor = plugin.getNameColorManager().getNameColor(rawSlot);

        //Get the player's active NameColor
        NameColor activeNameColor = plugin.getPreferencesManager().getNameColor(player);

        //Get the NameColors the player is allowed to use
        List<NameColor> allowedNameColors = plugin.getPreferencesManager().getAllowedNameColors(player);

        //Check if the selected NameColor is currently the player's active NameColor
        if (nameColor == activeNameColor) {
            MessageManager.messagePlayer(player, Message.NAME_COLOR_ALREADY_ACTIVE, null);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
            return;
        }

        //Check if the player has permission to use the NameColor
        if (!allowedNameColors.contains(nameColor)) {
            MessageManager.messagePlayer(player, Message.NAME_COLOR_LOCKED, null);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
            return;
        }

        //Change the player's active NameColor
        plugin.getPreferencesManager().setNameColor(player, nameColor);
        player.closeInventory();
        MessageManager.messagePlayer(player, Message.NAME_COLOR_CHANGED, null);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

    }

}
