package me.simondumalski.chatmanager.listeners;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.enums.Log;
import me.simondumalski.chatmanager.utils.ConsoleLogger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private final Main plugin;

    /**
     * Constructor for the PlayerChatListener
     * @param plugin Instance of the main plugin class
     */
    public PlayerChatListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        //Get the player who is chatting
        Player player = e.getPlayer();

        //Get the message the player sent
        String message = e.getMessage();

        //Strip any chat colors sent by the player from the message
        message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));

        //Get the player's active ChatColor
        ChatColor chatColor = plugin.getPreferencesManager().getChatColor(player).getColor();

        //Add the ChatColor to the message
        e.setMessage(chatColor + message);

        //Get the chat format from the config.yml
        String chatFormat = plugin.getConfig().getString("plugin.chat-format");

        //Check if the specified chat format is valid
        if (chatFormat == null) {
            ConsoleLogger.logToConsole(Log.INVALID_CHAT_FORMAT, null);
            return;
        }

        //Add the player's active Title to the chat format
        String title = plugin.getPreferencesManager().getTitle(player).getTitle();
        chatFormat = title + chatFormat;
        e.setFormat(ChatColor.translateAlternateColorCodes('&', chatFormat));

    }

}
