package com.samy.superserveur.message;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    private final MessageManager messageManager;

    public MessageCommand(MessageManager messageManager){
        this.messageManager = messageManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Vous devez être un joueur pour exécuter cette commande.");
            return true;
        }

        if (label.equals("m")) {
            if (strings.length < 1) {
                player.sendMessage("Utilisation: /message <joueur> <message>");
                return true;
            }
            Player receiver = player.getServer().getPlayer(strings[0]);
            StringBuilder msg = new StringBuilder();
            for (int i = 1; i < strings.length; i++) {
                msg.append(strings[i]).append(" ");
            }
            sendMessageCommand(player, receiver, msg.toString());
        } else if (label.equals("r")) {
            if (strings.length < 1) {
                player.sendMessage("Utilisation: /r <message>");
                return true;
            }
            messageManager.respondMessage(player, strings[0]);
        }
    return true;
    }

    public void sendMessageCommand(Player player, Player receiver, String message) {
        if (receiver == null || !receiver.isOnline()) {
            player.sendMessage("Ce joueur n'est pas en ligne.");
            return;
        }
        messageManager.sendMessage(player, receiver, message);
    }
}
