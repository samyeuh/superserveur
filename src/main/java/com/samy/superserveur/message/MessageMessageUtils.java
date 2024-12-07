package com.samy.superserveur.message;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageMessageUtils {

    private static final String crochetOuvert = ChatColor.GRAY + "[" + ChatColor.RESET;
    private static final String crochetFermer = ChatColor.GRAY + "]" + ChatColor.WHITE;
    private static final String fleche = ChatColor.GRAY + " -> " + ChatColor.RESET;
    private static final ChatColor headerColor = ChatColor.AQUA;
    private static String formatName(String name){
        return headerColor + name + ChatColor.RESET;
    }

    private static TextComponent formatRespond(String name){
        String cmd = "/r ";
        TextComponent msg = new TextComponent(ChatColor.LIGHT_PURPLE + "[R]" + ChatColor.WHITE + ": ");
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Cliquez pour répondre à " + name)}));
        return msg;
    }

    public static void sendMessageSender(Player sender, Player receiver, String message) {
        String messageHeader = crochetOuvert +
                ChatColor.GRAY + "Moi"
                + fleche
                + formatName(receiver.getName())
                + crochetFermer
                + ChatColor.WHITE + ": ";
        String messageToSend = messageHeader + message;
        sender.sendMessage(messageToSend);
    }

    public static void sendMessageReceiver(Player sender, Player receiver, String message) {
        String messageHeader = crochetOuvert
                + formatName(sender.getName())
                + fleche
                + ChatColor.GRAY + "Moi"
                + crochetFermer;

        TextComponent respond = formatRespond(sender.getName());
        TextComponent fullMessage = new TextComponent(messageHeader);
        fullMessage.addExtra(respond);
        fullMessage.addExtra(message);
        receiver.spigot().sendMessage(fullMessage);
    }

    public static void messageError(Player player, String erreur) {
        player.sendMessage(ChatColor.RED + erreur);
    }
}
