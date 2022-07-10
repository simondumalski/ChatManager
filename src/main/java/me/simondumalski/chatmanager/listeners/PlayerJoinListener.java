package me.simondumalski.chatmanager.listeners;

import me.simondumalski.chatmanager.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Main plugin;

    /**
     * Constructor for the PlayerJoinListener
     * @param plugin Instance of the main plugin class
     */
    public PlayerJoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        //Get the player who joined
        Player player = e.getPlayer();

        //Update the player's display name to show their active NameColor
        plugin.getPreferencesManager().updateNameColor(player);

    }

}
