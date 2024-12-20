package com.samy.superserveur.message;

import com.samy.api.message.IMessageManager;
import com.samy.superserveur.SuperServeurPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    private final IMessageManager messageManager;

    public MessageCommand(SuperServeurPlugin plugin){
        this.messageManager = plugin.getApi().getMessageManager();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

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
            StringBuilder msg = new StringBuilder();
            for (String string : strings) {
                msg.append(string).append(" ");
            }
            messageManager.respondMessage(player, msg.toString());
        }
    return true;
    }

    public void sendMessageCommand(Player player, Player receiver, String message) {
        if (receiver == null || !receiver.isOnline()) {
            MessageMessageUtils.messageError(player, "Le joueur n'est pas en ligne.");
            return;
        }
        messageManager.sendMessage(player, receiver, message);
    }
}
