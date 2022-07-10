package me.simondumalski.chatmanager.utils;

import me.simondumalski.chatmanager.enums.Log;
import org.bukkit.ChatColor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogger {

    /**
     * Instance of the plugin's Logger
     */
    private static Logger logger;

    /**
     * Logs a message to console
     * @param log Message to log
     */
    public static void logToConsole(Log log, String[] args) {

        //Get the message to log
        String message = log.getMessage();

        //Get the Level of the log
        Level level = log.getLevel();

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

        //Log the message to console
        logger.log(level, message);

    }

    /**
     * Sets the reference to the plugin's logger
     * @param logger Logger
     */
    public static void setLogger(Logger logger) {
        ConsoleLogger.logger = logger;
    }

}
