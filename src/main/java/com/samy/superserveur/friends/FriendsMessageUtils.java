package com.samy.superserveur.friends;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

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

    public static void friendRequestAccepted(CommandSender sender, String friend) {
        sender.sendMessage(friendHeader + formatName(friend) + " est maintenant votre ami !");
    }

    public static void friendListHandler(CommandSender sender, List<String> friends) {
        sender.sendMessage(friendHeader + "Vos amis:");
        for (String friend : friends) {
            sender.sendMessage("- " + formatName(friend));
        }
        sender.sendMessage(ChatColor.GOLD + "---------------------------------");
    }

    public static void friendRemoved(CommandSender sender, String target) {
        sender.sendMessage(friendHeader + formatName(target) + " n'est plus votre ami.");
    }
}
