package me.simondumalski.chatmanager.colors.chatcolors;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CMChatColor {

    private final String name;
    private final ChatColor color;
    private final ItemStack menuItem;
    private final ItemStack voucherItem;

    /**
     * Constructor for ChatManager ChatColors
     * @param name Name of the ChatColor
     * @param color The ChatColor itself
     * @param menuItem Item to be used in menus
     * @param voucherItem Item to be used as a voucher
     */
    public CMChatColor(String name, ChatColor color, ItemStack menuItem, ItemStack voucherItem) {

        this.name = name;
        this.color = color;
        this.menuItem = menuItem;

        //Add the extra lines to the voucher item
        ItemMeta meta = voucherItem.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + name);
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "ChatManager ChatColor");
        meta.setLore(lore);
        voucherItem.setItemMeta(meta);

        this.voucherItem = voucherItem;
    }

    /**
     * Returns the name of the ChatColor
     * @return Name of the ChatColor
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the ChatColor itself
     * @return ChatColor
     */
    public ChatColor getColor() {
        return color;
    }

    /**
     * Returns the item used in menus to represent this CMChatColor
     * @return ItemStack
     */
    public ItemStack getMenuItem() {
        return menuItem;
    }

    /**
     * Returns the item used as a voucher for this CMChatColor
     * @return ItemStack
     */
    public ItemStack getVoucherItem() {
        return voucherItem;
    }

}
