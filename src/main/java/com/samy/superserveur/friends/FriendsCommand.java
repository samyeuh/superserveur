package com.samy.superserveur.friends;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FriendsCommand implements CommandExecutor {

    public final FriendsManager friendsManager;

    public FriendsCommand(FriendsManager friendsManager) {
        this.friendsManager = friendsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
            return true;
        }
        FriendsSubCommand subCommand;

        if (args.length < 1 || args.length > 2 || !FriendsSubCommand.contains(args[0])) {
            subCommand = FriendsSubCommand.LIST;
        } else {
            subCommand = FriendsSubCommand.valueOf(args[0].toUpperCase());
        }

        switch (subCommand) {
            case ADD:
                if (args.length < 2) {
                    player.sendMessage("Utilisation: /friends add <joueur>");
                    return true;
                }
                Player newFriend = Bukkit.getPlayer(args[1]);
                if (newFriend != null && newFriend.isOnline() && !newFriend.equals(player) && !friendsManager.areFriends(player, newFriend)) {
                    friendsManager.sendRequest(player, newFriend);
                } else {
                    player.sendMessage("Impossible d'ajouter cet ami.");
                }
                break;

            case ACCEPT:
                if (args.length == 2) {
                    Player requester = Bukkit.getPlayer(args[1]);
                    if (requester != null && friendsManager.acceptRequest(player, requester)) {
                        FriendsMessageUtils.friendRequestAccepted(player, requester.getName());
                    } else {
                        player.sendMessage("Impossible d'accepter la demande d'ami.");
                    }
                } else {
                    friendsManager.acceptMostRecentRequest(player);
                }
                break;

            case LIST:
                player.sendMessage("Vos amis:");
                for (UUID friendUUID : friendsManager.getFriends(player)) {
                    Player friend = Bukkit.getPlayer(friendUUID);
                    if (friend != null) {
                        player.sendMessage("- " + friend.getName());
                    }
                }
                break;

            case DELETE:
                if (args.length < 2) {
                    player.sendMessage("Utilisation: /friends delete <joueur>");
                    return true;
                }
                Player friendToDelete = Bukkit.getPlayer(args[1]);
                friendsManager.removeFriend(player, friendToDelete);
                break;

            case HELP:
                player.sendMessage("Commandes:");
                player.sendMessage("/friends add <joueur> - Ajouter un ami");
                player.sendMessage("/friends accept [joueur] - Accepter une demande d'ami");
                player.sendMessage("/friends list - Liste des amis");
                player.sendMessage("/friends delete <joueur> - Supprimer un ami");
                break;

        }
        return true;
    }
}
