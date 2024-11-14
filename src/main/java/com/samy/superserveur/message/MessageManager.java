package com.samy.superserveur.message;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MessageManager {

    Map<String, String> messagesReceived = new HashMap<>();

    public void sendMessage(Player sender, Player receiver, String message) {
        messagesReceived.remove(receiver.getName());
        messagesReceived.put(receiver.getName(), sender.getName());
        MessageMessageUtils.sendMessage(sender, receiver, message);
    }

    public void respondMessage(Player sender, String message) {
        String receiverName = messagesReceived.get(sender.getName());
        if (receiverName != null) {
            Player receiver = sender.getServer().getPlayer(receiverName);
            if (receiver != null) {
                sendMessage(sender, receiver, message);
            }
        }
    }
}
