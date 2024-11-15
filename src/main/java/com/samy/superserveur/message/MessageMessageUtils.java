package com.samy.superserveur.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageMessageUtils {

    public static void sendMessageSender(Player sender, Player receiver, String message) {
        String messageHeader = ChatColor.GRAY + "["
                + ChatColor.GOLD + sender.getName()
                + ChatColor.GRAY +  " -> " + receiver.getName() + "] " + ChatColor.WHITE;
        String messageToSend = messageHeader + message;
        sender.sendMessage(messageToSend);
    }

    public static void sendMessageReceiver(Player sender, Player receiver, String message) {
        String messageHeader = ChatColor.GRAY + "[" + sender.getName() + " -> "
                + ChatColor.GOLD + receiver.getName()
                + ChatColor.GRAY +  "] " + ChatColor.WHITE;
        String messageToSend = messageHeader + message;
        receiver.sendMessage(messageToSend);
    }
}
