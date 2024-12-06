package com.samy.superserveur.friends;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.TextComponent;
import java.util.List;

import static com.samy.superserveur.party.PartyMessageUtils.getTextComponent;

public class FriendsMessageUtils {

    private static final ChatColor headerColors = ChatColor.AQUA;
    private static final ChatColor secondaryColor = ChatColor.LIGHT_PURPLE;
    private static final String friendHeader = headerColors + "[Amis] " + ChatColor.RESET;

    private static String formatName(String name){
        return headerColors + name + ChatColor.RESET;
    }

    private static TextComponent formatCommand(String command, String text, String hover){
        return getTextComponent(command, text, hover, friendHeader, secondaryColor);
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
        sender.sendMessage(friendHeader + "Demande d'ami envoyée à " + formatName(friend.getName()));
        friend.sendMessage(friendHeader + formatName(sender.getName()) + " veut devenir votre ami");
        friend.spigot().sendMessage(formatCommand("/friends accept " + sender.getName(), ChatColor.GREEN + "Cliquez ici pour accepter", "Accepter la demande d'ami de " + sender.getName()));
    }

    public static void friendRequestNotExist(CommandSender sender, String target) {
        sender.sendMessage(friendHeader + ChatColor.RED + "Vous n'avez pas de demande d'ami de " + ChatColor.RESET + formatName(target));
    }

    public static void friendListHandler(CommandSender sender, List<String> onlineFriends, List<String> offlineFriends) {

        sender.sendMessage(friendHeader + "Vos amis:");
        sender.sendMessage(secondaryColor + "===============");
        if (onlineFriends.isEmpty() && offlineFriends.isEmpty()) {
            sender.sendMessage(ChatColor.GRAY + "Vous n'avez pas d'amis.");
        } else {
            for (String friend : onlineFriends) {
                String friendName = ChatColor.WHITE + friend + ChatColor.RESET;
                sender.sendMessage(ChatColor.GREEN + " ● " + friendName);
            }
            for (String friend : offlineFriends) {
                String friendName = ChatColor.GRAY + friend + ChatColor.RESET;
                sender.sendMessage(ChatColor.RED + " ● " + friendName);
            }
        }
        sender.sendMessage(secondaryColor + "===============");
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
