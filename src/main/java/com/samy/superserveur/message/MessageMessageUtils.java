package com.samy.superserveur.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageMessageUtils {

    private static final String crochetOuvert = ChatColor.GRAY + "[" + ChatColor.RESET;
    private static final String crochetFermer = ChatColor.GRAY + "]" + ChatColor.WHITE + " ";
    private static final String fleche = ChatColor.GRAY + " -> " + ChatColor.RESET;

    public static void sendMessageSender(Player sender, Player receiver, String message) {
        String messageHeader = crochetOuvert +
                ChatColor.WHITE + sender.getName()
                + fleche
                + ChatColor.GOLD + receiver.getName()
                + crochetFermer;
        String messageToSend = messageHeader + message;
        sender.sendMessage(messageToSend);
    }

    public static void sendMessageReceiver(Player sender, Player receiver, String message) {
        String messageHeader = crochetOuvert
                + ChatColor.GOLD + sender.getName()
                + fleche
                + ChatColor.WHITE + receiver.getName()
                + crochetFermer;
        String messageToSend = messageHeader + message;
        receiver.sendMessage(messageToSend);
    }

    public static void messageError(Player player, String erreur) {
        player.sendMessage(ChatColor.RED + erreur);
    }
}
