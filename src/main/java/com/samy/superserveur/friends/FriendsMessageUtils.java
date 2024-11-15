package com.samy.superserveur.friends;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendsMessageUtils {

    private static final String friendHeader = ChatColor.GOLD + "[Amis] " + ChatColor.RESET;

    private static String formatName(String name){
        return ChatColor.GOLD + name + ChatColor.RESET;
    }

    public static void friendConnected(CommandSender sender, String friend){
        String formattedMessage = ChatColor.GREEN + "connecté(e)" + ChatColor.RESET;
        sender.sendMessage(friendHeader + formatName(friend) + " s'est " + formattedMessage + " !");
    }

    public static void friendDisconnected(CommandSender sender, String friend){
        String formattedMessage = ChatColor.RED + "déconnecté(e)" + ChatColor.RESET;
        sender.sendMessage(friendHeader + formatName(friend) + " s'est " + formattedMessage + " !");
    }

    public static void friendRequestAccepted(CommandSender sender, Player friend) {
        sender.sendMessage(friendHeader + formatName(friend.getName()) + " est maintenant votre ami !");
        friend.sendMessage(friendHeader + formatName(sender.getName()) + " est maintenant votre ami !");
    }

    public static void friendRequestSent(CommandSender sender, Player friend){
        sender.sendMessage(friendHeader + "Une requête d'ami a été envoyé à " + formatName(friend.getName()));
        friend.sendMessage(friendHeader + formatName(sender.getName()) + " vous a envoyé une demande d'ami, pour l'accepter: /friends accept " + sender.getName());
    }

    public static void friendRequestNotExist(CommandSender sender, String target) {
        sender.sendMessage(friendHeader + ChatColor.RED + "Vous n'avez pas de demande d'ami de " + ChatColor.RESET + formatName(target));
    }

    public static void friendListHandler(CommandSender sender, List<String> onlineFriends, List<String> offlineFriends) {

        sender.sendMessage(friendHeader + "Vos amis:");
        sender.sendMessage(ChatColor.GOLD + "---------------------------------");
        if (onlineFriends.isEmpty() && offlineFriends.isEmpty()) {
            sender.sendMessage(ChatColor.GRAY + "Vous n'avez pas d'amis.");
        } else {
            for (String friend : onlineFriends) {
                sender.sendMessage(ChatColor.GREEN + " ● " + formatName(friend));
            }
            for (String friend : offlineFriends) {
                sender.sendMessage(ChatColor.RED + " ● " + formatName(friend));
            }
        }
        sender.sendMessage(ChatColor.GOLD + "---------------------------------");
    }

    public static void friendRemoved(CommandSender sender, String target) {
        sender.sendMessage(friendHeader + formatName(target) + " n'est plus votre ami.");
    }

    public static void friendErrorAdd(CommandSender sender, String target) {
        if (sender.getName().equals(target)){
            sender.sendMessage(friendHeader + ChatColor.RED + "Vous ne pouvez pas vous ajouter vous-même en ami.");
        } else if (target != null) {
            sender.sendMessage(friendHeader + ChatColor.RED + "Impossible d'ajouter " + ChatColor.RESET + formatName(target) + ChatColor.RED + " à vos amis.");
        } else {
            sender.sendMessage(friendHeader + ChatColor.RED + "Impossible de trouver le joueur ");
        }
    }

    public static void friendError(CommandSender sender, String error){
        sender.sendMessage(friendHeader + ChatColor.RED + error);
    }
}
