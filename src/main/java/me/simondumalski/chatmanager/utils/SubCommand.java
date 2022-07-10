package me.simondumalski.chatmanager.utils;

import me.simondumalski.chatmanager.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    protected final Main plugin;

    /**
     * Constructor for SubCommands
     * @param plugin Instance of the main plugin class
     */
    public SubCommand(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns the command
     * @return Command
     */
    public abstract String getCommand();

    /**
     * Returns the description of the command
     * @return Command description
     */
    public abstract String getDescription();

    /**
     * Returns the usage of the command
     * @return Command usage
     */
    public abstract String getUsage();

    /**
     * Returns the permission required to use the command
     * @return Command permission
     */
    public abstract String getPermission();

    /**
     * Returns the list of tab complete arguments for the command
     * @param player Player performing the command
     * @param args Command arguments
     * @return List of strings
     */
    public abstract List<String> tabComplete(Player player, String[] args);

    /**
     * Performs the command for the specified player
     * @param player Player performing the command
     * @param args Command arguments
     */
    public abstract void perform(Player player, String[] args);

}
