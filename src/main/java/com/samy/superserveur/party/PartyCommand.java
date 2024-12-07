package com.samy.superserveur.party;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommand implements CommandExecutor {

    private final PartyManager partyManager;

    public PartyCommand(PartyManager partyManager){ this.partyManager = partyManager; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
            return true;
        }
        Player player = (Player) sender;
        PartySubCommand subCommand;

        if (args.length < 1 || args.length > 2 || !PartySubCommand.contains(args[0])) {
            subCommand = PartySubCommand.LIST;
        } else {
            subCommand = PartySubCommand.valueOf(args[0].toUpperCase());
        }

        switch(subCommand){
            case INVITE:
            case ADD:
                if (args.length < 2) {
                    player.sendMessage("Utilisation: /party add <joueur>");
                    break;
                }
                Player receiverToAdd = Bukkit.getPlayer(args[1]);
                partyManager.checkPartyForRequest(player, receiverToAdd);
                break;
            case DELETE:
            case REMOVE:
                if (args.length < 2) {
                    player.sendMessage("Utilisation: /party remove <joueur>");
                    break;
                }
                Player receiverToRemove = Bukkit.getPlayer(args[1]);
                partyManager.removePlayerFromParty(player, receiverToRemove);
                break;
            case LIST:
                partyManager.listParty(player);
                break;

            case ACCEPT:
                if (args.length < 2) {
                    partyManager.acceptMostRecentRequest(player);
                    break;
                }
                Player senderToAccept = Bukkit.getPlayer(args[1]);
                partyManager.acceptRequestFromPlayer(player, senderToAccept);
                break;
            case QUIT:
            case LEAVE:
                partyManager.leaveParty(player);
                break;
            case DISBAND:
                partyManager.disbandParty(player);
                break;
        }
        return true;
    }
}
