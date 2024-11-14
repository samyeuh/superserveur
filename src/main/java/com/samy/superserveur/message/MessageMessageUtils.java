package com.samy.superserveur.message;

import org.bukkit.entity.Player;

public class MessageMessageUtils {

    public static void sendMessage(Player sender, Player receiver, String message) {

        String messageToSend = "[ " + sender.getName() + " -> " + receiver.getName() + " ] " + message;
        sender.sendMessage(messageToSend);
        receiver.sendMessage(messageToSend);
    }
}
