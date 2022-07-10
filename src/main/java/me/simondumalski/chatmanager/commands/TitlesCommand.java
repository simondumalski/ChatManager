package me.simondumalski.chatmanager.commands;

import me.simondumalski.chatmanager.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class TitlesCommand implements TabExecutor {

    private final Main plugin;

    /**
     * Constructor for the TitlesCommand
     * @param plugin Instance of the main plugin class
     */
    public TitlesCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            plugin.getTitleMenu().open(player);

        } else {
            sender.sendMessage(ChatColor.RED + "Only players may use this command.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}
