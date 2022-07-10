package me.simondumalski.chatmanager.listeners;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.colors.chatcolors.CMChatColor;
import me.simondumalski.chatmanager.colors.namecolors.NameColor;
import me.simondumalski.chatmanager.colors.titles.Title;
import me.simondumalski.chatmanager.enums.Message;
import me.simondumalski.chatmanager.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerInteractListener implements Listener {

    private final Main plugin;

    /**
     * Constructor for the PlayerInteractListener
     * @param plugin Instance of the main plugin class
     */
    public PlayerInteractListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        //Check if the action that was done was a right-click
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        //Get the player who interacted
        Player player = e.getPlayer();

        //Get the item the player is holding
        ItemStack voucher = e.getItem();

        //Check if the item is valid
        if (voucher == null || voucher.getType() == Material.AIR) {
            return;
        }

        //Get the item meta
        ItemMeta meta = voucher.getItemMeta();

        //Get the lore of the item
        List<String> lore = meta.getLore();

        //Get the last line of the lore
        String line = lore.get(lore.size() - 1);

        //Check if the line contains "ChatManager"
        if (!line.contains("ChatManager")) {
            return;
        }

        //Get the type of voucher the item is
        String voucherType;

        if (line.contains("ChatColor")) {
            voucherType = "ChatColor";
        } else if (line.contains("NameColor")) {
            voucherType = "NameColor";
        } else if (line.contains("Title")) {
            System.out.println("title voucher");
            voucherType = "Title";
        } else {
            return;
        }

        switch (voucherType) {

            case "ChatColor":

                //Get the second last line of the lore
                String chatColorName = ChatColor.stripColor(lore.get(lore.size() - 2));

                //Find the ChatColor that matches the color name
                CMChatColor chatColor = plugin.getChatColorManager().findChatColor(chatColorName);

                //Check if the ChatColor is valid
                if (chatColor == null) {
                    System.out.println("chat color is invalid");
                    return;
                }

                //Cancel the event to prevent the item from being placed if placeable
                e.setCancelled(true);

                //Check if the player already has this ChatColor unlocked
                if (plugin.getPreferencesManager().getAllowedChatColors(player).contains(chatColor)) {
                    MessageManager.messagePlayer(player, Message.VOUCHER_ALREADY_UNLOCKED, new String[]{"chat color"});
                    return;
                }

                //Take the voucher from the player
                if (voucher.getAmount() > 1) {
                    voucher.setAmount(voucher.getAmount() - 1);
                } else {
                    player.getInventory().remove(voucher);
                }

                //Give the player permission to use the ChatColor
                plugin.getPerm().playerAdd(null, player, "chatmanager.chat." + chatColor.getName());
                MessageManager.messagePlayer(player, Message.VOUCHER_REDEEMED, null);

                break;
            case "NameColor":

                //Get the second last line of the lore
                String nameColorName = ChatColor.stripColor(lore.get(lore.size() - 2));

                //Find the NameColor that matches the color name
                NameColor nameColor = plugin.getNameColorManager().findNameColor(nameColorName);

                //Check if the NameColor is valid
                if (nameColor == null) {
                    System.out.println("name color is invalid");
                    return;
                }

                //Cancel the event to prevent the item from being placed if placeable
                e.setCancelled(true);

                //Check if the player already has this NameColor unlocked
                if (plugin.getPreferencesManager().getAllowedNameColors(player).contains(nameColor)) {
                    MessageManager.messagePlayer(player, Message.VOUCHER_ALREADY_UNLOCKED, new String[]{"name color"});
                    return;
                }

                //Take the voucher from the player
                if (voucher.getAmount() > 1) {
                    voucher.setAmount(voucher.getAmount() - 1);
                } else {
                    player.getInventory().remove(voucher);
                }

                //Give the player permission to use the NameColor
                plugin.getPerm().playerAdd(null, player, "chatmanager.name." + nameColor.getName());
                MessageManager.messagePlayer(player, Message.VOUCHER_REDEEMED, null);

                break;
            case "Title":

                //Get the second last line of the lore
                String titleName = ChatColor.stripColor(lore.get(lore.size() - 2));

                //Find the Title that matches the color name
                Title title = plugin.getTitleManager().findTitle(titleName);

                //Check if the Title is valid
                if (title == null) {
                    return;
                }

                //Cancel the event to prevent the item from being placed if placeable
                e.setCancelled(true);

                //Check if the player already has this Title unlocked
                if (plugin.getPreferencesManager().getAllowedTitles(player).contains(title)) {
                    MessageManager.messagePlayer(player, Message.VOUCHER_ALREADY_UNLOCKED, new String[]{"title"});
                    return;
                }

                //Take the voucher from the player
                if (voucher.getAmount() > 1) {
                    voucher.setAmount(voucher.getAmount() - 1);
                } else {
                    player.getInventory().remove(voucher);
                }

                //Give the player permission to use the Title
                plugin.getPerm().playerAdd(null, player, "chatmanager.title." + title.getName());
                MessageManager.messagePlayer(player, Message.VOUCHER_REDEEMED, null);

                break;

            default:
                break;

        }

    }

}
