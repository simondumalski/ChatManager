package me.simondumalski.chatmanager.colors.namecolors;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NameColor {

    private final String name;
    private final List<ChatColor> colors;
    private final ItemStack menuItem;
    private final ItemStack voucherItem;

    /**
     * Constructor for NameColors
     * @param name Name of the NameColor, unique identifier
     * @param colors List of colors used in this NameColor
     * @param menuItem Item to be used in menus
     * @param voucherItem Item to be used as a voucher
     */
    public NameColor(String name, List<ChatColor> colors, ItemStack menuItem, ItemStack voucherItem) {

        this.name = name;
        this.colors = colors;
        this.menuItem = menuItem;

        //Add the extra lines to the voucher item
        ItemMeta meta = voucherItem.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + name);
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "ChatManager NameColor");
        meta.setLore(lore);
        voucherItem.setItemMeta(meta);

        this.voucherItem = voucherItem;
    }

    /**
     * Returns the name of this NameColor
     * @return Name of the NameColor
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of colors used in this NameColor
     * @return List of colors
     */
    public List<ChatColor> getColors() {
        return colors;
    }

    /**
     * Returns the item used in menus to represent this NameColor
     * @return ItemStack
     */
    public ItemStack getMenuItem() {
        return menuItem;
    }

    /**
     * Returns the item used as a voucher for this NameColor
     * @return ItemStack
     */
    public ItemStack getVoucherItem() {
        return voucherItem;
    }

}
