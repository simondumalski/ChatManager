package me.simondumalski.chatmanager.commands.subcommands;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.colors.chatcolors.CMChatColor;
import me.simondumalski.chatmanager.colors.namecolors.NameColor;
import me.simondumalski.chatmanager.colors.titles.Title;
import me.simondumalski.chatmanager.utils.SubCommand;
import me.simondumalski.chatmanager.enums.Message;
import me.simondumalski.chatmanager.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class ListCommand extends SubCommand {

    /**
     * Constructor for the ListCommand
     * @param plugin Instance of the main plugin class
     */
    public ListCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public String getCommand() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists all of the valid ChatColors, NameColors, or Titles.";
    }

    @Override
    public String getUsage() {
        return "/chatmanager list <chatcolors|namecolors|titles>";
    }

    @Override
    public String getPermission() {
        return "chatmanager.admin.list";
    }

    @Override
    public List<String> tabComplete(Player player, String[] args) {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {

        //Check if the player sent a second arguments
        if (args.length < 2) {
            MessageManager.messagePlayer(player, Message.INVALID_USAGE, new String[]{getUsage()});
            return;
        }

        //Check if the player sent a valid second arguments
        if (!args[1].equalsIgnoreCase("chatcolors") && !args[1].equalsIgnoreCase("namecolors") && !args[1].equalsIgnoreCase("titles")) {
            MessageManager.messagePlayer(player, Message.INVALID_USAGE, new String[]{getUsage()});
            return;
        }

        //Get what the player wants to list
        String toList = args[1];

        switch (toList.toLowerCase()) {

            case "chatcolors":
                listChatColors(player);
                break;
            case "namecolors":
                listNameColors(player);
                break;
            case "titles":
                listTitles(player);
                break;
            default:
                MessageManager.messagePlayer(player, Message.INVALID_USAGE, new String[]{getUsage()});
                break;

        }

    }

    /**
     * Lists all the available ChatColors to the specified player
     * @param player Player to list the ChatColors to
     */
    private void listChatColors(Player player) {

        player.sendMessage(ChatColor.GOLD + "Chat Colors:");
        for (CMChatColor chatColor : plugin.getChatColorManager().getChatColors()) {
            String message = "&8- &f" + chatColor.getName();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }

    }

    /**
     * Lists all the available NameColors to the specified player
     * @param player Player to list the NameColors to
     */
    private void listNameColors(Player player) {

        player.sendMessage(ChatColor.GOLD + "Name Colors:");
        for (NameColor nameColor : plugin.getNameColorManager().getNameColors()) {
            String message = "&8- &f" + nameColor.getName();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }

    }

    /**
     * Lists all the available Titles to the specified player
     * @param player Player to list the Titles to
     */
    private void listTitles(Player player) {

        player.sendMessage(ChatColor.GOLD + "Titles:");
        for (Title title : plugin.getTitleManager().getTitles()) {
            String message = "&8- &f" + title.getName();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }

    }

}
