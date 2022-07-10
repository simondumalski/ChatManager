package me.simondumalski.chatmanager.menus;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.colors.namecolors.NameColor;
import me.simondumalski.chatmanager.colors.titles.Title;
import me.simondumalski.chatmanager.enums.Message;
import me.simondumalski.chatmanager.utils.Menu;
import me.simondumalski.chatmanager.utils.MessageManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TitleMenu extends Menu {
    /**
     * Constructor for the TitleMenu
     * @param plugin Instance of the main plugin class
     */
    public TitleMenu(Main plugin) {
        super(plugin);
    }

    @Override
    public void open(Player player) {

        //Create the inventory
        Inventory inventory = plugin.getServer().createInventory(null, 54, "Titles");

        //Get the player's active Title
        Title activeTitle = plugin.getPreferencesManager().getTitle(player);

        //Get the list of Titles the player is allowed to use
        List<Title> allowedTitles = plugin.getPreferencesManager().getAllowedTitles(player);

        //Add each Title's menu item to the inventory
        int i = 0;

        for (Title title : plugin.getTitleManager().getTitles()) {

            //Check if the current index is greater than the inventory size
            if (i > 53) {
                break;
            }

            //Get the menu item
            ItemStack item = title.getMenuItem().clone();

            //Get the menu item meta
            ItemMeta meta = item.getItemMeta();

            //Change the %status% placeholder in the item name based on if the NameColor is active, unlocked, or locked
            if (meta.getDisplayName().contains("%status%")) {

                if (title == activeTitle) {
                    meta.setDisplayName(meta.getDisplayName().replace("%status%", plugin.getConfigData().ACTIVE));
                } else if (allowedTitles.contains(title)) {
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

        //Get the Title based on the slot that was clicked
        Title title = plugin.getTitleManager().getTitle(rawSlot);

        //Get the player's active Title
        Title activeTitle = plugin.getPreferencesManager().getTitle(player);

        //Get the Titles the player is allowed to use
        List<Title> allowedTitles = plugin.getPreferencesManager().getAllowedTitles(player);

        //Check if the selected Title is currently the player's active Title
        if (title == activeTitle) {
            MessageManager.messagePlayer(player, Message.TITLE_ALREADY_ACTIVE, null);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
            return;
        }

        //Check if the player has permission to use the Title
        if (!allowedTitles.contains(title)) {
            MessageManager.messagePlayer(player, Message.TITLE_LOCKED, null);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
            return;
        }

        //Change the player's active Title
        plugin.getPreferencesManager().setTitle(player, title);
        player.closeInventory();
        MessageManager.messagePlayer(player, Message.TITLE_CHANGED, null);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

    }

}
