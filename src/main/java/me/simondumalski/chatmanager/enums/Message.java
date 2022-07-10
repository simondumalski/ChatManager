package me.simondumalski.chatmanager.enums;

public enum Message {

    PREFIX("messages.prefix"),

    INVALID_MENU_ITEM("messages.errors.invalid-menu-item"),
    INSUFFICIENT_PERMISSIONS("messages.errors.insufficient-permissions"),
    INVALID_USAGE("messages.errors.invalid-usage"),
    INVALID_PLAYER("messages.errors.invalid-player"),
    INVALID_ARGUMENT("messages.errors.invalid-argument"),
    UNKNOWN_COMMAND("messages.errors.unknown-command"),

    VOUCHER_GIVEN("messages.vouchers.given"),
    VOUCHER_RECEIVED("messages.vouchers.received"),
    VOUCHER_REDEEMED("messages.vouchers.redeemed"),
    VOUCHER_ALREADY_UNLOCKED("messages.vouchers.already-unlocked"),

    CHAT_COLOR_ALREADY_ACTIVE("messages.chat-color.already-active"),
    CHAT_COLOR_CHANGED("messages.chat-color.changed"),
    CHAT_COLOR_LOCKED("messages.chat-color.locked"),

    NAME_COLOR_ALREADY_ACTIVE("messages.name-color.already-active"),
    NAME_COLOR_CHANGED("messages.name-color.changed"),
    NAME_COLOR_LOCKED("messages.name-color.locked"),

    TITLE_ALREADY_ACTIVE("messages.title.already-active"),
    TITLE_CHANGED("messages.title.changed"),
    TITLE_LOCKED("messages.title.locked");

    private final String configValue;

    /**
     * Constructor for Messages
     * @param configValue Config value of the message
     */
    Message(String configValue) {
        this.configValue = configValue;
    }

    /**
     * Returns the config value of this message
     * @return Config value
     */
    public String getConfigValue() {
        return configValue;
    }

}
