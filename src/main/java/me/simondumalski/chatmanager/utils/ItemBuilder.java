package me.simondumalski.chatmanager.utils;

import me.simondumalski.chatmanager.colors.namecolors.NameColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    /**
     * Builds and returns an ItemStack based on the information in the provided ConfigurationSection
     * @param section ConfigurationSection containing the item information
     * @return Created item
     */
    public static ItemStack buildItem(ConfigurationSection section) {

        //Get the item details from the config.yml
        Material material = Material.matchMaterial(section.getString("material"));
        String name = section.getString("name");
        List<String> lore = section.getStringList("lore");
        boolean glowing = section.getBoolean("glowing");

        if (material == null || name == null || lore == null) {
            return null;
        }

        //Create the item
        ItemStack item = new ItemStack(material, 1);

        //Set the item to be glowing if the option is set to true
        if (glowing) {
            item.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
        }

        //Get the item meta
        ItemMeta meta = item.getItemMeta();

        //Set the enchants to be hidden if the option is set to ture
        if (glowing) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        //Set the item name
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        //Update the lore
        List<String> updatedLore = new ArrayList<>();

        for (String line : lore) {
            updatedLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        //Set the item lore
        meta.setLore(updatedLore);

        //Set the item meta
        item.setItemMeta(meta);

        //Return the created item
        return item;

    }

    /**
     * Builds and returns an ItemStack based on the information in the provided ConfigurationSection
     * @param name Name of the color for the item
     * @param section ConfigurationSection containing the item information
     * @return Created item
     */
    public static ItemStack buildItem(String name, ConfigurationSection section) {

        //Build the item based on the config information
        ItemStack item = buildItem(section);

        //Check if the item was created successfully
        if (item == null) {
            return null;
        }

        //Get the item meta
        ItemMeta meta = item.getItemMeta();

        //Change the %color% placeholder
        List<String> updatedLore = new ArrayList<>();

        if (name != null) {

            //Try to get the ChatColor from the name
            ChatColor chatColor;

            try {
                chatColor = ChatColor.valueOf(name);
            } catch (IllegalArgumentException ignored) {
                chatColor = ChatColor.WHITE;
            }

            //Item display name
            if (meta.getDisplayName().contains("%color%")) {
                meta.setDisplayName(meta.getDisplayName().replace("%color%", chatColor + name));
            }

            //Item lore
            for (String line : meta.getLore()) {

                if (line.contains("%color%")) {
                    line = line.replace("%color%", chatColor + name);
                }

                updatedLore.add(line);

            }

            //Set the item lore
            meta.setLore(updatedLore);

        }

        //Set the item meta
        item.setItemMeta(meta);

        //Return the item
        return item;
    }

}
