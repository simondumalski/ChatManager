package me.simondumalski.chatmanager;

import me.simondumalski.chatmanager.colors.chatcolors.ChatColorManager;
import me.simondumalski.chatmanager.colors.namecolors.NameColorManager;
import me.simondumalski.chatmanager.colors.titles.TitleManager;
import me.simondumalski.chatmanager.commands.ChatManagerCommand;
import me.simondumalski.chatmanager.commands.ColorsCommand;
import me.simondumalski.chatmanager.commands.TitlesCommand;
import me.simondumalski.chatmanager.enums.Log;
import me.simondumalski.chatmanager.listeners.InventoryClickListener;
import me.simondumalski.chatmanager.listeners.PlayerInteractListener;
import me.simondumalski.chatmanager.menus.ChatColorMenu;
import me.simondumalski.chatmanager.menus.ColorSelectionMenu;
import me.simondumalski.chatmanager.menus.NameColorMenu;
import me.simondumalski.chatmanager.menus.TitleMenu;
import me.simondumalski.chatmanager.playerdata.DataConfig;
import me.simondumalski.chatmanager.listeners.PlayerChatListener;
import me.simondumalski.chatmanager.listeners.PlayerJoinListener;
import me.simondumalski.chatmanager.playerdata.PreferencesManager;
import me.simondumalski.chatmanager.tasks.DataSaveTask;
import me.simondumalski.chatmanager.utils.ConfigData;
import me.simondumalski.chatmanager.utils.ConsoleLogger;
import me.simondumalski.chatmanager.utils.MessageManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.Permissions;

public final class Main extends JavaPlugin {

    private DataConfig dataConfig;
    private ConfigData configData;

    private Permission perm;

    private ChatColorManager chatColorManager;
    private NameColorManager nameColorManager;
    private TitleManager titleManager;

    private PreferencesManager preferencesManager;

    private ColorSelectionMenu colorSelectionMenu;
    private ChatColorMenu chatColorMenu;
    private NameColorMenu nameColorMenu;
    private TitleMenu titleMenu;

    @Override
    public void onEnable() {

        //Initialize the config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        reloadConfig();
        configData = new ConfigData(this);

        //Initialize the message managers
        ConsoleLogger.setLogger(getLogger());
        MessageManager.setPlugin(this);

        //Initialize Vault
        perm = getServer().getServicesManager().getRegistration(Permission.class).getProvider();

        //Initialize the plugin managers
        chatColorManager = new ChatColorManager(this);
        nameColorManager = new NameColorManager(this);
        titleManager = new TitleManager(this);

        preferencesManager = new PreferencesManager(this);

        //Initialize the menus
        colorSelectionMenu = new ColorSelectionMenu(this);
        chatColorMenu = new ChatColorMenu(this);
        nameColorMenu = new NameColorMenu(this);
        titleMenu = new TitleMenu(this);

        //Initialize the data.yml
        dataConfig = new DataConfig(this);
        dataConfig.loadData();

        //Register the event listeners
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);

        //Set the command executors
        getCommand("chatmanager").setExecutor(new ChatManagerCommand(this));
        getCommand("colors").setExecutor(new ColorsCommand(this));
        getCommand("titles").setExecutor(new TitlesCommand(this));

        //Schedule the data save task
        int saveInterval = getConfig().getInt("plugin.save-interval");

        if (saveInterval == 0) {
            saveInterval = 300;
            ConsoleLogger.logToConsole(Log.INVALID_SAVE_INTERVAL, null);
        }

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new DataSaveTask(this), 300L * 20, saveInterval * 20L);

        //Log to console that the plugin has been enabled
        ConsoleLogger.logToConsole(Log.PLUGIN_ENABLED, null);

    }

    @Override
    public void onDisable() {

        //Un-schedule the tasks
        getServer().getScheduler().cancelTasks(this);

        //Save the data.yml
        dataConfig.saveData();

        //Log to console that the plugin has been disabled
        ConsoleLogger.logToConsole(Log.PLUGIN_DISABLED, null);

    }

    /**
     * Returns the instance of the plugin's DataConfig
     * @return DataConfig
     */
    public DataConfig getDataConfig() {
        return dataConfig;
    }

    /**
     * Returns the instance of the plugin's ConfigData
     * @return ConfigData
     */
    public ConfigData getConfigData() {
        return configData;
    }

    /**
     * Returns the instance of the plugin's ChatColorManager
     * @return ChatColorManager
     */
    public ChatColorManager getChatColorManager() {
        return chatColorManager;
    }

    /**
     * Returns the instance of the plugin's NameColorManager
     * @return NameColorManager
     */
    public NameColorManager getNameColorManager() {
        return nameColorManager;
    }

    /**
     * Returns the instance of the plugin's TitleManager
     * @return TitleManager
     */
    public TitleManager getTitleManager() {
        return titleManager;
    }

    /**
     * Returns the instance of the plugin's PreferencesManager
     * @return PreferencesManager
     */
    public PreferencesManager getPreferencesManager() {
        return preferencesManager;
    }

    /**
     * Returns the instance of the plugin's ColorSelectionMenu
     * @return ColorSelectionMenu
     */
    public ColorSelectionMenu getColorSelectionMenu() {
        return colorSelectionMenu;
    }

    /**
     * Returns the instance of the plugin's ChatColorMenu
     * @return ChatColorMenu
     */
    public ChatColorMenu getChatColorMenu() {
        return chatColorMenu;
    }

    /**
     * Returns the instance of the plugin's NameColorMenu
     * @return NameColorMenu
     */
    public NameColorMenu getNameColorMenu() {
        return nameColorMenu;
    }

    /**
     * Returns the instance of the plugin's TitleMenu
     * @return TitleMenu
     */
    public TitleMenu getTitleMenu() {
        return titleMenu;
    }

    /**
     * Returns the instance of the Permission provider
     * @return Permission
     */
    public Permission getPerm() {
        return perm;
    }

}
