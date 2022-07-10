package me.simondumalski.chatmanager.enums;

import java.util.logging.Level;

public enum Log {

    PLUGIN_ENABLED("&aPlugin enabled!", Level.INFO),
    PLUGIN_DISABLED("&cPlugin disabled!", Level.INFO),

    LOADED_ITEMS("Successfully loaded %args0% %args1%!", Level.INFO),
    FILE_SAVED("Saved %args0%!", Level.INFO),

    INVALID_MENU_ITEM("The %args0% item for menu %args1% is invalid!", Level.SEVERE),
    INVALID_CHAT_FORMAT("The chat format specified in the config.yml is invalid!", Level.SEVERE),

    INVALID_SAVE_INTERVAL("Save interval is invalid! Defaulting to 300...", Level.WARNING),
    INVALID_COLOR("The %args0% %args1% is invalid and will not be added!", Level.WARNING),
    INVALID_ITEM("The %args0% item for %args1% %args2% is invalid and will not be added!", Level.WARNING),
    INVALID_ITEM_SEVERE("The %args0% item for %args1% %args2% is invalid! Disabling plugin...", Level.SEVERE);

    private final String message;
    private final Level level;

    /**
     * Constructor for Log messages
     * @param message Message of the log
     * @param level Severity level of the log
     */
    Log(String message, Level level) {
        this.message = message;
        this.level = level;
    }

    /**
     * Returns the message that this Log will print
     * @return String message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the severity level of this Log
     * @return Level
     */
    public Level getLevel() {
        return level;
    }

}
