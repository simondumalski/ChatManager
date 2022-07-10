package me.simondumalski.chatmanager.playerdata;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.colors.chatcolors.CMChatColor;
import me.simondumalski.chatmanager.colors.namecolors.NameColor;
import me.simondumalski.chatmanager.colors.titles.Title;
import me.simondumalski.chatmanager.enums.Log;
import me.simondumalski.chatmanager.utils.ConsoleLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class DataConfig {

    private final Main plugin;
    private File file;
    private YamlConfiguration config;

    /**
     * Constructor for the DataConfig
     * Initializes the DataConfig
     * @param plugin Instance of the main plugin class
     */
    public DataConfig(Main plugin) {

        this.plugin = plugin;

        try {

            file = new File(plugin.getDataFolder(), "data.yml");
            config = YamlConfiguration.loadConfiguration(file);

            if (!config.isConfigurationSection("data")) {
                config.createSection("data");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Saves the data to the data.yml
     */
    public void saveData() {

        try {

            //Get the HashMaps containing the player preference data
            HashMap<UUID, CMChatColor> activeChatColors = plugin.getPreferencesManager().getActiveChatColors();
            HashMap<UUID, NameColor> activeNameColors = plugin.getPreferencesManager().getActiveNameColors();
            HashMap<UUID, Title> activeTitles = plugin.getPreferencesManager().getActiveTitles();

            //Save each entry in the ChatColors HashMap to file
            for (UUID uuid : activeChatColors.keySet()) {
                CMChatColor chatColor = activeChatColors.get(uuid);
                config.set("data." + uuid.toString() + ".chat-color", chatColor.getName());
            }

            //Save each entry in the NameColors HashMap to file
            for (UUID uuid : activeNameColors.keySet()) {
                NameColor nameColor = activeNameColors.get(uuid);
                config.set("data." + uuid.toString() + ".name-color", nameColor.getName());
            }

            //Save each entry in the Titles HashMap to file
            for (UUID uuid : activeTitles.keySet()) {
                Title title = activeTitles.get(uuid);
                config.set("data." + uuid.toString() + ".title", title.getName());
            }

            //Save the file
            config.save(file);

            //Log to console that the data.yml was saved
            ConsoleLogger.logToConsole(Log.FILE_SAVED, new String[]{"data.yml"});

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Loads the data from the data.yml
     */
    public void loadData() {

        try {

            //Initialize the HashMaps for the player preference data
            HashMap<UUID, CMChatColor> activeChatColors = new HashMap<>();
            HashMap<UUID, NameColor> activeNameColors = new HashMap<>();
            HashMap<UUID, Title> activeTitles = new HashMap<>();

            //Load the player data from file
            for (String uuidString : config.getConfigurationSection("data").getKeys(false)) {

                //Convert the uuidString into a UUID
                UUID uuid = UUID.fromString(uuidString);

                //Get the UUID's configuration section
                ConfigurationSection section = config.getConfigurationSection("data." + uuidString);

                //Get the player's ChatColor
                String chatColorName = section.getString("chat-color");
                CMChatColor chatColor = plugin.getChatColorManager().findChatColor(chatColorName);

                //Add the ChatColor to the HashMap if it is valid
                if (chatColor != null) {
                    activeChatColors.put(uuid, chatColor);
                }

                //Get the player's NameColor
                String nameColorName = section.getString("name-color");
                NameColor nameColor = plugin.getNameColorManager().findNameColor(nameColorName);

                //Add the NameColor to the HashMap if it is valid
                if (nameColor != null) {
                    activeNameColors.put(uuid, nameColor);
                }

                //Get the player's Title
                String titleName = section.getString("title");
                Title title = plugin.getTitleManager().findTitle(titleName);

                //Add the Title to the HashMap if it is valid
                if (title != null) {
                    activeTitles.put(uuid, title);
                }

            }

            //Set the PreferencesManager's HashMaps
            plugin.getPreferencesManager().setActiveChatColors(activeChatColors);
            plugin.getPreferencesManager().setActiveNameColors(activeNameColors);
            plugin.getPreferencesManager().setActiveTitles(activeTitles);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
