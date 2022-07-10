package me.simondumalski.chatmanager.utils;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.enums.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

    /**
     * Instance of the main plugin class
     */
    private static Main plugin;

    /**
     * Sends the specified CommandSender a message set in the config.yml
     * @param sender CommandSender to send the message to
     * @param configValue Message to send
     */
    public static void messagePlayer(CommandSender sender, Message configValue, String[] args) {

        //Get the message from the config.yml
        String message = plugin.getConfig().getString(configValue.getConfigValue());

        //Check that the message is valid
        if (message == null) {
            sender.sendMessage(configValue.getConfigValue());
            return;
        }

        //Get the prefix from the config.yml
        String prefix = plugin.getConfig().getString(Message.PREFIX.getConfigValue());

        //Replace the %prefix% placeholder
        if (prefix != null && message.contains("%prefix%")) {
            message = message.replace("%prefix%", prefix);
        }

        //Replace the %args#% placeholders
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                String placeholder = "%args" + i + "%";
                if (message.contains(placeholder)) {
                    message = message.replace(placeholder, args[i]);
                }
            }
        }

        //Translate the color codes
        message = ChatColor.translateAlternateColorCodes('&', message);

        //Send the message
        sender.sendMessage(message);

    }

    /**
     * Sets the reference to the instance of the main plugin class
     * @param plugin Main plugin class
     */
    public static void setPlugin(Main plugin) {
        MessageManager.plugin = plugin;
    }

}
