package me.simondumalski.chatmanager.commands.subcommands;

import me.simondumalski.chatmanager.Main;
import me.simondumalski.chatmanager.colors.chatcolors.CMChatColor;
import me.simondumalski.chatmanager.colors.namecolors.NameColor;
import me.simondumalski.chatmanager.colors.titles.Title;
import me.simondumalski.chatmanager.utils.SubCommand;
import me.simondumalski.chatmanager.enums.Message;
import me.simondumalski.chatmanager.utils.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiveCommand extends SubCommand {

    /**
     * Constructor for the GiveCommand
     * @param plugin Instance of the main plugin class
     */
    public GiveCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public String getCommand() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "Gives <player> a voucher for <color>.";
    }

    @Override
    public String getUsage() {
        return "/chatmanager give <chatcolor|namecolor|title> <player> <color> [amount]";
    }

    @Override
    public String getPermission() {
        return "chatmanager.admin.give";
    }

    @Override
    public List<String> tabComplete(Player player, String[] args) {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {

        //Check if the player sent enough arguments
        if (args.length < 4) {
            MessageManager.messagePlayer(player, Message.INVALID_USAGE, new String[]{getUsage()});
            return;
        }

        //Check if the player sent a valid voucher type
        if (!args[1].equalsIgnoreCase("chatcolor") && !args[1].equalsIgnoreCase("namecolor") && !args[1].equalsIgnoreCase("title")) {
            MessageManager.messagePlayer(player, Message.INVALID_USAGE, new String[]{getUsage()});
            return;
        }

        //Get the voucher type to give
        String voucherType = args[1].toLowerCase();

        //Get the target player
        Player target = plugin.getServer().getPlayer(args[2]);

        //Check if the target player is valid
        if (target == null) {
            MessageManager.messagePlayer(player, Message.INVALID_PLAYER, null);
            return;
        }

        //Get the amount of vouchers to give
        int amount = 1;

        if (args.length >= 5) {

            try {
                amount = Integer.parseInt(args[4]);
            } catch (NumberFormatException e) {
                MessageManager.messagePlayer(player, Message.INVALID_ARGUMENT, new String[]{"amount"});
                return;
            }

            if (amount <= 0) {
                MessageManager.messagePlayer(player, Message.INVALID_ARGUMENT, new String[]{"amount"});
                return;
            }

        }

        //Get the name of the color to give
        String colorName = args[3];

        switch (voucherType) {

            case "chatcolor":

                //Get the ChatColor to give
                CMChatColor chatColor = plugin.getChatColorManager().findChatColor(colorName);

                //Check if the ChatColor is valid
                if (chatColor == null) {
                    MessageManager.messagePlayer(player, Message.INVALID_ARGUMENT, new String[]{"chat color"});
                    return;
                }

                //Get the voucher item
                ItemStack chatColorVoucher = chatColor.getVoucherItem().clone();

                //Set the amount of vouchers to give
                chatColorVoucher.setAmount(amount);

                //Give the voucher to the player
                player.getInventory().addItem(chatColorVoucher);

                //Send messages to the command sender and the target player
                MessageManager.messagePlayer(player, Message.VOUCHER_GIVEN, new String[]{target.getName(), Integer.toString(amount), colorName});
                MessageManager.messagePlayer(target, Message.VOUCHER_RECEIVED, new String[]{Integer.toString(amount), colorName});

                break;
            case "namecolor":

                //Get the NameColor to give
                NameColor nameColor = plugin.getNameColorManager().findNameColor(colorName);

                //Check if the NameColor is valid
                if (nameColor == null) {
                    MessageManager.messagePlayer(player, Message.INVALID_ARGUMENT, new String[]{"name color"});
                    return;
                }

                //Get the voucher item
                ItemStack nameColorVoucher = nameColor.getVoucherItem().clone();

                //Set the amount of vouchers to give
                nameColorVoucher.setAmount(amount);

                //Give the voucher to the player
                player.getInventory().addItem(nameColorVoucher);

                //Send messages to the command sender and the target player
                MessageManager.messagePlayer(player, Message.VOUCHER_GIVEN, new String[]{target.getName(), Integer.toString(amount), colorName});
                MessageManager.messagePlayer(target, Message.VOUCHER_RECEIVED, new String[]{Integer.toString(amount), colorName});

                break;
            case "title":

                //Get the Title to give
                Title title = plugin.getTitleManager().findTitle(colorName);

                //Check if the Title is valid
                if (title == null) {
                    MessageManager.messagePlayer(player, Message.INVALID_ARGUMENT, new String[]{"title"});
                    return;
                }

                //Get the voucher item
                ItemStack titleVoucher = title.getVoucherItem().clone();

                //Set the amount of vouchers to give
                titleVoucher.setAmount(amount);

                //Give the voucher to the player
                player.getInventory().addItem(titleVoucher);

                //Send messages to the command sender and the target player
                MessageManager.messagePlayer(player, Message.VOUCHER_GIVEN, new String[]{target.getName(), Integer.toString(amount), colorName});
                MessageManager.messagePlayer(target, Message.VOUCHER_RECEIVED, new String[]{Integer.toString(amount), colorName});

                break;
            default:
                MessageManager.messagePlayer(player, Message.INVALID_USAGE, new String[]{getUsage()});
                break;

        }

    }

}
