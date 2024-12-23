package com.samy.superserveur.friends;

import com.samy.api.friends.IFriendsManager;
import com.samy.superserveur.SuperServeurPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendsCommand implements CommandExecutor {

    public final IFriendsManager friendsManager;

    public FriendsCommand(SuperServeurPlugin plugin) {
        this.friendsManager = plugin.getApi().getFriendsManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
            return true;
        }
        Player player = (Player) sender;
        FriendsSubCommand subCommand;
        if (args == null || args.length == 0) {
            subCommand = FriendsSubCommand.LIST;
        } else if (!FriendsSubCommand.contains(args[0])) {
            FriendsMessageUtils.friendError(player, "Commande inconnue.");
            return true;
        } else {
            subCommand = FriendsSubCommand.valueOf(args[0].toUpperCase());
        }

        switch (subCommand) {
            case INVITE:
            case ADD:
                if (args.length < 2) {
                    player.sendMessage("Utilisation: /friends add <joueur>");
                    return true;
                }
                Player newFriend = Bukkit.getPlayer(args[1]);
                if (newFriend != null && newFriend.isOnline() && !newFriend.equals(player) && !friendsManager.areFriends(player, newFriend)) {
                    friendsManager.sendRequest(player, newFriend);
                } else {
                    assert newFriend != null;
                    FriendsMessageUtils.friendErrorAdd(player, newFriend.getName());
                }
                break;

            case ACCEPT:
                if (args.length == 2) {
                    Player requester = Bukkit.getPlayer(args[1]);
                    if (requester == null) {
                        FriendsMessageUtils.friendRequestNotExist(player, args[1]);
                        break;
                    }
                    friendsManager.acceptRequest(player, requester);
                } else {
                    friendsManager.acceptMostRequest(player);
                }
                break;

            case LIST:
                FriendsMessageUtils.friendListHandler(player, friendsManager.getOnlineFriends(player), friendsManager.getOfflineFriends(player));
                break;
            case REMOVE:
            case DELETE:
                if (args.length < 2) {
                    player.sendMessage("Utilisation: /friends delete <joueur>");
                    return true;
                }
                Player friendToDelete = Bukkit.getPlayer(args[1]);
                if (friendToDelete != null && friendsManager.areFriends(player, friendToDelete)) {
                    friendsManager.removeFriend(player, friendToDelete);
                } else {
                    player.sendMessage("Impossible de supprimer cet ami.");
                }
                break;

            case HELP:
                // TODO: Friend MessageUtils
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
