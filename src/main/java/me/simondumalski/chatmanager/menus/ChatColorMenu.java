package me.simondumalski.chatmanager.menus;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.colors.chatcolors.CMChatColor;
import me.simondumalski.chatmanager.enums.Message;
import me.simondumalski.chatmanager.utils.Menu;
import me.simondumalski.chatmanager.utils.MessageManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChatColorMenu extends Menu {

    /**
     * Constructor for the ChatColorMenu
     * @param plugin Instance of the main plugin class
     */
    public ChatColorMenu(Main plugin) {
        super(plugin);
    }

    @Override
    public void open(Player player) {

        //Create the inventory
        Inventory inventory = plugin.getServer().createInventory(null, 18, "Chat Colors");

        //Get the player's active ChatColor
        CMChatColor activeChatColor = plugin.getPreferencesManager().getChatColor(player);

        //Get the list of ChatColors the player is allowed to use
        List<CMChatColor> allowedChatColors = plugin.getPreferencesManager().getAllowedChatColors(player);

        //Add each ChatColor's menu item to the inventory
        int i = 0;

        for (CMChatColor chatColor : plugin.getChatColorManager().getChatColors()) {

            //Get the menu item
            ItemStack item = chatColor.getMenuItem().clone();

            //Get the menu item meta
            ItemMeta meta = item.getItemMeta();

            //Change the %status% placeholder in the item name based on if the ChatColor is active, unlocked, or locked
            if (meta.getDisplayName().contains("%status%")) {

                if (chatColor == activeChatColor) {
                    meta.setDisplayName(meta.getDisplayName().replace("%status%", plugin.getConfigData().ACTIVE));
                } else if (allowedChatColors.contains(chatColor)) {
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

        //Get the ChatColor based on the slot that was clicked
        CMChatColor chatColor = plugin.getChatColorManager().getChatColor(rawSlot);

        //Get the player's active ChatColor
        CMChatColor activeChatColor = plugin.getPreferencesManager().getChatColor(player);

        //Get the ChatColors the player is allowed to use
        List<CMChatColor> allowedChatColors = plugin.getPreferencesManager().getAllowedChatColors(player);

        //Check if the selected ChatColor is currently the player's active ChatColor
        if (chatColor == activeChatColor) {
            MessageManager.messagePlayer(player, Message.CHAT_COLOR_ALREADY_ACTIVE, null);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
            return;
        }

        //Check if the player has permission to use the ChatColor
        if (!allowedChatColors.contains(chatColor)) {
            MessageManager.messagePlayer(player, Message.CHAT_COLOR_LOCKED, null);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
            return;
        }

        //Change the player's active ChatColor
        plugin.getPreferencesManager().setChatColor(player, chatColor);
        player.closeInventory();
        MessageManager.messagePlayer(player, Message.CHAT_COLOR_CHANGED, null);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);

    }

}
