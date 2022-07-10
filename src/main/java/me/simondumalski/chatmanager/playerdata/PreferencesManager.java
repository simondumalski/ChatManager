package me.simondumalski.chatmanager.playerdata;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.colors.chatcolors.CMChatColor;
import me.simondumalski.chatmanager.colors.namecolors.NameColor;
import me.simondumalski.chatmanager.colors.titles.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PreferencesManager {

    private final Main plugin;
    private HashMap<UUID, CMChatColor> activeChatColors = new HashMap<>();
    private HashMap<UUID, NameColor> activeNameColors = new HashMap<>();
    private HashMap<UUID, Title> activeTitles = new HashMap<>();

    /**
     * Constructor for the PreferencesManager
     * @param plugin Instance of the main plugin class
     */
    public PreferencesManager(Main plugin) {
        this.plugin = plugin;
    }

    /*
     * Chat Colors
     */

    /**
     * Returns the specified player's active ChatColor
     * @param player Player whose ChatColor to get
     * @return Active ChatColor
     */
    public CMChatColor getChatColor(Player player) {

        if (!activeChatColors.containsKey(player.getUniqueId())) {
            activeChatColors.put(player.getUniqueId(), plugin.getChatColorManager().getDefaultChatColor());
        }

        return activeChatColors.get(player.getUniqueId());
    }

    /**
     * Sets the specified player's active ChatColor
     * @param player Player whose ChatColor to set
     * @param chatColor ChatColor to set
     */
    public void setChatColor(Player player, CMChatColor chatColor) {
        activeChatColors.put(player.getUniqueId(), chatColor);
    }

    /**
     * Returns the list of ChatColors the specified player is allowed to use
     * @param player Player whose allowed ChatColors to get
     * @return List of allowed ChatColors
     */
    public List<CMChatColor> getAllowedChatColors(Player player) {

        List<CMChatColor> allowedChatColors = new ArrayList<>();

        for (CMChatColor chatColor : plugin.getChatColorManager().getChatColors()) {

            if (chatColor == plugin.getChatColorManager().getDefaultChatColor()) {
                allowedChatColors.add(chatColor);
            } else if (player.hasPermission("chatmanager.chat." + chatColor.getName())) {
                allowedChatColors.add(chatColor);
            }

        }

        return allowedChatColors;
    }

    /**
     * Returns the HashMap of Player UUIDs and their active ChatColors
     * @return HashMap
     */
    public HashMap<UUID, CMChatColor> getActiveChatColors() {
        return activeChatColors;
    }

    /**
     * Sets the HashMap of Player UUIDs and their active ChatColors
     * @param activeChatColors HashMap
     */
    public void setActiveChatColors(HashMap<UUID, CMChatColor> activeChatColors) {
        this.activeChatColors = activeChatColors;
    }

    /*
     * Name Colors
     */

    /**
     * Returns the specified player's active NameColor
     * @param player Player whose NameColor to get
     * @return Active NameColor
     */
    public NameColor getNameColor(Player player) {

        if (!activeNameColors.containsKey(player.getUniqueId())) {
            activeNameColors.put(player.getUniqueId(), plugin.getNameColorManager().getDefaultNameColor());
        }

        return activeNameColors.get(player.getUniqueId());
    }

    /**
     * Sets the specified player's active NameColor
     * @param player Player whose NameColor to set
     * @param nameColor NameColor to set
     */
    public void setNameColor(Player player, NameColor nameColor) {
        activeNameColors.put(player.getUniqueId(), nameColor);
        updateNameColor(player);
    }

    /**
     * Returns the list of NameColors the specified player is allowed to use
     * @param player Player whose allowed NameColors to get
     * @return List of allowed NameColors
     */
    public List<NameColor> getAllowedNameColors(Player player) {

        List<NameColor> allowedNameColors = new ArrayList<>();

        for (NameColor nameColor : plugin.getNameColorManager().getNameColors()) {

            if (nameColor == plugin.getNameColorManager().getDefaultNameColor()) {
                allowedNameColors.add(nameColor);
            } else if (player.hasPermission("chatmanager.name." + nameColor.getName())) {
                allowedNameColors.add(nameColor);
            }

        }

        return allowedNameColors;
    }

    /**
     * Updates the player's display name to show their active NameColor
     * @param player Player whose name to update
     */
    public void updateNameColor(Player player) {

        //Get the player's active NameColor
        NameColor nameColor = getNameColor(player);
        List<ChatColor> colors = nameColor.getColors();

        //Get the player's username
        String username = player.getName();

        //Add the colors to the username
        StringBuilder coloredUsername = new StringBuilder();

        for (int i = 0; i < username.length(); i++) {
            coloredUsername.append(colors.get(i % colors.size())).append(username.charAt(i));
        }

        //Set the player's display name to the colored username
        player.setDisplayName(coloredUsername.toString());

    }

    /**
     * Returns the HashMap of Player UUIDs and their active NameColors
     * @return HashMap
     */
    public HashMap<UUID, NameColor> getActiveNameColors() {
        return activeNameColors;
    }

    /**
     * Sets the HashMap of Player UUIDs and their active NameColors
     * @param activeNameColors HashMap
     */
    public void setActiveNameColors(HashMap<UUID, NameColor> activeNameColors) {
        this.activeNameColors = activeNameColors;
    }

    /*
     * Titles
     */

    /**
     * Returns the specified player's active Title
     * @param player Player whose Title to get
     * @return Active Title
     */
    public Title getTitle(Player player) {

        if (!activeTitles.containsKey(player.getUniqueId())) {
            activeTitles.put(player.getUniqueId(), plugin.getTitleManager().getDefaultTitle());
        }

        return activeTitles.get(player.getUniqueId());
    }

    /**
     * Sets the specified player's active Title
     * @param player Player whose Title to set
     * @param title Title to set
     */
    public void setTitle(Player player, Title title) {
        activeTitles.put(player.getUniqueId(), title);
    }

    /**
     * Returns the list of Titles the specified player is allowed to use
     * @param player Player whose allowed Titles to get
     * @return List of allowed Titles
     */
    public List<Title> getAllowedTitles(Player player) {

        List<Title> allowedTitles = new ArrayList<>();

        for (Title title : plugin.getTitleManager().getTitles()) {

            if (title == plugin.getTitleManager().getDefaultTitle()) {
                allowedTitles.add(title);
            } else if (player.hasPermission("chatmanager.title." + title.getName())) {
                allowedTitles.add(title);
            }

        }

        return allowedTitles;
    }

    /**
     * Returns the HashMap of Player UUIDs and their active Titles
     * @return HashMap
     */
    public HashMap<UUID, Title> getActiveTitles() {
        return activeTitles;
    }

    /**
     * Sets the HashMap of Player UUIDs and their active Titles
     * @param activeTitles HashMap
     */
    public void setActiveTitles(HashMap<UUID, Title> activeTitles) {
        this.activeTitles = activeTitles;
    }
}
