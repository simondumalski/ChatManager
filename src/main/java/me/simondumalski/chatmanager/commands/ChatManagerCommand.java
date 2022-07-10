package me.simondumalski.chatmanager.commands;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.commands.subcommands.GiveCommand;
import me.simondumalski.chatmanager.commands.subcommands.ListCommand;
import me.simondumalski.chatmanager.enums.Message;
import me.simondumalski.chatmanager.utils.MessageManager;
import me.simondumalski.chatmanager.utils.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatManagerCommand implements TabExecutor {

    private final Main plugin;
    private final List<SubCommand> subCommands;

    /**
     * Constructor for the ChatManagerCommand
     * @param plugin Instance of the main plugin class
     */
    public ChatManagerCommand(Main plugin) {

        this.plugin = plugin;

        this.subCommands = new ArrayList<>();
        subCommands.add(new ListCommand(plugin));
        subCommands.add(new GiveCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            //Check if the player sent any command arguments
            if (args == null || args.length < 1) {
                MessageManager.messagePlayer(player, Message.UNKNOWN_COMMAND, null);
                return true;
            }

            //Find a matching SubCommand
            for (SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.getCommand())) {
                    if (player.hasPermission(subCommand.getPermission())) {
                        subCommand.perform(player, args);
                    } else {
                        MessageManager.messagePlayer(player, Message.INSUFFICIENT_PERMISSIONS, null);
                    }
                    return true;
                }
            }

            //If no matching SubCommand was found, send the unknown command message
            MessageManager.messagePlayer(player, Message.UNKNOWN_COMMAND, null);

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
