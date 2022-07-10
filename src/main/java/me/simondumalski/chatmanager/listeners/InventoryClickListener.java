package me.simondumalski.chatmanager.listeners;

import me.simondumalski.chatmanager.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final Main plugin;

    /**
     * Constructor for the InventoryClickListener
     * @param plugin Instance of the main plugin class
     */
    public InventoryClickListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        //Check if it was a player who clicked
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        //Check if the item that was clicked is valid
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        //Get the player who clicked
        Player player = (Player) e.getWhoClicked();

        //Get the title of the menu that was clicked
        String menuTitle = e.getView().getTitle();

        e.setCancelled(true);

        //Get the slot that was clicked
        int rawSlot = e.getRawSlot();

        //Check if the menu is a plugin menu
        switch (menuTitle) {

            //Color Selection Menu
            case "Color Selection":

                switch (rawSlot) {
                    case 12:
                        player.closeInventory();
                        plugin.getChatColorMenu().open(player);
                        break;
                    case 14:
                        player.closeInventory();
                        plugin.getNameColorMenu().open(player);
                        break;
                    default:
                        break;
                }

                break;

            //Chat Color Menu
            case "Chat Colors":
                plugin.getChatColorMenu().perform(player, rawSlot);
                break;

            //Name Color Menu
            case "Name Colors":
                plugin.getNameColorMenu().perform(player, rawSlot);
                break;

            //Title Menu
            case "Titles":
                plugin.getTitleMenu().perform(player, rawSlot);
                break;

            default:
                e.setCancelled(false);
                break;


        }

    }

}
