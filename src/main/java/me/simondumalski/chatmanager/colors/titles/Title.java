package me.simondumalski.chatmanager.colors.titles;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Title {

    private final String name;
    private final String title;
    private final ItemStack menuItem;
    private final ItemStack voucherItem;

    /**
     * Constructor for Titles
     * @param name Name of the Title
     * @param title The Title itself
     * @param menuItem Item to be used in menus
     * @param voucherItem Item to be used as a voucher
     */
    public Title(String name, String title, ItemStack menuItem, ItemStack voucherItem) {

        this.name = name;
        this.title = title;
        this.menuItem = menuItem;

        //Add the extra lines to the voucher item
        ItemMeta meta = voucherItem.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + name);
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "ChatManager Title" );
        meta.setLore(lore);
        voucherItem.setItemMeta(meta);

        this.voucherItem = voucherItem;
    }

    /**
     * Returns the name of the Title
     * @return Name of the Title
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Title string
     * @return Title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the item used in menus to represent this Title
     * @return ItemStack
     */
    public ItemStack getMenuItem() {
        return menuItem;
    }

    /**
     * Returns the item used as a voucher for this Title
     * @return ItemStack
     */
    public ItemStack getVoucherItem() {
        return voucherItem;
    }

}
